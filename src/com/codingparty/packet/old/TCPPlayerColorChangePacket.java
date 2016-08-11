package com.codingparty.packet.old;

public class TCPPlayerColorChangePacket extends AbstractTCPPacket {

//	private Color newColor;
//	
//	public TCPPlayerColorChangePacket(int id, Color color) {
//		super(EnumTCPPacketType.PLAYER_COLOR_CHANGE_PACKET, id);
//		newColor = color;
//	}
//	
//	public int getConnectionID() {
//		return connectionID;
//	}
//	
//	public Color getColor() {
//		return newColor;
//	}
//
//	@Override
//	public byte[] prepare() {
//		byteLength = 12;
//		ByteBuffer b = ByteBuffer.allocate(byteLength);
//		b.putInt(packetType.ordinal());
//		b.putInt(connectionID);
//		System.out.println("YOU");
//		b.putInt(newColor.toInt());
//		System.out.println("FUCK");
//		isPreparedForShipment = true;
//		return (preparedData = b.array());
//	}
}
