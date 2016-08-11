package com.codingparty.file.setting;

import math.Vector2f;

public class Vector2fSetting extends FileSetting {

	private final Vector2f defaultSetting;
	private Vector2f customSetting;
	
	public Vector2fSetting(String settingName, Vector2f defaultVector2f) {
		super(settingName);
		defaultSetting = defaultVector2f;
		customSetting = new Vector2f(defaultVector2f);
	}
	
	public void setCustomVector2f(Vector2f v) {
		customSetting.set(v);
	}
	
	public void setCustomVector2f(float x, float y) {
		customSetting.set(x, y);
	}
	
	public void resetVector2f() {
		customSetting.set(defaultSetting);
	}
	
	public Vector2f getDefaultVector2f() {
		return defaultSetting;
	}
	
	public Vector2f getCustomVector2f() {
		return customSetting;
	}
}
