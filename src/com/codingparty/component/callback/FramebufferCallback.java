package com.codingparty.component.callback;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL11;

public class FramebufferCallback extends GLFWFramebufferSizeCallback {

	@Override
	public void invoke(long window, int width, int height) {
		GL11.glViewport(0, 0, width, height);
	}
}
