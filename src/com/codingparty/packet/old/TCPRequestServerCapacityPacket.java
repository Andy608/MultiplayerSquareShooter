package com.codingparty.packet.old;

public class TCPRequestServerCapacityPacket extends AbstractTCPPacket {

//	public TCPRequestServerCapacityPacket(int id) {
//		super(EnumTCPPacketType.SERVER_CAPACITY_PACKET, id);
//	}
//
//	@Override
//	public byte[] prepare() {
//		byteLength = 8;
//		ByteBuffer b = ByteBuffer.allocate(byteLength);
//		b.putInt(packetType.ordinal());
//		b.putInt(connectionID);
//		isPreparedForShipment = true;
//		return (preparedData = b.array());
//	}
}
