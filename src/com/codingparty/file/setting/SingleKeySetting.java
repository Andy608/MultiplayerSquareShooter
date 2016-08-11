package com.codingparty.file.setting;

public class SingleKeySetting extends KeySetting {

	private int defaultKey;
	private int customKey;
	
	public SingleKeySetting(String settingName, int defaultKeyOption) {
		super(settingName);
		defaultKey = defaultKeyOption;
		customKey = defaultKeyOption;
	}
	
	public void setCustomKey(int customKeyOption) {
		customKey = customKeyOption;
	}
	
	public boolean equalsControl(int key) {
		return customKey == key;
	}
	
	public void resetKey() {
		customKey = defaultKey;
	}
	
	public int getDefaultKey() {
		return defaultKey;
	}
	
	public int getCustomKey() {
		return customKey;
	}
}
