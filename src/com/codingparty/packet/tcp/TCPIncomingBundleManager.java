package com.codingparty.packet.tcp;

import java.util.ArrayList;

import com.codingparty.core.CodingParty;
import com.codingparty.entity.util.Color;
import com.codingparty.logger.LoggerUtil;
import com.codingparty.math.MathHelper;

public class TCPIncomingBundleManager {
	
	private static ArrayList<AbstractTCPClientPacket> incomingPackets = new ArrayList<>();

	public static synchronized void retreiveBundle(byte[] bundleData) {
		int packetsInBundle = TCPPacketUtil.getInt(bundleData, 0);
		int offset = 4;
		
		for (int i = 0; i < packetsInBundle; i++) {
			EnumTCPPacketType packetType = TCPPacketUtil.getEnum(bundleData, offset);
			byte[] packetData = new byte[packetType.getByteLength()];
			
			for (int j = 0; j < packetData.length; j++) {
				packetData[j] = bundleData[offset];
				offset++;
			}
			
			if (packetType.equals(EnumTCPPacketType.ID_PACKET)) {
				if (!CodingParty.getWorld().getGameManager().getConnectionThread().isConnectionIDSet()) {
					int connectionID = TCPPacketUtil.getInt(packetData, 4);
					LoggerUtil.logInfo(TCPIncomingBundleManager.class, "RECEIVED ID PACKET FROM SERVER. ID = " + connectionID);
					CodingParty.getWorld().getGameManager().getConnectionThread().setConnectionID(connectionID);
				}
			}
			else if (packetType.equals(EnumTCPPacketType.LEVEL_PACKET)) {
				int levelID = TCPPacketUtil.getInt(packetData, 4);
				LoggerUtil.logInfo(TCPIncomingBundleManager.class, "RECEIVED LEVEL PACKET FROM SERVER. LEVELID = " + levelID);
				incomingPackets.add(new TCPClientLevelPacket(levelID));
			}
			else if (packetType.equals(EnumTCPPacketType.LOGIN_PACKET)) {
				int connectionID = TCPPacketUtil.getInt(packetData, 4);
				LoggerUtil.logInfo(TCPIncomingBundleManager.class, "RECEIVED LOGIN PACKET FROM SERVER. LOGINID = " + connectionID);
				incomingPackets.add(new TCPClientLoginPacket(connectionID, (packetData[8] == (byte)1) ? true : false));
			}
			else if (packetType.equals(EnumTCPPacketType.PLAYER_COLOR_PACKET)) {
				int connectionID = TCPPacketUtil.getInt(packetData, 4);
				LoggerUtil.logInfo(TCPIncomingBundleManager.class, "RECEIVED PLAYER COLOR PACKET FROM SERVER. COLOR = " + Color.fromInt(TCPPacketUtil.getInt(packetData, 8)));
				incomingPackets.add(new TCPClientPlayerColorPacket(connectionID, Color.fromInt(TCPPacketUtil.getInt(packetData, 8))));
			}
			else if (packetType.equals(EnumTCPPacketType.SERVER_CAPACITY_PACKET)) {
				if (CodingParty.getWorld().getGameManager().getUsers() == null) {
					LoggerUtil.logInfo(TCPIncomingBundleManager.class, "RECEIVED SERVER CAPACITY PACKET FROM SERVER. CAPACITY = " + TCPPacketUtil.getInt(packetData, 4));
					CodingParty.getWorld().getGameManager().initUsers(TCPPacketUtil.getInt(packetData, 4));
//					incomingPackets.add(new TCPClientCapacityPacket(TCPPacketUtil.getInt(packetData, 4)));
				}
			}
			else {
				LoggerUtil.logWarn(TCPIncomingBundleManager.class, "The incoming packet is not in the desired format. Type: " + packetType);
				return;
			}
			
			System.out.println("INCOMING PACKETS SIZE: " + incomingPackets.size());
		}
	}
	
