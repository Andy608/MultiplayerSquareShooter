package com.codingparty.core;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.codingparty.camera.CameraMatrixManager;
import com.codingparty.component.Window;
import com.codingparty.component.callback.CursorPosCallback;
import com.codingparty.file.FileUtil;
import com.codingparty.level.Levels;
import com.codingparty.logger.LoggerUtil;
import com.codingparty.model.Models;
import com.codingparty.model.util.ModelResourceManager;
import com.codingparty.packet.tcp.TCPIncomingBundleManager;
import com.codingparty.packet.tcp.TCPOutgoingBundleManager;
import com.codingparty.packet.udp.UDPIncomingPacketManager;
import com.codingparty.texture.Spritesheets;
import com.codingparty.texture.TextureLoader;
import com.codingparty.world.World;

public class CodingParty {
	
	private static CodingParty instance;
	
	private static final int TICKS_PER_SECOND = 60;
	private static final double TIME_SLICE = 1 / (double)TICKS_PER_SECOND;
	private static final float LAG_CAP = 0.15f;
	private int ticks;
	private int frames;
	
	public static void main(String[] args) {
		String path = (new File(CodingParty.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getParentFile().getPath();
		String decodedPath;
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			System.setProperty("org.lwjgl.librarypath", decodedPath + FileUtil.getFileSeparator(false));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		LoggerUtil.init();
		Thread.setDefaultUncaughtExceptionHandler(LoggerUtil.getInstance());
		instance = new CodingParty();
		instance.run();
	}
	
	private CodingParty() {
		LoggerUtil.logInfo(this.getClass(), "Creating " + Info.NAME + " " + Info.VERSION + " by " + Info.AUTHOR);
	}
	
	
	private void run() {
		LoggerUtil.logInfo(this.getClass(), "Initializing internal structure. Currently running on LWJGL " + Sys.getVersion() + ".");
		
		try {
			FileResourceTracker.init();
			Window.init(Info.NAME + " | " + Info.AUTHOR + " | "+ Info.VERSION);
			Window.buildScreen();
			CameraMatrixManager.init();
			GL.createCapabilities();
			ModelResourceManager.init();
			TextureLoader.init();
			Spritesheets.init();
			Models.buildModels();
			Levels.buildLevels();
//			TileList.initTiles();
			loop();
		} catch (Exception e) {
			LoggerUtil.logError(getClass(), e);
		} finally {
			cleanUp();
			System.exit(0);
		}
	}
	
	private static World world;
	/////////////////////////////
	
	private void loop() {
		world = new World();
		CursorPosCallback.centerMouse();
//		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		/////////////////////////////
		
		double lastTime;
		double currentTime;
		double deltaTime;
		double accumulatedTime = 0.0d;
		
		lastTime = GLFW.glfwGetTime();
		while (GLFW.glfwWindowShouldClose(Window.getID()) == GL11.GL_FALSE) {
			
			currentTime = GLFW.glfwGetTime();
			deltaTime = currentTime - lastTime;
			lastTime = currentTime;
			accumulatedTime += deltaTime;
			
			if (accumulatedTime > LAG_CAP) {
				accumulatedTime = LAG_CAP;
			}
			
			while (accumulatedTime >= TIME_SLICE) {
				accumulatedTime -= TIME_SLICE;
				GLFW.glfwPollEvents();
				update(TIME_SLICE);
			}
			
			
			if (world.getGameManager().isConnected()) {
				render(TIME_SLICE); //TODO: Fix the lerp.
			}
		}
	}
	
	private void update(double deltaTime) {
		
		if (ticks % 3 == 0) {
			TCPIncomingBundleManager.update();
			TCPOutgoingBundleManager.update();
		}
		UDPIncomingPacketManager.update(deltaTime);
		
		if (world.getGameManager().isConnected()) {
			world.update(deltaTime);
		}
		
		tick();
	}
	
	private int totalFPS, averageFPS, counter;
	
	private void tick() {
		ticks++;
		if (ticks % 60 == 0) {
			counter++;
			totalFPS += frames;
			averageFPS = totalFPS / counter;
//			LoggerUtil.logInfo(getClass(), "Ticks: " + ticks + " | FPS: " + frames + " | Average FPS: " + averageFPS);
			ticks = 0;
			frames = 0;
			
			if (counter % 100 == 0) {
//				LoggerUtil.logInfo(getClass(), "Reset Average FPS counter.");
				averageFPS = 0;
				totalFPS = 0;
				counter = 0;
			}
		}
	}
	
	private void render(double deltaTime) {
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL13.GL_MULTISAMPLE);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		frames++;
		world.render(deltaTime);
		GLFW.glfwSwapBuffers(Window.getID());
	}
	
	public static World getWorld() {
		return world;
	}
	
	public static CodingParty getInstance() {
		return instance;
	}
	
	public static int getTicks() {
		return instance.ticks;
	}
	
	public static void cleanUp() {
		world.getGameManager().disconnectFromServer();
		Window.save();
		FileResourceTracker.releaseResource(ModelResourceManager.getInstance());
		FileResourceTracker.releaseProgramResources();
		System.gc();
	}
}
