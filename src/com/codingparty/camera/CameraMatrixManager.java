package com.codingparty.camera;

import com.codingparty.file.setting.ProgramSettings;
import com.codingparty.logger.LoggerUtil;

import math.Matrix4f;
import math.Vector2f;

public class CameraMatrixManager {

	private static CameraMatrixManager instance;
	private Matrix4f perspectiveMatrix;
	private Matrix4f orthographicMatrix;
	
	//TEMP
	private static final float NEAR_PLANE = 0.01f;
	private static final float FAR_PLANE = 200.0f;
	
	private static final float VIEW_DISTANCE = 5f;
	//////
	
	private CameraMatrixManager() {
		perspectiveMatrix = new Matrix4f();
		orthographicMatrix = new Matrix4f();
	}
	
	public static void init() {
		if (instance == null) {
			instance = new CameraMatrixManager();
			buildProjectionMatrix();
		}
		else {
			LoggerUtil.logWarn(CameraMatrixManager.class, instance.getClass().getSimpleName() + ".class is already initialized.");
		}
	}
	
	public static void buildProjectionMatrix() {
		if (ProgramSettings.isPerspectiveView()) {
			buildPerspectiveMatrix();
		}
		else {
			buildOrthographicMatrix();
		}
	}
	
	private static void buildPerspectiveMatrix() {
		if (instance == null) init();
		
		Vector2f windowSize = new Vector2f(ProgramSettings.getCurrentWindowWidth(), ProgramSettings.getCurrentWindowHeight());
		float aspectRatio = (float)windowSize.x / (float)windowSize.y;
		float FOV = ProgramSettings.getCustomFOV();
		
		instance.perspectiveMatrix.setIdentity();
		instance.perspectiveMatrix.m00 = 1f / (float)(Math.tan(Math.toRadians(FOV) / 2f));
		instance.perspectiveMatrix.m11 = aspectRatio / (float)(Math.tan(Math.toRadians(FOV) / 2f));
		instance.perspectiveMatrix.m22 = (NEAR_PLANE + FAR_PLANE) / (NEAR_PLANE - FAR_PLANE);
		instance.perspectiveMatrix.m23 = -1f;
		instance.perspectiveMatrix.m32 = (2 * NEAR_PLANE * FAR_PLANE) / (NEAR_PLANE - FAR_PLANE);
		instance.perspectiveMatrix.m33 = 0f;
	}
	
	private static void buildOrthographicMatrix() {
		if (instance == null) init();
		
		Vector2f windowSize = new Vector2f(ProgramSettings.getCurrentWindowWidth(), ProgramSettings.getCurrentWindowHeight());
		float aspectRatio = (float)windowSize.x / (float)windowSize.y;
		
		instance.orthographicMatrix.setIdentity();
		instance.orthographicMatrix.m00 = 1f / VIEW_DISTANCE;
		instance.orthographicMatrix.m11 = aspectRatio / VIEW_DISTANCE;
		instance.orthographicMatrix.m22 = -2f / (FAR_PLANE - NEAR_PLANE);
		instance.orthographicMatrix.m32 = -((FAR_PLANE + NEAR_PLANE) / (FAR_PLANE - NEAR_PLANE));
		instance.orthographicMatrix.m33 = 1f;
	}
	
	public static Matrix4f getProjectionMatrix() {
		if (ProgramSettings.isPerspectiveView()) {
			return instance.perspectiveMatrix;
		}
		else {
			return instance.orthographicMatrix;
		}
	}
}
