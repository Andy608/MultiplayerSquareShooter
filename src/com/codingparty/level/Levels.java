package com.codingparty.level;

import java.util.ArrayList;

import com.codingparty.logger.LoggerUtil;

public class Levels {

	private static ArrayList<AbstractLevel> levels = new ArrayList<>();
	private static LevelLobby lobby;
	
	public static void buildLevels() {
		lobby = new LevelLobby();
		
		registerLevels();
	}
	
	private static void registerLevels() {
		levels.add(lobby);
	}
	
	public static AbstractLevel getLevelByID(int id) {
		if (id >= 0 && id < levels.size()) {
			return levels.get(id);
		}
		else {
			LoggerUtil.logWarn(Levels.class, "Level ID is invalid. Returning lobby level.");
			return lobby;
		}
	}
	
	public static int getLevelAmount() {
		return levels.size();
	}
	
	public static LevelLobby getLobby() {
		return lobby;
	}
}
