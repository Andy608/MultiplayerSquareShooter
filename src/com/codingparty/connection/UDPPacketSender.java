package com.codingparty.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.codingparty.logger.LoggerUtil;
import com.codingparty.packet.udp.AbstractUDPClientPacket;
import com.codingparty.packet.udp.UDPClientPlayerControlUpdatePacket;

public class UDPPacketSender {

	//TODO: Create this object somewhere. Call the sendUpdatePacket method somewhere. 
	
	private String hostIP;
	private int portNumber;
	private DatagramSocket socket;
	
	public UDPPacketSender(String ipAddress, int port) throws SocketException {
		hostIP = ipAddress;
		portNumber = port;
		socket = new DatagramSocket();
	}
	
	public void sendUpdatePacket(AbstractUDPClientPacket updatePacket) {
		byte[] packetData = updatePacket.getPacketData();
		
		try {
			InetAddress address = InetAddress.getByName(hostIP);
			
			UDPClientPlayerControlUpdatePacket p = (UDPClientPlayerControlUpdatePacket)updatePacket;
//			System.out.println("FORWARD: " + p.isForwardPressed + " | " + " BACKWARD: " + p.isBackPressed + " | " + " LEFT: " + p.isLeftPressed + " | " + " RIGHT: " + p.isRightPressed);
			DatagramPacket packet = new DatagramPacket(packetData, packetData.length, address, portNumber);
			socket.send(packet);
		} catch (UnknownHostException e) {
			LoggerUtil.logError(getClass(), "Invalid host address.", e);
		} catch (IOException e) {
			LoggerUtil.logError(getClass(), e);
		}
	}
}
