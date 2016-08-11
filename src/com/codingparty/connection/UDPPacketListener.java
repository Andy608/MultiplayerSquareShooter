package com.codingparty.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import com.codingparty.logger.LoggerUtil;
import com.codingparty.packet.udp.EnumUDPPacketType;
import com.codingparty.packet.udp.UDPIncomingPacketManager;

public class UDPPacketListener extends Thread {

	private String groupIP;
	private MulticastSocket socket;
	private volatile boolean isListening;
	
	public UDPPacketListener(String ip, int port) throws IOException {
		groupIP = ip;
		socket = new MulticastSocket(port);
		isListening = false;
	}
	
	@Override
	public void run() {
		
		try {
			InetAddress group = InetAddress.getByName(groupIP);
			socket.joinGroup(group);
		} catch (IOException e1) {
			LoggerUtil.logError(getClass(), e1);
		}
		
		while (isListening) {
			
			try {
				byte[] buffer = new byte[EnumUDPPacketType.MAX_BYTE_ALLOCATION];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				
				UDPIncomingPacketManager.receivedUDPPacket(packet.getData());
				
			} catch (IOException e) {
				LoggerUtil.logError(getClass(), e);
			}
		}
	}
	
	public synchronized void startListening() {
		isListening = true;
		this.start();
	}
	
	public synchronized void stopListening() {
		isListening = false;
	}
	
	public synchronized boolean isListening() {
		return isListening;
	}
}
