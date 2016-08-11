package com.codingparty.packet.old;

public class TCPIncomingPacketHandler {
	
//	public static void decipherIncomingPacketData(TCPConnectionThread connectionThread, byte[] packetData) {
//		
////		System.out.println("Deciphering packet from bytes...");
//		EnumTCPPacketType packetType = TCPPacketUtil.getEnum(packetData);
//		
//		switch(packetType) {
//		case LOG_IN_OFF_PACKET:
//			executeIncomingLogOnOffPacket(connectionThread, packetData);
//			break;
//		case SET_ID_PACKET:
//			executeIncomingSetConnectionIDPacket(connectionThread, packetData);
//			break;
//		case LEVEL_CHANGE_PACKET:
//			executeIncomingLevelChangePacket(connectionThread, packetData);
//			break;
//		case NAME_CHANGE_PACKET:
//			executeIncomingNameChangePacket(connectionThread, packetData);
//			break;
//		case SERVER_CAPACITY_PACKET:
//			executeIncomingServerCapacityPacket(connectionThread, packetData);
//			break;
//		case PLAYER_COLOR_CHANGE_PACKET:
//			executeIncomingPlayerColorChangePacket(connectionThread, packetData);
//			break;
//		default:
//			LoggerUtil.logWarn(TCPIncomingPacketHandler.class, "A packet could not be deciphered from the data given.");
//			return;
//		}
//	}
//	
//	private static void executeIncomingLogOnOffPacket(TCPConnectionThread connectionThread, byte[] packetData) {
//		int connectionID = TCPPacketUtil.getConnectionID(packetData);
//		
//		boolean isLoggingOn = (packetData[8] == 1 ? true : false);
//		System.out.println("RECEIVED LOG ON/OFF PACKET: " + isLoggingOn);
//		
////		System.out.println("\nReceived log on/off packet from server: " + "\nUserID = " + userID + "\nisLoggingOn = " + isLoggingOn);
//		//TODO: Handle what to do when a user logs in or out!
//		//Go through the list of players in the level and remove the one that has logged off,
//		//Or add one that has logged on.
//		
//		if (isLoggingOn) {
//			CodingParty.getCurrentWorld().getGameManager().addUser(connectionID);
//		}
//		else {
//			CodingParty.getCurrentWorld().getGameManager().removeUser(connectionID);
//		}
//	}
//	
//	private static void executeIncomingSetConnectionIDPacket(TCPConnectionThread connectionThread, byte[] packetData) {
//		int connectionID = TCPPacketUtil.getConnectionID(packetData);
//		System.out.println("RECEIVED SET CONNECTION ID. CONNECTION ID = " + connectionID);
//		connectionThread.setConnectionID(connectionID);
//	}
//	
//	private static void executeIncomingLevelChangePacket(TCPConnectionThread connectionThread, byte[] packetData) {
//		int levelID = TCPPacketUtil.getInt(packetData, 4);
//		System.out.println("RECEIVED LEVEL CHANGE PACKET. Level = " + Levels.getLevelByID(levelID).getLevelInfo().getLevelName());
//		CodingParty.getCurrentWorld().getGameManager().setLevel(levelID);
//	}
//	
//	private static void executeIncomingNameChangePacket(TCPConnectionThread connectionThread, byte[] packetData) {
//		int connectionID = TCPPacketUtil.getConnectionID(packetData);
//		
//		byte[] nameInBytes = new byte[packetData.length - 8];
//		for (int i = 0; i < nameInBytes.length; i++) {
//			nameInBytes[i] = packetData[i + 8];
//		}
//		String updatedName = new String(nameInBytes);
//		
//		CodingParty.getCurrentWorld().getGameManager().setNameForUser(connectionID, updatedName);
//		System.out.println("RECEIVED NAME CHANGE PACKET. Name = " + updatedName);
//	}
//	
//	private static void executeIncomingServerCapacityPacket(TCPConnectionThread connectionThread, byte[] packetData) {
//		int serverCapacity = TCPPacketUtil.getInt(packetData, 8);
//		System.out.println("RECEIVED SERVER CAPACITY PACKET. CAPACITY = " + serverCapacity);
//		CodingParty.getCurrentWorld().getGameManager().initUsers(serverCapacity);
//	}
//	
//	private static void executeIncomingPlayerColorChangePacket(TCPConnectionThread connectionThread, byte[] packetData) {
//		int connectionID = TCPPacketUtil.getConnectionID(packetData);
//		Color newColor = Color.fromInt(TCPPacketUtil.getInt(packetData, 8));
//		System.out.println("RECEIVED PLAYER COLOR CHANGE PACKET. COLOR = " + newColor);
//		CodingParty.getCurrentWorld().getGameManager().setUserColor(connectionID, newColor);
//	}
}
