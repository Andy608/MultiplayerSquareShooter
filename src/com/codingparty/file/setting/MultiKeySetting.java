package com.codingparty.file.setting;

import java.util.LinkedHashSet;

public class MultiKeySetting extends KeySetting {

	public static final int MAX_COMBO = 3;
	private int[] defaultKey;
	private int[] customKey;
	
	public MultiKeySetting(String settingName, int... defaultBinding) {
		super(settingName);
		defaultKey = defaultBinding;
		customKey = defaultBinding.clone();
	}
	
	public void setCustomKeyBinding(int[] customKeyBinding) {
		if (customKeyBinding == null || customKeyBinding.length < 1) {
			resetKeyBinding();
		}
		else if (customKeyBinding.length > MAX_COMBO) {
			int[] snippedKeyBinding = new int[MAX_COMBO];
			
			for (int i = 0; i < MAX_COMBO; i++) {
				snippedKeyBinding[i] = customKeyBinding[i];
			}
			customKey = snippedKeyBinding;
		}
		else {
			customKey = customKeyBinding;
		}
	}
	
	public boolean equalsControl(LinkedHashSet<Integer> keys) {
		if (keys == null || keys.size() == 0) return false;
		if (keys.size() != customKey.length) return false;

		for (int i = 0; i < customKey.length; i++) {
			if (!keys.contains(customKey[i])) return false;
		}
		return true;
	}
	
	public void resetKeyBinding() {
		customKey = defaultKey.clone();
	}
	
	public int[] getDefaultKeyBinding() {
		return defaultKey;
	}
	
	public int[] getCustomKeyBinding() {
		return customKey;
	}
	
	public String getReadableCustomKeyBinding() {
		StringBuilder b = new StringBuilder();
		
		b.append(customKey[0]);
		for (int i = 1; i < customKey.length; i++) {
			b.append(',').append(customKey[i]);
		}
		return b.toString();
	}
}
