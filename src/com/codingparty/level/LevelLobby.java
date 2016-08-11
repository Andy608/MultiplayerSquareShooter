package com.codingparty.level;

import com.codingparty.connection.ConnectedUser;
import com.codingparty.core.CodingParty;
import com.codingparty.entity.EntityPaper;
import com.codingparty.world.World;

public class LevelLobby extends AbstractLevel {
	
	private EntityPaper paper;
	
	public LevelLobby() {
		super(EnumLevelType.LOBBY);
		paper = new EntityPaper();
	}
	
	@Override
	public void update(double deltaTime) {
		resetObjects();
		//update timer
		//update positions
		//check for collision etc
	}
	
	@Override
	public void resetLevel() {
		resetObjects();
		//reset timers
		//reset object positions
	}
	
	private void resetObjects() {
		levelComponents.clear();
		
		levelComponents.add(paper);
		
		World world = CodingParty.getWorld();
		ConnectedUser[] connectedUsers = world.getGameManager().getUsers();
		
		for (int i = 0; i < connectedUsers.length; i++) {
			if (connectedUsers[i] != null) {
				levelComponents.add(connectedUsers[i].getEntityPlayer());
			}
		}
	}
}
