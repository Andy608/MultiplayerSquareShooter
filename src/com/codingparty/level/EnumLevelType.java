package com.codingparty.level;

public enum EnumLevelType {

	LOBBY(0, "Lobby");
	
	private int levelID;
	private String levelName;
	
	private EnumLevelType(int id, String name) {
		levelID = id;
		levelName = name;
	}
	
	public int getLevelID() {
		return levelID;
	}
	
	public String getLevelName() {
		return levelName;
	}
	
	@Override
	public String toString() {
		return levelName;
	}
}
