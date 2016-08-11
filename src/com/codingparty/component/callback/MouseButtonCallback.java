package com.codingparty.component.callback;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import math.Vector2f;

public class MouseButtonCallback extends GLFWMouseButtonCallback {

	private static boolean isRightButtonDown;
	private static Vector2f pointClicked = new Vector2f();
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
	        if(GLFW.GLFW_PRESS == action) {
	        	isRightButtonDown = true;
	        	pointClicked.set(CursorPosCallback.getMousePosX(), CursorPosCallback.getMousePosY());
	        }
	        else if(GLFW.GLFW_RELEASE == action) {
	        	isRightButtonDown = false;
	        }
	    }
	}
	
	public static boolean isRightButtonDown() {
		return isRightButtonDown;
	}
	
	public static float getPointClickedX() {
		pointClicked.setX(CursorPosCallback.getMousePosX());
		return pointClicked.x;
	}
	
	public static float getPointClickedY() {
		pointClicked.setY(CursorPosCallback.getMousePosY());
		return pointClicked.y;
	}
	
	public static Vector2f getPointClicked() {
		return pointClicked;
	}
}
