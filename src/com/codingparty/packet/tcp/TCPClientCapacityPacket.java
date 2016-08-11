package com.codingparty.packet.tcp;

import java.nio.ByteBuffer;

public class TCPClientCapacityPacket extends AbstractTCPClientPacket {

	/**
	 * ::Packet Data::
	 * First 4 bytes will always be EnumPacketType
	 * Next 4 bytes will always be the capacity of the server.
	 */
	
	private int serverCapacity;
	
	public TCPClientCapacityPacket(int capacity) {
		super(EnumTCPPacketType.SERVER_CAPACITY_PACKET);
		serverCapacity = capacity;
	}

	@Override
	protected byte[] prepare() {
		ByteBuffer packetData = ByteBuffer.allocate(getByteLength());
		packetData.putInt(getPacketType().ordinal());
		packetData.putInt(serverCapacity);
		isPreparedForShipment = true;
		return (preparedData = packetData.array());
	}
	
	public int getCapacity() {
		return serverCapacity;
	}
}