package com.codingparty.level;

import java.util.ArrayList;

import com.codingparty.entity.Entity;

public abstract class AbstractLevel {

	private EnumLevelType levelType;
	protected ArrayList<Entity> levelComponents;
	//private int levelTimer;
	
	public AbstractLevel(EnumLevelType type) {
		levelType = type;
		levelComponents = new ArrayList<>();
	}
	
	public abstract void update(double deltaTime);
	
	public abstract void resetLevel();
	
	public EnumLevelType getLevelType() {
		return levelType;
	}
	
	public ArrayList<Entity> getLevelComponents() {
		return levelComponents;
	}
	
	@Override
	public String toString() {
		return levelType.toString();
	}
}
