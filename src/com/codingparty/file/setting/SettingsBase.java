package com.codingparty.file.setting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codingparty.file.FileUtil;
import com.codingparty.file.ResourceDirectory;
import com.codingparty.file.ResourceHelper;
import com.codingparty.file.SystemUtil;
import com.codingparty.file.SystemUtil.EnumOS;
import com.codingparty.logger.LoggerUtil;
import com.codingparty.math.ArrayUtil;

import math.Vector2f;

public abstract class SettingsBase {

	protected static final char DEFAULT_DELIMITER = '=', SEPARATOR = ',';
	protected static final EnumOS os = SystemUtil.getOSType();
	protected static final ResourceDirectory SETTINGS_DIR = new ResourceDirectory(ResourceHelper.GAME_APPDATA_DIRECTORY.getFullDirectory(), "settings", false);

	protected abstract void initDefaultSettings();
	public abstract void loadSettingsFromFile();
	protected abstract void setDefaultSettings();
	public abstract void storeSettingsInFile();
	
	/**
	 * Reads all information in from a settings file and breaks up each line into a string and puts it in an array of Strings.
	 * Then trims the array to take out any lines with blank space in them.
	 * @param settingsFile : The settings file.
	 * @return : an array of strings representing each line in the file.
	 */
	protected String[] readOptionsFile(File settingsFile) throws IOException {
		return trimExtraSpace(FileUtil.getAllLinesFromFileAsList(settingsFile));
	}
	
	protected String[] trimExtraSpace(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, list.get(i).trim());
			if (list.get(i).isEmpty()) {
				list.remove(i);
				i--;
			}
			else {
				list.set(i, list.get(i).replaceAll("\\s+",""));
			}
		}
		return list.toArray(new String[list.size()]);
	}
	
	protected Boolean parseBoolean(String s) {
		if (s != null) {
			if (s.equalsIgnoreCase("true")) return true;
			else if (s.equalsIgnoreCase("false")) return false;
		}
		return null;
	}
	
	protected boolean getBooleanValue(Boolean savedValue, boolean defaultValue) {
		if (savedValue == null) {
			LoggerUtil.logWarn(getClass(), "Boolean value cannot be null. Return default.");
			return defaultValue;
		}
		else return savedValue.booleanValue();
	}
	
	protected int getIntValue(Integer savedValue, int defaultValue) {
		if (savedValue == null) return defaultValue;
		else return savedValue.intValue();
	}
	
	protected Vector2f getVector2fValue(Vector2f savedValue, Vector2f defaultValue) throws IllegalStateException {
		if (defaultValue == null && savedValue == null) {
			throw new IllegalStateException("Both savedValue and defaultValue cannot be null! Cannot set value to a null reference.");
		}
		
		if (savedValue == null) return defaultValue;
		else return savedValue;
	}
	
	protected boolean getSingleBooleanFromSetting(String fileOption, boolean defaultValue, char delimiter) {
		return getBooleanValue(parseBoolean(fileOption.substring(fileOption.indexOf(delimiter) + 1)), defaultValue);
	}
	
	protected int getSingleIntegerFromSetting(String fileOption, int defaultValue, char delimiter) throws NumberFormatException {
		try {
			return Integer.parseInt(fileOption.substring(fileOption.indexOf(delimiter) + 1));
		} catch (NumberFormatException e) {
			LoggerUtil.logWarn(getClass(), e, "Could not parse integer from string. Returning default.", false);
			return defaultValue;
		}
	}
	
	protected int getSingleIntegerFromSetting(String fileOption, int defaultValue, char startingDelimiter, char endingDelimiter) {
		try {
			return Integer.parseInt(fileOption.substring(fileOption.indexOf(startingDelimiter) + 1, fileOption.indexOf(endingDelimiter)));
		} catch (NumberFormatException e) {
			LoggerUtil.logWarn(getClass(), e, "Could not parse integer from string. Returning default.", false);
			return defaultValue;
		}
	}
	
	protected float getSingleFloatFromSetting(String fileOption, float defaultValue, char delimiter) {
		try {
			return Float.parseFloat(fileOption.substring(fileOption.indexOf(delimiter) + 1));
		} catch (NumberFormatException e) {
			LoggerUtil.logWarn(getClass(), e, "Could not parse float from string. Returning default.", false);
			return defaultValue;
		}
	}
	
	protected int[] getMultipleIntegersFromSetting(String fileOption, int[] defaultValue, char startingDelimiter, char inBetweenDelimiter) throws NumberFormatException {
		List<Integer> customValues = new ArrayList<Integer>();
		
		try {
			int startDelimiterIndex = 0, endDelimiterIndex;
			char[] fileOptionCharArray = fileOption.toCharArray();
			for (int i = 0; i < fileOptionCharArray.length; i++) {
				if (fileOptionCharArray[i] == startingDelimiter) {
					startDelimiterIndex = i;
				}
				else if (fileOptionCharArray[i] == inBetweenDelimiter) {
					if (fileOptionCharArray[startDelimiterIndex] == startingDelimiter || fileOptionCharArray[startDelimiterIndex] == inBetweenDelimiter) {
						endDelimiterIndex = i;
						customValues.add(Integer.parseInt(fileOption.substring(startDelimiterIndex + 1, endDelimiterIndex)));
						startDelimiterIndex = i;
					}
				}
				else if (i == fileOptionCharArray.length - 1) {
					customValues.add(Integer.parseInt(fileOption.substring(startDelimiterIndex + 1)));
				}
			}
			return ArrayUtil.convertListToIntegerArray(customValues);
			
		} catch (NumberFormatException e) {
			LoggerUtil.logWarn(getClass(), e, "Could not parse multiple integers from string. Returning default.", false);
			return defaultValue;
		}
	}
}
