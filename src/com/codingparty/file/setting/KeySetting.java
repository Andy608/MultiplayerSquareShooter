package com.codingparty.file.setting;

public abstract class KeySetting extends FileSetting {

	private boolean isPressed;
	
	public KeySetting(String settingName) {
		super(settingName);
		isPressed = false;
	}
	
	public boolean isPressed() {
		return isPressed;
	}
	
	public void setPressed(boolean b) {
		isPressed = b;
	}
}
