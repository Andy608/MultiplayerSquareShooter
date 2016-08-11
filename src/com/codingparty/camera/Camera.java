package com.codingparty.camera;

import math.Matrix4f;
import math.Vector3f;

public abstract class Camera {

	protected static final float MAX_SPEED = 5f;
	protected static final float MIN_VELOCITY = 0.00001f;
	
	protected Vector3f acceleration;
	protected Vector3f velocity;
	protected Vector3f friction;
	
	protected Vector3f rotationAcceleration;
	protected Vector3f rotationVelocity;
	
	protected Matrix4f viewMatrix;
	protected Vector3f position;
	protected Vector3f rotation;
	
	protected Vector3f translationVector;
	
	protected float zoomFactor;
	protected float zoomAcceleration;
	protected float zoomVelocity;
	
	public Camera() {
		this(new Vector3f());
	}
	
	public Camera(Vector3f pos) {
		this(pos, new Vector3f());
	}
	
	public Camera(Vector3f pos, Vector3f rot) {
		position = pos;
		rotation = rot;
		viewMatrix = new Matrix4f();
		acceleration = new Vector3f();
		velocity = new Vector3f();
		friction = new Vector3f();
		
		rotationAcceleration = new Vector3f();
		rotationVelocity = new Vector3f();
		
		translationVector = new Vector3f();
		zoomFactor = 1.0f;
	}
	
	/**
	 * Should be called in the update method.
	 */
	protected abstract void createViewMatrix();
	
	public abstract void update(double deltaTime);
	
	/**
	 * @return : The x position of the camera.
	 */
	public float getX() {
		return position.x;
	}
	
	/**
	 * @return : The y position of the camera.
	 */
	public float getY() {
		return position.y;
	}
	
	/**
	 * @return : The z position of the camera.
	 */
	public float getZ() {
		return position.z;
	}
	
	/**
	 * Rotation about the x-axis. Negative degree = looking up.
	 * @return : The x rotation of the camera.
	 */
	public float getPitch() {
		return rotation.x;
	}
	
	/**
	 * Rotation about the y-axis. Turning your head left and right.
	 * @return : The y rotation of the camera.
	 */
	public float getYaw() {
		return rotation.y;
	}
	
	/**
	 * Rotation about the z-axis. Tilting your head sideways.
	 * @return : The z rotation of the camera.
	 */
	public float getRoll() {
		return rotation.z;
	}
	
	public void setPitch(float rotX) {
		rotation.x = rotX;
	}
	
	public void setYaw(float rotY) {
		rotation.y = rotY;
	}
	
	public void setRoll(float rotZ) {
		rotation.z = rotZ;
	}
	
	/**
	 * @return : The view matrix for this camera.
	 */
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
	public Vector3f getPosition() {
		return position;
	}
}
