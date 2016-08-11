package com.codingparty.file.setting;

public class IntegerSetting extends FileSetting {

	protected int defaultSetting;
	protected int customSetting;
	
	protected IntegerSetting(String fileName) {
		super(fileName);
	}
	
	public IntegerSetting(String fileName, int defaultInteger) {
		super(fileName);
		defaultSetting = defaultInteger;
		customSetting = defaultInteger;
	}
	
	public void setCustomInteger(int customInteger) {
		customSetting = customInteger;
	}
	
	public void resetInteger() {
		customSetting = defaultSetting;
	}
	
	public int getDefaultInteger() {
		return defaultSetting;
	}
	
	public int getCustomInteger() {
		return customSetting;
	}
}
