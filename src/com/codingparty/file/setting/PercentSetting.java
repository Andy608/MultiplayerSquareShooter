package com.codingparty.file.setting;

import com.codingparty.math.MathHelper;

public class PercentSetting extends FileSetting {

	private final float MIN_PERCENT, MAX_PERCENT;
	
	public final float DEFAULT_VALUE;
	private float defaultPercent;
	private float customPercent;
	
	/**
	 * @param settingName : The name saved in the file.
	 * @param startingValue : The default value that the percentages will manipulate.
	 * @param defaultPercentage : The percentage used if it hasn't been changed yet/is no custom percentage.
	 * @param minPercentage : The minimum percentage allowed to manipulate the starting value.
	 * @param maxPercentage : The maximum percentage allowed to manipulate the starting value.
	 */
	public PercentSetting(String settingName, float startingValue, float defaultPercentage, float minPercentage, float maxPercentage) {
		super(settingName);
		DEFAULT_VALUE = startingValue;
		defaultPercent = MathHelper.clampFloat(defaultPercentage, minPercentage, maxPercentage);
		customPercent = defaultPercentage;
		MIN_PERCENT = minPercentage;
		MAX_PERCENT = maxPercentage;
	}
	
	/**
	 * Sets the custom percentage and clamps it to fit within the boundaries. Ex: 0.8 = 80%
	 * @param customPercent : The custom percentage value in decimal form.
	 */
	public void setCustomPercent(float percent) {
		customPercent = MathHelper.clampFloat(percent, MIN_PERCENT, MAX_PERCENT);
	}
	
	public void resetPercent() {
		customPercent = defaultPercent;
	}
	
	public float getDefaultPercent() {
		return defaultPercent;
	}
	
	public float getCustomPercent() {
		return customPercent;
	}
	
	public float getMaxPercent() {
		return MAX_PERCENT;
	}
	
	public float getMinPercent() {
		return MIN_PERCENT;
	}
	
	/**
	 * Calculates what the value would be at the current percentage.
	 */
	public float getValue() {
		return DEFAULT_VALUE * (customPercent / 100.0f);
	}
}
