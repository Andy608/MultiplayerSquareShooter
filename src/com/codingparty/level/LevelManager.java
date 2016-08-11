package com.codingparty.level;

public class LevelManager {

	private LevelLobby defaultLevel;
	private AbstractLevel currentLevel;
	
	public LevelManager() {
		defaultLevel = Levels.getLobby();
	}
	
	public void restartLevel() {
		currentLevel.resetLevel();
	}
	
	public void setLevel(AbstractLevel newLevel) {
		if (newLevel == null) {
			currentLevel = defaultLevel;
		}
		else {
			currentLevel = newLevel;
		}
		currentLevel.resetLevel();
	}
	
	public void update(double deltaTime) {
		currentLevel.update(deltaTime);
	}
	
	public AbstractLevel getCurrentLevel() {
		return currentLevel;
	}
}
