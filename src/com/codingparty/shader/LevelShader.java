package com.codingparty.shader;

import com.codingparty.camera.Camera;
import com.codingparty.core.FileResourceTracker;
import com.codingparty.file.FileResourceLocation;
import com.codingparty.file.FileResourceLocation.EnumFileExtension;

public class LevelShader extends ShaderBase {

	private int cameraPositionID;
	private int debugCameraPositionID;
	private int entityColorID;
	
	public LevelShader() {
		super(new FileResourceLocation(RESOURCE_DIRECTORY, "levelVertexShader", EnumFileExtension.VS), new FileResourceLocation(RESOURCE_DIRECTORY, "levelFragmentShader", EnumFileExtension.FS));
		FileResourceTracker.addClass(this);
	}
	
	@Override
	public void bindUniformVariables() {
		super.bindUniformVariables();
		cameraPositionID = getUniformLocation("cameraPosition");
		debugCameraPositionID = getUniformLocation("debugCameraPosition");
		entityColorID = getUniformLocation("entityColor");
	}
	
	public void loadCameraPosition(Camera c) {
		super.loadFloats(cameraPositionID, c.getX(), c.getY(), c.getZ());
	}
	
	public void loadDebugCameraPosition(Camera c) {
		super.loadFloats(debugCameraPositionID, c.getX(), c.getY(), c.getZ());
	}
	
	public void loadEntityColor(float red, float green, float blue) {
		super.loadFloats(entityColorID, red, green, blue);
	}
}
