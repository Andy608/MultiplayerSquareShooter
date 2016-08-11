package com.codingparty.packet.old;

public abstract class AbstractTCPPacket {

//	protected int connectionID;
//	protected EnumTCPPacketType packetType;
//	protected boolean isPreparedForShipment;
//	protected byte[] preparedData;
//	protected int byteLength;
//	
//	public AbstractTCPPacket(EnumTCPPacketType type, int id) {
//		connectionID = id;
//		packetType = type;
//		isPreparedForShipment = false;
//	}
//	
//	public abstract byte[] prepare();
//	
//	public byte[] getPreparedData() {
//		if (!isPreparedForShipment) {
//			preparedData = prepare();
//			isPreparedForShipment = true;
//			return preparedData;
//		}
//		return preparedData;
//	}
//	
//	public EnumTCPPacketType getPacketType() {
//		return packetType;
//	}
}
