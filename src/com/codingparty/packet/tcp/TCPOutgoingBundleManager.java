package com.codingparty.packet.tcp;

import java.util.ArrayList;

import com.codingparty.core.CodingParty;
import com.codingparty.logger.LoggerUtil;

public class TCPOutgoingBundleManager {
	
	private static TCPClientPacketBundle bundle;

	public static synchronized void update() {
		if (bundle != null && !bundle.isBundleShipped() && !bundle.isEmpty()) {
			shipBundle(bundle);
		}
	}
	
	public static synchronized void addPacketToBundle(AbstractTCPClientPacket packet) {
		if (bundle == null || bundle.isBundleShipped()) {
			bundle = new TCPClientPacketBundle();
		}
		bundle.addPacketToBundle(packet);
	}
	
	public static synchronized void shipBundle(TCPClientPacketBundle bundle) {
		ArrayList<AbstractTCPClientPacket> bundlePackets = bundle.getPackets();
		
		System.out.println("\n=============== BUNDLE START ===============");
		
		for (int i = 0; i < bundlePackets.size(); i++) {
			EnumTCPPacketType packetType = bundlePackets.get(i).getPacketType();
			
			switch(packetType) {
			case LOGIN_PACKET:
				loginPacketUpdate((TCPClientLoginPacket)bundlePackets.get(i));
				break;
			case PLAYER_COLOR_PACKET:
				playerColorPacketUpdate((TCPClientPlayerColorPacket)bundlePackets.get(i));
				break;
			default:
				LoggerUtil.logWarn(TCPIncomingBundleManager.class, "The outgoing packet is not in the desired format. Type: " + packetType);
				return;
			}
		}
		
		System.out.println("================ BUNDLE END ================\n");
		
		//Send bundle to server
		CodingParty.getWorld().getGameManager().getConnectionThread().sendBundle(bundle);
	}
	
	private static synchronized void loginPacketUpdate(TCPClientLoginPacket packet) {
		int loginConnectionID = packet.getPlayerConnectionID();
		System.out.println("SENDING LOGIN PACKET: " + loginConnectionID + " IS LOGGING " + (packet.isLoggingIn() ? "IN" : "OUT") + ".");
	}
	
	private static synchronized void playerColorPacketUpdate(TCPClientPlayerColorPacket packet) {
		int playerConnectionID = packet.getPlayerConnectionID();
		System.out.println("SENDING PLAYER COLOR PACKET: " + playerConnectionID + " COLOR: " + packet.getColor());
		CodingParty.getWorld().getGameManager().setUserColor(playerConnectionID, packet.getColor());
	}
}
