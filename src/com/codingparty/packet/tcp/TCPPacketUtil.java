package com.codingparty.packet.tcp;

public class TCPPacketUtil {

	public static int getInt(byte[] packetData, int offset) {
		return (packetData[offset] <<24) & 0xff000000 | (packetData[offset + 1] <<16) & 0x00ff0000 | (packetData[offset + 2] << 8) & 0x0000ff00 | (packetData[offset + 3] << 0) & 0x000000ff;
	}
	
	public static EnumTCPPacketType getEnum(byte[] packetData, int offset) {
		return EnumTCPPacketType.values()[getInt(packetData, offset)];
	}
}
