package com.codingparty.packet.tcp;

import java.nio.ByteBuffer;

public class TCPClientIDPacket extends AbstractTCPClientPlayerUpdatePacket {

	/**
	 * ::Packet Data::
	 * First 4 bytes will always be EnumPacketType
	 * Next 4 bytes will always be connectionID of the person logging in/out.
	 */
	
	public TCPClientIDPacket(int connectionID) {
		super(EnumTCPPacketType.ID_PACKET, connectionID);
	}

	@Override
	protected byte[] prepare() {
		ByteBuffer packetData = ByteBuffer.allocate(getByteLength());
		packetData.putInt(getPacketType().ordinal());
		packetData.putInt(playerConnectionID);
		isPreparedForShipment = true;
		return (preparedData = packetData.array());
	}
}
