package com.codingparty.packet.tcp;

import java.nio.ByteBuffer;

import com.codingparty.entity.util.Color;

public class TCPClientPlayerColorPacket extends AbstractTCPClientPlayerUpdatePacket {

	/**
	 * ::Packet Data::
	 * First 4 bytes will always be EnumPacketType
	 * Next 4 bytes will always be connectionID of the person the color is changing.
	 * Next 4 bytes will always be the color.
	 */
	
	private Color playerColor;
	
	public TCPClientPlayerColorPacket(int connectionID, Color color) {
		super(EnumTCPPacketType.PLAYER_COLOR_PACKET, connectionID);
		playerColor = color;
	}

	@Override
	protected byte[] prepare() {
		ByteBuffer packetData = ByteBuffer.allocate(getByteLength());
		packetData.putInt(getPacketType().ordinal());
		packetData.putInt(playerConnectionID);
		packetData.putInt(playerColor.toInt());
		isPreparedForShipment = true;
		return (preparedData = packetData.array());
	}
	
	public Color getColor() {
		return playerColor;
	}
}
