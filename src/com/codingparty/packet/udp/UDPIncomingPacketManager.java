package com.codingparty.packet.udp;

import java.util.ArrayList;

import com.codingparty.core.CodingParty;
import com.codingparty.packet.PacketUtil;

public class UDPIncomingPacketManager {

	private static ArrayList<AbstractUDPClientPacket> incomingPackets = new ArrayList<>();
	
	public static synchronized void receivedUDPPacket(byte[] packetData) {
		EnumUDPPacketType packetType = PacketUtil.getUDPEnum(packetData, 0);
		
		switch(packetType) {
		case PLAYER_POSITION_UPDATE_PACKET:
			addPlayerPositionPacket(packetData);
			break;
		case OBJECT_STATE_UPDATE_PACKET:
			//TODO: Add this.
			break;
		}
	}
	
//	packetData.putInt(getPacketType().ordinal());
//	packetData.putInt(playerConnectionID);
//	packetData.putLong(initialTime);
//	packetData.putFloat(positionX);
//	packetData.putFloat(positionY);
//	packetData.putFloat(positionZ);
//	packetData.putFloat(rotationX);
//	packetData.putFloat(rotationY);
//	packetData.putFloat(rotationZ);
	
	private static synchronized void addPlayerPositionPacket(byte[] packetData) {
		int connectionID = PacketUtil.getInt(packetData, 4);
		long initialTime = PacketUtil.getLong(packetData, 8);
		float posX = PacketUtil.getFloat(packetData, 16);
		float posY = PacketUtil.getFloat(packetData, 20);
		float posZ = PacketUtil.getFloat(packetData, 24);
		float rotX = PacketUtil.getFloat(packetData, 28);
		float rotY = PacketUtil.getFloat(packetData, 32);
		float rotZ = PacketUtil.getFloat(packetData, 36);
//		System.out.println("ADDING PLAYER STATE UPDATE PACKET: CONNECTION = " + connectionID);
		incomingPackets.add(new UDPServerPlayerStateUpdatePacket(connectionID, initialTime, posX, posY, posZ, rotX, rotY, rotZ));
	}
	
	public static synchronized void update(double deltaTime) {
		for (int packetIndex = 0; packetIndex < incomingPackets.size(); packetIndex++) {
			
			if (incomingPackets.get(packetIndex) == null) {
				incomingPackets.remove(packetIndex);
				packetIndex--;
				continue;
			}
			
			if (incomingPackets.get(packetIndex).getPacketType().equals(EnumUDPPacketType.PLAYER_POSITION_UPDATE_PACKET)) {
				UDPServerPlayerStateUpdatePacket updatePacket = (UDPServerPlayerStateUpdatePacket) incomingPackets.get(packetIndex);
				CodingParty.getWorld().getGameManager().updatePlayerPosition(updatePacket.getPlayerConnectionID(), updatePacket.positionX, updatePacket.positionY, updatePacket.positionZ, updatePacket.rotationX, updatePacket.rotationY, updatePacket.rotationZ, deltaTime);
				incomingPackets.remove(updatePacket);
				packetIndex--;
			}
		}
	}
}
