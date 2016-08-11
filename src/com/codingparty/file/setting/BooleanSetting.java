package com.codingparty.file.setting;

public class BooleanSetting extends FileSetting {

	private boolean defaultSetting;
	private boolean customSetting;
	
	public BooleanSetting(String settingName, boolean defaultValue) {
		super(settingName);
		defaultSetting = defaultValue;
		customSetting = defaultValue;
	}
	
	public void setCustomBoolean(boolean customBoolean) {
		customSetting = customBoolean;
	}
	
	public void resetBoolean() {
		customSetting = defaultSetting;
	}
	
	public boolean getDefaultBoolean() {
		return defaultSetting;
	}
	
	public boolean getCustomBoolean() {
		return customSetting;
	}
}
