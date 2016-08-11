package com.codingparty.packet.udp;

public enum EnumUDPPacketType {
	
	PLAYER_CONTROL_UPDATE_PACKET(20),
	PLAYER_POSITION_UPDATE_PACKET(40),
	OBJECT_STATE_UPDATE_PACKET(28);
	
	public static final int MAX_BYTE_ALLOCATION = 40;
	
	private int byteLength;
	
	private EnumUDPPacketType(int length) {
		byteLength = length;
	}
	
	public int getByteLength() {
		return byteLength;
	}
}
