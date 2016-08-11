package com.codingparty.packet.tcp;

import java.nio.ByteBuffer;

import com.codingparty.level.EnumLevelType;
import com.codingparty.logger.LoggerUtil;

public class TCPClientLevelPacket extends AbstractTCPClientPacket {

	/**
	 * ::Packet Data::
	 * First 4 bytes will always be EnumPacketType
	 * Next 4 bytes will always be the levelID.
	 */
	
	private EnumLevelType levelType;
	
	public TCPClientLevelPacket(int levelID) {
		this (getLevelType(levelID));
	}
	
	public TCPClientLevelPacket(EnumLevelType level) {
		super(EnumTCPPacketType.LEVEL_PACKET);
		levelType = level;
	}

	private static EnumLevelType getLevelType(int levelID) {
		EnumLevelType[] enumVals = EnumLevelType.values();
		if (levelID >= 0 && levelID < enumVals.length) {
			return enumVals[levelID];
		}
		else {
			LoggerUtil.logWarn(TCPClientLevelPacket.class, "Invalid LevelID... Returning LobbyID");
			return EnumLevelType.LOBBY;
		}
	}
	
	@Override
	protected byte[] prepare() {
		ByteBuffer packetData = ByteBuffer.allocate(getByteLength());
		packetData.putInt(getPacketType().ordinal());
		packetData.putInt(levelType.getLevelID());
		isPreparedForShipment = true;
		return (preparedData = packetData.array());
	}
	
	public EnumLevelType getLevelType() {
		return levelType;
	}
}
