package com.codingparty.component.callback;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

import com.codingparty.camera.CameraMatrixManager;
import com.codingparty.file.setting.ProgramSettings;

public class WindowSizeCallback extends GLFWWindowSizeCallback {

	@Override
	public void invoke(long window, int width, int height) {
		if (!ProgramSettings.isFullscreenEnabled()) {
			ProgramSettings.updateWindowSize(width, height);
		}
		CameraMatrixManager.buildProjectionMatrix();
	}
}
