package com.codingparty.component.callback;

import math.Vector2f;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.codingparty.component.Window;
import com.codingparty.file.setting.ProgramSettings;

public class CursorPosCallback extends GLFWCursorPosCallback {

	private static Vector2f mouseOffset = new Vector2f();
	private static Vector2f mousePos = new Vector2f();
	private static Vector2f mouseOffsetFromPoint = new Vector2f();
	
	@Override
	public void invoke(long windowID, double xpos, double ypos) {
		mousePos.set((float)xpos, (float)ypos);
		
		int rawWidth = ProgramSettings.getCurrentWindowWidth();
		int rawHeight = ProgramSettings.getCurrentWindowHeight();
		int evenWidth = ((rawWidth & 1) == 0) ? rawWidth : rawWidth - 1;
		int evenHeight = ((rawHeight & 1) == 0) ? rawHeight : rawHeight - 1;
		
		mouseOffset.set((float)(xpos - (evenWidth / 2f)), (float)(ypos - (evenHeight / 2f)));
	}
	
	public static float getMouseOffsetX() {
		return mouseOffset.x;
	}
	
	public static float getMouseOffsetY() {
		return mouseOffset.y;
	}
	
	public static float getMouseOffsetXFromPoint(float x) {
		mouseOffsetFromPoint.setX((float)(x - mousePos.x));
		return mouseOffsetFromPoint.x;
	}
	
	public static float getMouseOffsetYFromPoint(float y) {
		mouseOffsetFromPoint.setY((float)(y - mousePos.y));
		return mouseOffsetFromPoint.y;
	}
	
	public static float getMousePosX() {
		return mousePos.x;
	}
	
	public static float getMousePosY() {
		return mousePos.y;
	}
	
	public static void centerMouse() {
		int width = ProgramSettings.getCurrentWindowWidth();
		int height = ProgramSettings.getCurrentWindowHeight();
		GLFW.glfwSetCursorPos(Window.getID(), width / 2f, height / 2f);
	}
}
