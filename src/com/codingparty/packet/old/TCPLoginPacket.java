package com.codingparty.packet.old;

public class TCPLoginPacket extends AbstractTCPPacket {

//	private boolean isLoggingOn;
//	
//	public TCPLoginPacket(int id, boolean loggingIn) {
//		super(EnumTCPPacketType.LOG_IN_OFF_PACKET, id);
//		isLoggingOn = loggingIn;
//	}
//	
//	public int getConnectionID() {
//		return connectionID;
//	}
//	
//	public boolean isLoggingOn() {
//		return isLoggingOn;
//	}
//	
//	public boolean isLoggingOff() {
//		return !isLoggingOn;
//	}
//
//	@Override
//	public byte[] prepare() {
//		byteLength = 9;
//		ByteBuffer b = ByteBuffer.allocate(byteLength);
//		b.putInt(packetType.ordinal());
//		b.putInt(connectionID);
//		b.put((byte)(isLoggingOn ? 1 : 0));
//		isPreparedForShipment = true;
//		return (preparedData = b.array());
//	}
}
