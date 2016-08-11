package com.codingparty.camera;

import math.Vector3f;

public abstract class LerpCamera extends Camera {

	protected Vector3f lerpedPosition;
	
	public LerpCamera() {
		this(new Vector3f());
	}
	
	public LerpCamera(Vector3f pos) {
		this(pos, new Vector3f());
	}
	
	public LerpCamera(Vector3f pos, Vector3f rot) {
		super(pos, rot);
		lerpedPosition = new Vector3f();
	}
	
	public abstract void render(double deltaTime);
	
	public Vector3f getLerpPosition() {
		return lerpedPosition;
	}
}
