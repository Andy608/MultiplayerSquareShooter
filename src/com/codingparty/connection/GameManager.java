package com.codingparty.connection;

import java.io.IOException;

import com.codingparty.core.CodingParty;
import com.codingparty.entity.EntityPlayer;
import com.codingparty.entity.EntityPlayerMP;
import com.codingparty.entity.EntityPlayerSP;
import com.codingparty.entity.util.Color;
import com.codingparty.file.setting.ControlSettings;
import com.codingparty.level.AbstractLevel;
import com.codingparty.level.LevelManager;
import com.codingparty.level.Levels;
import com.codingparty.logger.LoggerUtil;
import com.codingparty.packet.tcp.TCPClientLoginPacket;
import com.codingparty.packet.tcp.TCPClientPacketBundle;
import com.codingparty.packet.tcp.TCPClientPlayerColorPacket;
import com.codingparty.packet.tcp.TCPOutgoingBundleManager;
import com.codingparty.packet.udp.UDPClientPlayerControlUpdatePacket;

public class GameManager {

	//In the future get this from database.
	private String hostIP;
	///////////////////////////////////////
	private TCPConnectionThread tcpConnection;
	private ConnectedUser[] users;
	private LevelManager levelManager;
	
	private UDPPacketSender UDPPacketSender;
	private UDPPacketListener UDPPacketListener;
	
	public GameManager() {
		hostIP = "108.5.54.131";
		levelManager = new LevelManager();
		tcpConnection = new TCPConnectionThread(hostIP, 4406);
		
		try {
			UDPPacketSender = new UDPPacketSender(hostIP, 4407);
			UDPPacketListener = new UDPPacketListener("227.214.1.225", 4409);
			UDPPacketListener.startListening();
		} catch (IOException e) {
			LoggerUtil.logError(getClass(), e);
			CodingParty.cleanUp();
			System.exit(-1);
		}
	}
	
	public synchronized void initUsers(int serverCapacity) {
		if (users == null) {
			System.out.println("Server Capacity = " + serverCapacity);
			users = new ConnectedUser[serverCapacity];
		}
		else {
			LoggerUtil.logWarn(GameManager.class, "Users list has already been created.");
		}
	}
	
	public synchronized void setLevel(int levelID) {
		levelManager.setLevel(Levels.getLevelByID(levelID));
	}
	
	public AbstractLevel getLevel() {
		return levelManager.getCurrentLevel();
	}
	
	public synchronized boolean addUser(int connectionID) {
		if (users != null && users[connectionID] == null) {
			System.out.println("ADDING USER: " + connectionID);
			EntityPlayer player;
			
			if (connectionID == tcpConnection.getConnectionID()) {
				player = new EntityPlayerSP();
			}
			else {
				player = new EntityPlayerMP();
			}
			users[connectionID] = new ConnectedUser(connectionID, player);
			return true;
		}
		else {
			LoggerUtil.logInfo(GameManager.class, "CANNOT ADD USER TO LIST.");
			return false;
		}
	}
	
	public synchronized void removeUser(int connectionID) {
		if (users != null && users[connectionID] != null) {
			System.out.println("REMOVING USER: " + connectionID);
			users[connectionID] = null;
		}
	}
	
	public synchronized void setNameForUser(int connectionID, String name) {
		if (users != null && users[connectionID] != null) {
			users[connectionID].setUsername(name);
		}
	}
	
	public void updatePlayerColor(Color color) {
		System.out.println("GameManager.updatePlayerColor() " + color);
		TCPOutgoingBundleManager.addPacketToBundle(new TCPClientPlayerColorPacket(tcpConnection.getConnectionID(), color));
	}
	
	public void updatePlayerPosition(int connectionID, float posX, float posY, float posZ, float rotX, float rotY, float rotZ, double deltaTime) {
		if (users != null && users[connectionID] != null) {
			
			if (connectionID == tcpConnection.getConnectionID()) {
				//Check if client pos is valid. Else move client pos back to server pos.
				EntityPlayerSP entityPlayerSP = (EntityPlayerSP)users[connectionID].getEntityPlayer();
				entityPlayerSP.updatePosition(posX, posY, posZ);
			}
			else {
				//Set the incoming positions to the new values. Interpolate between previous-previous position and previous position.
				EntityPlayerMP entityPlayerMP = (EntityPlayerMP)users[connectionID].getEntityPlayer();
				entityPlayerMP.updatePosition(posX, posY, posZ);
				entityPlayerMP.updateRotation(rotX, rotY, rotZ);
			}
		}
	}
	
	public void setUserColor(int connectionID, Color newColor) {
		if (users != null && users[connectionID] != null) {
			users[connectionID].getEntityPlayer().setColor(newColor);
		}
		else if (connectionID == tcpConnection.getConnectionID()) {
			CodingParty.getWorld().getPlayer().setColor(newColor);
		}
	}
	
	public synchronized ConnectedUser[] getUsers() {
		return users;
	}
	
	public synchronized void update(double deltaTime) {
		levelManager.update(deltaTime);
		for (int i = 0; i < users.length; i++) {
			if (users[i] != null) {
				users[i].getEntityPlayer().update(deltaTime);
			}
		}
		
		//TODO: Only send this when player presses a key AND is in a game.
		int connectionID = tcpConnection.getConnectionID();
//			EntityPlayer player = users[connectionID].getEntityPlayer();
//			System.out.println("SENDING POS: " + player.getX() + " | " + player.getY() + " | " + player.getZ());
		UDPClientPlayerControlUpdatePacket controlPacket = new UDPClientPlayerControlUpdatePacket(connectionID, System.nanoTime(), ControlSettings.moveForwardKey.isPressed(), ControlSettings.moveBackwardKey.isPressed(), ControlSettings.moveLeftKey.isPressed(), ControlSettings.moveRightKey.isPressed());
		UDPPacketSender.sendUpdatePacket(controlPacket);
	}
	
	public synchronized void connectToServer() {
		tcpConnection.start();
	}
	
	public synchronized boolean isConnected() {
		return tcpConnection.hasReceivedInitPackets();
	}
	
	public synchronized TCPConnectionThread getConnectionThread() {
		return tcpConnection;
	}
	
	public synchronized void disconnectFromServer() {
		TCPClientPacketBundle exitBundle = new TCPClientPacketBundle();
		exitBundle.addPacketToBundle(new TCPClientLoginPacket(tcpConnection.getConnectionID(), false));
		TCPOutgoingBundleManager.shipBundle(exitBundle);
		UDPPacketListener.stopListening();
	}
}
