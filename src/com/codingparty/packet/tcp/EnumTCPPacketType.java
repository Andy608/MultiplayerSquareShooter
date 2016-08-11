package com.codingparty.packet.tcp;

import com.codingparty.packet.EnumPacketPriorityLevel;

public enum EnumTCPPacketType {

	ID_PACKET(8, EnumPacketPriorityLevel.VERY_HIGH),
	SERVER_CAPACITY_PACKET(8, EnumPacketPriorityLevel.VERY_HIGH),
	LOGIN_PACKET(9, EnumPacketPriorityLevel.HIGH),
	PLAYER_COLOR_PACKET(12, EnumPacketPriorityLevel.HIGH),
	LEVEL_PACKET(8, EnumPacketPriorityLevel.MODERATE);
//	NAME_CHANGE_PACKET(EnumPacketPriorityLevel.LOW),
	
	private EnumPacketPriorityLevel packetPriority;
	private int byteLength;
	
	private EnumTCPPacketType(int length, EnumPacketPriorityLevel priority) {
		byteLength = length;
		packetPriority = priority;
	}
	
	public int getByteLength() {
		return byteLength;
	}
	
	public EnumPacketPriorityLevel getPriority() {
		return packetPriority;
	}
}
