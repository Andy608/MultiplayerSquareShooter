package com.codingparty.component.callback;

import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.opengl.GL11;

public class WindowFocusCallback extends GLFWWindowFocusCallback {

	@Override
	public void invoke(long window, int focused) {
		if (focused == GL11.GL_FALSE) {
			//Pause and save the game.
			//Pause Music.
			System.out.println("Unfocused!");
		}
		else {
			//Unpause the game.
			//Play Music.
			System.out.println("Focused!");
		}
	}
}
