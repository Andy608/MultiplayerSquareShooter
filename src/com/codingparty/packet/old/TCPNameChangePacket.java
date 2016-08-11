package com.codingparty.packet.old;

public class TCPNameChangePacket extends AbstractTCPPacket {

//	private String displayName;
//	
//	public TCPNameChangePacket(int id, String updatedName) {
//		super(EnumTCPPacketType.NAME_CHANGE_PACKET, id);
//		displayName = updatedName;
//	}
//
//	@Override
//	public byte[] prepare() {
//		byte[] nameInBytes = displayName.getBytes();
//		byteLength = 8 + nameInBytes.length;
//		ByteBuffer b = ByteBuffer.allocate(byteLength);
//		b.putInt(packetType.ordinal());
//		b.putInt(connectionID);
//		b.put(nameInBytes);
//		isPreparedForShipment = true;
//		return (preparedData = b.array());
//	}
//
//	public String getDisplayName() {
//		return displayName;
//	}
}
