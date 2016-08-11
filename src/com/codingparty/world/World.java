package com.codingparty.world;

import java.util.Random;

import com.codingparty.camera.Camera;
import com.codingparty.camera.FreeRoamCamera;
import com.codingparty.component.callback.CursorPosCallback;
import com.codingparty.connection.GameManager;
import com.codingparty.entity.EntityPlayerSP;
import com.codingparty.file.setting.ProgramSettings;

import math.Vector3f;

public class World {

	public static final Random rand = new Random();
	
	private boolean centerMouse;
	private boolean isPaused;
	
	private EntityPlayerSP player;
	
	private GameManager gameManager;
	private WorldRenderer worldRenderer;
	
	private Camera debugCamera;
	
	public World() {
		gameManager = new GameManager();
		gameManager.connectToServer();
		debugCamera = new FreeRoamCamera(new Vector3f(0, 1, 0), new Vector3f(90, 0, 0));
		
		player = new EntityPlayerSP();
		player.setColor((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
		
		centerMouse = false;
		isPaused = false;
		
		worldRenderer = new WorldRenderer();
	}
	
	public void update(double deltaTime) {
		if (isPaused) return;
		
		if (centerMouse) {
			CursorPosCallback.centerMouse();
		}
		
		if (ProgramSettings.isDebugEnabled()) {
			centerMouse = true;
			debugCamera.update(deltaTime);
		}
		else {
			centerMouse = false;
			player.update(deltaTime);
		}
		
		gameManager.update(deltaTime);
//		ModelResourceManager.update();
		
//		System.out.println(player.getPosition());
//		System.out.println(camera.getPosition());
	}
	
	public void render(double deltaTime) {
		worldRenderer.render(this);
		player.getCamera().render(deltaTime);
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}
	
	public EntityPlayerSP getPlayer() {
		return player;
	}
	
	public Camera getDebugCamera() {
		return debugCamera;
	}
}
