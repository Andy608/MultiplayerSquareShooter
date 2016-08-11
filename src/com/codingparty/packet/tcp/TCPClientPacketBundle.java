package com.codingparty.packet.tcp;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.codingparty.packet.EnumPacketPriorityLevel;

public class TCPClientPacketBundle {

	/**
	 * ::Packet Headers::
	 * First 4 bytes will always be the number of packets in the bundle.
	 * Next 4 bytes will always be EnumPacketType for packet #1. etc.
	 */
	
	private static int MAX_TCP_BUNDLE_BYTE_LENGTH = 1400;
	private static int PACKET_HEADER_BYTE_LENGTH = 4;
	private ByteBuffer bundleData;
	private int bundleByteLength;
	private boolean isPreparedforShipment;
	private volatile boolean isBundleShipped;
	private ArrayList<AbstractTCPClientPacket> packets;
	
	public TCPClientPacketBundle() {
		packets = new ArrayList<>();
		isBundleShipped = false;
		isPreparedforShipment = false;
	}
	
	public void addPacketToBundle(AbstractTCPClientPacket packet) {
		if (MAX_TCP_BUNDLE_BYTE_LENGTH >= bundleByteLength + packet.getByteLength() + PACKET_HEADER_BYTE_LENGTH) {
			if (!isBundleShipped && packets.add(packet)) {
				bundleByteLength += packet.getByteLength();
			}
		}
	}
	
	public byte[] getPacketData() {
		if (!isPreparedforShipment) {
			isPreparedforShipment = true;
			bundleData = ByteBuffer.allocate(PACKET_HEADER_BYTE_LENGTH + bundleByteLength);
			bundleData.putInt(packets.size());
			
			for (int priority = EnumPacketPriorityLevel.VERY_HIGH.ordinal(); priority <= EnumPacketPriorityLevel.LOW.ordinal(); priority++) {
				for (int packetIndex = 0; packetIndex < packets.size(); packetIndex++) {

					if (packets.get(packetIndex).getPacketType().getPriority().ordinal() == priority) {
						byte[] packetData = packets.get(packetIndex).getPacketData();
						
						for (int packetDataIndex = 0; packetDataIndex < packetData.length; packetDataIndex++) {
							bundleData.put(packetData[packetDataIndex]);
						}
					}
				}
			}
		}
		return bundleData.array();
	}
	
	public boolean isBundleShipped() {
		return isBundleShipped;
	}
	
	public synchronized void setBundleSent() {
		isBundleShipped = true;
		for (int i = 0; i < packets.size(); i++) {
			packets.get(i).setPacketSent();
		}
	}
	
	public ArrayList<AbstractTCPClientPacket> getPackets() {
		return packets;
	}
	
	public int getBundleByteLength() {
		return bundleByteLength;
	}
	
	public boolean isEmpty() {
		return packets.isEmpty();
	}
}
