package com.codingparty.file.setting;

public abstract class FileSetting {

	private String settingName;
	
	public FileSetting(String name) {
		settingName = name;
	}
	
	public String getSettingName() {
		return settingName;
	}
}
