package com.codingparty.component.callback;

import org.lwjgl.glfw.GLFWScrollCallback;

public class ScrollCallback extends GLFWScrollCallback {

	private static double scrollOffset;
	private static int minOffset;
	private static int maxOffset;
	
	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		if ((minOffset == 0 && maxOffset == 0) || (scrollOffset < maxOffset && yoffset > 0) || (scrollOffset > minOffset && yoffset < 0)) {
			scrollOffset += yoffset;
		}
		resetOffsetLimits();
	}
	
	public static void setOffsetLimits(int min, int max) {
		minOffset = min;
		maxOffset = max;
	}

	public static void resetOffsetLimits() {
		maxOffset = 0;
		minOffset = 0;
	}
	
	public static double getScrollOffset() {
		return scrollOffset;
	}
}