	public static synchronized void update() {
		executeLoginPackets();
		
		//issue:
		//the login packet is not being added to the user list because the login packet is coming in before the user list gets initialized.
		
		for (int packetIndex = 0; packetIndex < incomingPackets.size(); packetIndex++) {
			AbstractTCPClientPacket currentPacket = incomingPackets.get(packetIndex);
			
			if (currentPacket.getPacketType().equals(EnumTCPPacketType.PLAYER_COLOR_PACKET)) {
				TCPClientPlayerColorPacket colorPacket = (TCPClientPlayerColorPacket) currentPacket;
				if (CodingParty.getWorld().getGameManager().getUsers()[colorPacket.getPlayerConnectionID()] != null) {
					CodingParty.getWorld().getGameManager().setUserColor(colorPacket.getPlayerConnectionID(), colorPacket.getColor());
					System.out.println("SET COLOR");
					removePacketsFromIncomingList(colorPacket);
					packetIndex--;
				}
			}
			else if (currentPacket.getPacketType().equals(EnumTCPPacketType.LEVEL_PACKET)) {
				TCPClientLevelPacket levelPacket = (TCPClientLevelPacket) currentPacket;
				CodingParty.getWorld().getGameManager().setLevel(levelPacket.getLevelType().getLevelID());
				System.out.println("SET LEVEL");
				removePacketsFromIncomingList(levelPacket);
				packetIndex--;
			}
		}
	}
	
	private static synchronized void executeLoginPackets() {
		for (int packetIndex = 0; packetIndex < incomingPackets.size(); packetIndex++) {
			AbstractTCPClientPacket currentPacket = incomingPackets.get(packetIndex);
			if (currentPacket.getPacketType().equals(EnumTCPPacketType.LOGIN_PACKET)) {
				TCPClientLoginPacket loginPacket = (TCPClientLoginPacket) currentPacket;
				
				if (!loginPacket.isLoggingIn()) {
					//Update the client game. Remove player from game.
					CodingParty.getWorld().getGameManager().removeUser(loginPacket.getPlayerConnectionID());
					System.out.println("SET LOGOUT");
					removePacketsFromIncomingList(loginPacket);
					
					//Shift index since I removed 1 packet.
					packetIndex--;
				}
				else {
					TCPClientPlayerColorPacket colorPacket = (TCPClientPlayerColorPacket) getPacketWithConnectionIDFromIncomingList(EnumTCPPacketType.PLAYER_COLOR_PACKET, loginPacket.getPlayerConnectionID());
					
					if (colorPacket != null) {
						//Update the server game. Set the color of the player. Add player to game.
						CodingParty.getWorld().getGameManager().addUser(loginPacket.getPlayerConnectionID());
						CodingParty.getWorld().getGameManager().setUserColor(colorPacket.getPlayerConnectionID(), colorPacket.getColor());
						
						System.out.println("SET LOGIN AND COLOR");
						
						int indexOfColorPacket = incomingPackets.indexOf(colorPacket);
						
						//Shift indexes since I removed 2 packets.
						if (MathHelper.min(packetIndex, indexOfColorPacket) == indexOfColorPacket) {
							packetIndex = indexOfColorPacket - 1;
						}
						else {
							packetIndex--;
						}
						removePacketsFromIncomingList(loginPacket, colorPacket);
					}
				}
			}
		}
	}
	
	private static synchronized void removePacketsFromIncomingList(AbstractTCPClientPacket... packets) {
		for (AbstractTCPClientPacket packet : packets) {
			incomingPackets.remove(packet);
		}
	}
	
	private static synchronized AbstractTCPClientPlayerUpdatePacket getPacketWithConnectionIDFromIncomingList(EnumTCPPacketType type, int connectionID) {
		
		for (int packetIndex = 0; packetIndex < incomingPackets.size(); packetIndex++) {
			if (incomingPackets.get(packetIndex) instanceof AbstractTCPClientPlayerUpdatePacket) {
				AbstractTCPClientPlayerUpdatePacket packet = (AbstractTCPClientPlayerUpdatePacket) incomingPackets.get(packetIndex);
				
				if (packet.getPacketType().equals(type) && packet.getPlayerConnectionID() == connectionID) {
					return packet;
				}
			}
		}
		LoggerUtil.logWarn(TCPIncomingBundleManager.class, "Could not find a packet in the bundle with type = " + type + " and connectionID = " + connectionID);
		return null;
	}
	
}
