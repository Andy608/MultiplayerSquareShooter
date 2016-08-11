package com.codingparty.file.setting;

import com.codingparty.math.MathHelper;

public class ClampedIntegerSetting extends IntegerSetting {

	private final int MIN, MAX;
	
	/**
	 * @param fileName : The name that will be displayed when saved in a file.
	 * @param min : Should be less than the max.
	 * @param max : Should be greater than the min.
	 * @param defaultInteger : The default value. Should be in between the min and max.
	 * @throws IllegalArgumentException when the min is greater than the max, max is less than the min, or min is equal to the max.
	 */
	public ClampedIntegerSetting(String fileName, int defaultInteger, int min, int max) throws IllegalArgumentException {
		super(fileName);
		defaultSetting = MathHelper.clampInt(defaultInteger, min, max);
		MIN = min;
		MAX = max;
	}
	
	@Override
	public void setCustomInteger(int customInteger) {
		customSetting = MathHelper.clampInt(customInteger, MIN, MAX);
	}

	public int getMax() {
		return MAX;
	}
	
	public int getMin() {
		return MIN;
	}
}
