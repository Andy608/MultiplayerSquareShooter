package com.codingparty.packet;

import java.nio.ByteBuffer;

import com.codingparty.packet.tcp.EnumTCPPacketType;
import com.codingparty.packet.udp.EnumUDPPacketType;

public class PacketUtil {

	public static int getInt(byte[] packetData, int offset) {
		return (packetData[offset] <<24) & 0xff000000 | (packetData[offset + 1] <<16) & 0x00ff0000 | (packetData[offset + 2] << 8) & 0x0000ff00 | (packetData[offset + 3] << 0) & 0x000000ff;
	}
	
	public static float getFloat(byte[] packetData, int offset) {
        ByteBuffer buffer = ByteBuffer.wrap(packetData);
        return buffer.getFloat(offset);
	}
	
	public static double getDouble(byte[] packetData, int offset) {
		ByteBuffer buffer = ByteBuffer.wrap(packetData);
        return buffer.getDouble(offset);
	}
	
	public static long getLong(byte[] packetData, int offset) {
		ByteBuffer buffer = ByteBuffer.wrap(packetData);
		return buffer.getLong(offset);
	}
	
	public static boolean getBoolean(byte packetByte) {
		return (packetByte == (byte)1);
	}
	
	public static EnumUDPPacketType getUDPEnum(byte[] packetData, int offset) {
		return EnumUDPPacketType.values()[getInt(packetData, offset)];
	}
	
	public static EnumTCPPacketType getTCPEnum(byte[] packetData, int offset) {
		return EnumTCPPacketType.values()[getInt(packetData, offset)];
	}
}
