package com.codingparty.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import com.codingparty.core.CodingParty;
import com.codingparty.logger.LoggerUtil;
import com.codingparty.packet.tcp.TCPClientPacketBundle;
import com.codingparty.packet.tcp.TCPIncomingBundleManager;

public class TCPConnectionThread extends Thread {

	private static final int TIME_OUT_MILLISECONDS = 5000;
	private Socket TCPSocket;
	private String hostIP;
	private int portNumber;
	private boolean stayConnected;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;
	private boolean hasIDBeenSet;
	private int connectionID;
	
	public TCPConnectionThread(String ip, int port) {
		hostIP = ip;
		portNumber = port;
		hasIDBeenSet = false;
	}
	
	@Override
	public void run() {
		try {
			TCPSocket = new Socket();
			TCPSocket.connect(new InetSocketAddress(InetAddress.getByName(hostIP), portNumber), TIME_OUT_MILLISECONDS);
			dataOutputStream = new DataOutputStream(TCPSocket.getOutputStream());
			dataInputStream = new DataInputStream(TCPSocket.getInputStream());
			
		} catch (IOException e) {
			LoggerUtil.logError(getClass(), "Could not connect to server at ip: " + hostIP + " : " + portNumber + " Is it running?", e);
			return;
		}
		LoggerUtil.logInfo(getClass(), "The server is open! IP: " + hostIP + " Port: " + portNumber);
		stayConnected = true;
		
		while (stayConnected && !TCPSocket.isClosed()) {
			try {
				if (dataInputStream.available() > 0) {
					//Listens for any TCP Packets coming in from the server.
					//This is then sent off to the TCPPacketHandler to decide what to do and update all the other threads.
					byte[] incomingBundleData = readBundleData();
					if (incomingBundleData != null) {
						TCPIncomingBundleManager.retreiveBundle(incomingBundleData);
					}
				}
			} catch (IOException e) {
				LoggerUtil.logError(getClass(), e);
			}
		}
		
		try {
			TCPSocket.close();
			System.out.println("SOCKET CLOSED.");
		} catch (IOException e) {
			LoggerUtil.logError(getClass(), e);
		}
		System.out.println("Disconnected from server.");
	}
	
	public synchronized byte[] readBundleData() {
		
		try {
			int packetLength = dataInputStream.readInt();
			byte[] packetData = new byte[packetLength];
			dataInputStream.readFully(packetData);
			return packetData;
			
		} catch(SocketException e) {
			LoggerUtil.logWarn(getClass(), "Lost connection to server unexpectedly.");
			//TODO: Reset to main menu
		} catch (IOException e) {
			LoggerUtil.logError(getClass(), "Error reading packet data.", e);
		}
		return null;
	}
	
	public synchronized void sendBundle(TCPClientPacketBundle bundle) {
		
		byte[] bundleData = bundle.getPacketData();
		try {
			dataOutputStream.writeInt(bundleData.length);
			dataOutputStream.write(bundleData);
			
			bundle.setBundleSent();
			bundle = null;
			
		} catch(SocketException e) {
			LoggerUtil.logWarn(getClass(), "Lost connection to server unexpectedly.");
			//TODO: Reset to main menu
			return;
		} catch (IOException e) {
			LoggerUtil.logError(getClass(), e);
		}
	}
	
	public synchronized boolean hasReceivedInitPackets() {
		return (hasIDBeenSet && CodingParty.getWorld().getGameManager().getLevel() != null
			&& CodingParty.getWorld().getGameManager().getUsers() != null
			&& CodingParty.getWorld().getGameManager().getUsers()[connectionID] != null
			&& CodingParty.getWorld().getGameManager().getUsers()[connectionID].getEntityPlayer().getColor() != null);
	}
	
	public synchronized boolean stayConnected() {
		return stayConnected;
	}
	
	public synchronized int getConnectionID() {
		return connectionID;
	}
	
	public synchronized boolean isConnectionIDSet() {
		return hasIDBeenSet;
	}
	
	public synchronized void setConnectionID(int id) {
		if (!hasIDBeenSet) {
			hasIDBeenSet = true;
			connectionID = id;
			System.out.println("CONNECTION ID SET TO: " + connectionID);
		}
		else {
			LoggerUtil.logWarn(getClass(), "ConnectionID has already been set.");
		}
	}
}
