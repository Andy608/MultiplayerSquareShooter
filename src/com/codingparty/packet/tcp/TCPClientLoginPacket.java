package com.codingparty.packet.tcp;

import java.nio.ByteBuffer;

public class TCPClientLoginPacket extends AbstractTCPClientPlayerUpdatePacket {

	/**
	 * ::Packet Data::
	 * First 4 bytes will always be EnumPacketType
	 * Next 4 bytes will always be connectionID of the person logging in/out.
	 * Next 1 byte will always be a boolean. True = login, False = logout.
	 */
	
	private boolean isLoggingIn;
	
	public TCPClientLoginPacket(int connectionID, boolean login) {
		super(EnumTCPPacketType.LOGIN_PACKET, connectionID);
		isLoggingIn = login;
	}

	@Override
	protected byte[] prepare() {
		ByteBuffer packetData = ByteBuffer.allocate(getByteLength());
		packetData.putInt(getPacketType().ordinal());
		packetData.putInt(playerConnectionID);
		packetData.put((byte) (isLoggingIn ? 1 : 0));
		isPreparedForShipment = true;
		return (preparedData = packetData.array());
	}

	public boolean isLoggingIn() {
		return isLoggingIn;
	}
}
