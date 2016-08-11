package com.codingparty.component;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import com.codingparty.component.callback.WindowCallbackManager;
import com.codingparty.core.FileResourceTracker;
import com.codingparty.core.IRelease;
import com.codingparty.file.setting.ControlSettings;
import com.codingparty.file.setting.ProgramSettings;
import com.codingparty.logger.LoggerUtil;

import math.Vector2f;

public class Window implements IRelease {

	private static Window instance;
	private long windowID;
	
	private static Vector2f primaryMonitorSize;
	private WindowCallbackManager callbackManager;
	private String windowTitle;
	
	private Window(String title) {
		instance = this;
		windowTitle = title;
	}
	
	public static void init(String title) {
		if (instance == null) {
			instance = new Window(title);
			FileResourceTracker.addClass(instance);
		}
		
		if (instance.windowID != MemoryUtil.NULL) {
			throw new IllegalStateException("A GLFW window is already created. Will not create another window for the current game thread.");
		}
		
		try {
			if (GLFW.glfwInit() == GL11.GL_FALSE) {
				throw new IllegalStateException("Unable to initialize GLFW.");
			}
			else {
				ByteBuffer primaryMonitor = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
				primaryMonitorSize = new Vector2f(GLFWvidmode.width(primaryMonitor), GLFWvidmode.height(primaryMonitor));
				
				ProgramSettings.init();
				ProgramSettings.getInstance().loadSettingsFromFile();
				
				ControlSettings.init();
				ControlSettings.getInstance().loadSettingsFromFile();
			}
		} catch (RuntimeException e) {
			LoggerUtil.logError(Window.class, e);
		}
	}
	
	public static void buildScreen() throws RuntimeException {
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		
		long displayHandle;
		
		if (ProgramSettings.isFullscreenEnabled()) {
			displayHandle = GLFW.glfwCreateWindow(getPrimaryMonitorWidth(), getPrimaryMonitorHeight(), instance.windowTitle, GLFW.glfwGetPrimaryMonitor(), instance.windowID);
		}
		else {
			displayHandle = GLFW.glfwCreateWindow(ProgramSettings.getCurrentWindowWidth(), ProgramSettings.getCurrentWindowHeight(), instance.windowTitle, MemoryUtil.NULL, (instance.windowID == MemoryUtil.NULL) ? MemoryUtil.NULL : instance.windowID);
		}
			
		if (displayHandle == MemoryUtil.NULL) {
			throw new RuntimeException("Failed to create GLFW window.");
		}
		
		if (instance.windowID != MemoryUtil.NULL) {
			instance.callbackManager.release();
			GLFW.glfwDestroyWindow(instance.windowID);
		}
		
		instance.windowID = displayHandle;
		
		if (!ProgramSettings.isFullscreenEnabled()) {
			GLFW.glfwSetWindowPos(instance.windowID, ProgramSettings.getCurrentWindowPositionX(), ProgramSettings.getCurrentWindowPositionY());
		}
		
		GLFW.glfwMakeContextCurrent(instance.windowID);
		GLFW.glfwSwapInterval(ProgramSettings.isVSyncEnabled() ? 1 : 0);
		GLFW.glfwShowWindow(instance.windowID);
		instance.callbackManager = new WindowCallbackManager(instance.windowID);
	}
	
	//TODO: Move this to a save handler. Make more robust.
	public static void save() {
		ProgramSettings.getInstance().storeSettingsInFile();
		ControlSettings.getInstance().storeSettingsInFile();
	}
	
	public static long getID() {
		return instance.windowID;
	}
	
	public static int getPrimaryMonitorWidth() {
		return (int)primaryMonitorSize.x;
	}
	
	public static int getPrimaryMonitorHeight() {
		return (int)primaryMonitorSize.y;
	}
	
	public static Window getWindow() {
		return instance;
	}
	
	@Override
	public void release() {
		LoggerUtil.logInfo(getClass(), "Releasing window resources.");
		callbackManager.release();
		GLFW.glfwDestroyWindow(instance.windowID);
		GLFW.glfwTerminate();
	}
	
	public static Window getInstance() {
		return instance;
	}
}
