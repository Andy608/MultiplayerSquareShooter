package com.codingparty.component.callback;

import java.util.LinkedHashSet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import com.codingparty.core.CodingParty;
import com.codingparty.file.setting.ControlSettings;
import com.codingparty.file.setting.ProgramSettings;

public class KeyCallback extends GLFWKeyCallback {

	private static LinkedHashSet<Integer> currentKeys = new LinkedHashSet<>();
	
	private static boolean isUpArrowPressed;
	private static boolean isDownArrowPressed;
	private static boolean isLeftArrowPressed;
	private static boolean isRightArrowPressed;
	
	@Override
	public void invoke(long windowID, int key, int scancode, int action, int mods) {
		if (action == GLFW.GLFW_RELEASE) {
			currentKeys.remove(key);
			
			if (ControlSettings.moveForwardKey.getCustomKey() == key) {
				ControlSettings.moveForwardKey.setPressed(false);
			}
			else if (ControlSettings.moveBackwardKey.getCustomKey() == key) {
				ControlSettings.moveBackwardKey.setPressed(false);
			}
			else if (ControlSettings.moveLeftKey.getCustomKey() == key) {
				ControlSettings.moveLeftKey.setPressed(false);
			}
			else if (ControlSettings.moveRightKey.getCustomKey() == key) {
				ControlSettings.moveRightKey.setPressed(false);
			}
			else if (ControlSettings.moveUpKey.getCustomKey() == key) {
				ControlSettings.moveUpKey.setPressed(false);
			}
			else if (ControlSettings.moveDownKey.getCustomKey() == key) {
				ControlSettings.moveDownKey.setPressed(false);
			}
			else if (GLFW.GLFW_KEY_UP == key) {
				isUpArrowPressed = false;
			}
			else if (GLFW.GLFW_KEY_DOWN == key) {
				isDownArrowPressed = false;
			}
			else if (GLFW.GLFW_KEY_LEFT == key) {
				isLeftArrowPressed = false;
			}
			else if (GLFW.GLFW_KEY_RIGHT == key) {
				isRightArrowPressed = false;
			}
			else if (GLFW.GLFW_KEY_R == key) {
				CodingParty.getWorld().getPlayer().setColor((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
				CodingParty.getWorld().getGameManager().updatePlayerColor(CodingParty.getWorld().getPlayer().getColor());
			}
		}
		else if (action == GLFW.GLFW_PRESS) {
			currentKeys.add(key);
			
			if (ControlSettings.shutdownKeyBinding.equalsControl(currentKeys)) {
				GLFW.glfwSetWindowShouldClose(windowID, GL11.GL_TRUE);
			}
			else if (ControlSettings.moveForwardKey.getCustomKey() == key) {
				ControlSettings.moveForwardKey.setPressed(true);
			}
			else if (ControlSettings.moveBackwardKey.getCustomKey() == key) {
				ControlSettings.moveBackwardKey.setPressed(true);
			}
			else if (ControlSettings.moveLeftKey.getCustomKey() == key) {
				ControlSettings.moveLeftKey.setPressed(true);
			}
			else if (ControlSettings.moveRightKey.getCustomKey() == key) {
				ControlSettings.moveRightKey.setPressed(true);
			}
			else if (ControlSettings.moveUpKey.getCustomKey() == key) {
				ControlSettings.moveUpKey.setPressed(true);
			}
			else if (ControlSettings.moveDownKey.getCustomKey() == key) {
				ControlSettings.moveDownKey.setPressed(true);
			}
			else if (GLFW.GLFW_KEY_UP == key) {
				isUpArrowPressed = true;
			}
			else if (GLFW.GLFW_KEY_DOWN == key) {
				isDownArrowPressed = true;
			}
			else if (GLFW.GLFW_KEY_LEFT == key) {
				isLeftArrowPressed = true;
			}
			else if (GLFW.GLFW_KEY_RIGHT == key) {
				isRightArrowPressed = true;
			}
			else if (ControlSettings.fullscreenKey.getCustomKey() == key) {
				currentKeys.clear();
				ProgramSettings.updateFullscreen(!ProgramSettings.isFullscreenEnabled());
			}
			else if (ControlSettings.debugKey.getCustomKey() == key) {
				ProgramSettings.setDebug(!ProgramSettings.isDebugEnabled());
			}
		}
	}
	
	public static boolean isUpArrowPressed() {
		return isUpArrowPressed;
	}
	
	public static boolean isDownArrowPressed() {
		return isDownArrowPressed;
	}
	
	public static boolean isLeftArrowPressed() {
		return isLeftArrowPressed;
	}
	
	public static boolean isRightArrowPressed() {
		return isRightArrowPressed;
	}
}
