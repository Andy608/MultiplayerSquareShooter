package com.codingparty.packet.udp;

import java.nio.ByteBuffer;

public class UDPClientPlayerControlUpdatePacket extends AbstractUDPClientPacket {

	/**
	 * ::Packet Headers::
	 * First 4 bytes will always be EnumPacketType.
	 */
	private int playerConnectionID;
	private long initialTime;
	public final boolean isForwardPressed;
	public final boolean isBackPressed;
	public final boolean isLeftPressed;
	public final boolean isRightPressed;
	
	public UDPClientPlayerControlUpdatePacket(int connectionID, long timeStamp, boolean forward, boolean back, boolean left, boolean right) {
		super(EnumUDPPacketType.PLAYER_CONTROL_UPDATE_PACKET);
		playerConnectionID = connectionID;
		initialTime = timeStamp;
		isForwardPressed = forward;
		isBackPressed = back;
		isLeftPressed = left;
		isRightPressed = right;
	}

	@Override
	protected byte[] prepare() {
		ByteBuffer packetData = ByteBuffer.allocate(getByteLength());
		packetData.putInt(getPacketType().ordinal());
		packetData.putInt(playerConnectionID);
		packetData.putLong(initialTime);
		packetData.put((byte) (isForwardPressed ? 1 : 0));
		packetData.put((byte) (isBackPressed ? 1 : 0));
		packetData.put((byte) (isLeftPressed ? 1 : 0));
		packetData.put((byte) (isRightPressed ? 1 : 0));
		isPreparedForShipment = true;
		return (preparedData = packetData.array());
	}
	
	public int getPlayerConnectionID() {
		return playerConnectionID;
	}
	
	public long getInitialTime() {
		return initialTime;
	}
}
