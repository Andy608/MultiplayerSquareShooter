package com.codingparty.packet.udp;

import java.nio.ByteBuffer;

public class UDPServerPlayerStateUpdatePacket extends AbstractUDPClientPacket {

	/**
	 * ::Packet Headers::
	 * First 4 bytes will always be EnumPacketType.
	 */
	private int playerConnectionID;
	public float positionX, positionY, positionZ;
	public float rotationX, rotationY, rotationZ;
	private long initialTime;
	
	
	public UDPServerPlayerStateUpdatePacket(int connectionID, long timeStamp, float posX, float posY, float posZ, float rotX, float rotY, float rotZ) {
		super(EnumUDPPacketType.PLAYER_POSITION_UPDATE_PACKET);
		playerConnectionID = connectionID;
		initialTime = timeStamp;
		positionX = posX;
		positionY = posY;
		positionZ = posZ;
		rotationX = rotX;
		rotationY = rotY;
		rotationZ = rotZ;
	}

	@Override
	protected byte[] prepare() {
		ByteBuffer packetData = ByteBuffer.allocate(getByteLength());
		packetData.putInt(getPacketType().ordinal());
		packetData.putInt(playerConnectionID);
		packetData.putLong(initialTime);
		packetData.putFloat(positionX);
		packetData.putFloat(positionY);
		packetData.putFloat(positionZ);
		packetData.putFloat(rotationX);
		packetData.putFloat(rotationY);
		packetData.putFloat(rotationZ);
		isPreparedForShipment = true;
		return (preparedData = packetData.array());
	}
	
	public int getPlayerConnectionID() {
		return playerConnectionID;
	}
	
	public double getInitialTime() {
		return initialTime;
	}
}
