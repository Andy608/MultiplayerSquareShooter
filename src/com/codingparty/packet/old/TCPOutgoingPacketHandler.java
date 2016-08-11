package com.codingparty.packet.old;

public class TCPOutgoingPacketHandler {

//	public static synchronized void sendPacket(TCPConnectionThread connectionThread, AbstractTCPPacket packet) {
//		EnumTCPPacketType packetType = packet.getPacketType();
//		
//		switch(packetType) {
//		case LOG_IN_OFF_PACKET:
//			executeOutgoingLogOnOffPacket(connectionThread, packet.getPreparedData());
//			break;
//		case NAME_CHANGE_PACKET:
//			executeOutgoingNameChangePacket(connectionThread, packet.getPreparedData());
//			break;
//		case SERVER_CAPACITY_PACKET:
//			executeOutgoingServerCapacityPacket(connectionThread, packet.getPreparedData());
//			break;
//		case PLAYER_COLOR_CHANGE_PACKET:
//			exeuteOutgoingPlayerColorChangePacket(connectionThread, packet.getPreparedData());
//			break;
//		default:
//			LoggerUtil.logWarn(TCPIncomingPacketHandler.class, "A packet could not be deciphered from the data given.");
//			return;
//		}
//	}
//	
//	private static synchronized void executeOutgoingLogOnOffPacket(TCPConnectionThread connectionThread, byte[] packetData) {
//		boolean isLoggingOn = (packetData[8] == 1 ? true : false);
//		LoggerUtil.logInfo(TCPOutgoingPacketHandler.class, "SENDING LOG ON/OFF PACKET: LOG IN/OFF = " + isLoggingOn);
//		connectionThread.sendPacket(packetData, packetData.length);
//	}
//	
//	private static synchronized void executeOutgoingNameChangePacket(TCPConnectionThread connectionThread, byte[] packetData) {
//		byte[] nameInBytes = new byte[packetData.length - 8];
//		for (int i = 0; i < nameInBytes.length; i++) {
//			nameInBytes[i] = packetData[i + 8];
//		}
//		String updatedName = new String(nameInBytes);
//		LoggerUtil.logInfo(TCPOutgoingPacketHandler.class, "SENDING NAME CHANGE PACKET: NAME = " + updatedName);
//		connectionThread.sendPacket(packetData, packetData.length);
//	}
//	
//	private static synchronized void executeOutgoingServerCapacityPacket(TCPConnectionThread connectionThread, byte[] packetData) {
//		LoggerUtil.logInfo(TCPOutgoingPacketHandler.class, "SENDING REQUEST FOR SERVER CAPACITY...");
//		connectionThread.sendPacket(packetData, packetData.length);
//	}
//	
//	private static synchronized void exeuteOutgoingPlayerColorChangePacket(TCPConnectionThread connectionThread, byte[] packetData) {
//		System.out.println("ANAL");
//		Color newColor = Color.fromInt(TCPPacketUtil.getInt(packetData, 8));
//		System.out.println("SENDING PLAYER COLOR CHANGE PACKET: COLOR = " + newColor);
//		connectionThread.sendPacket(packetData, packetData.length);
//	}
}
