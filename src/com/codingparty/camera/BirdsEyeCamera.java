package com.codingparty.camera;

import com.codingparty.file.setting.ControlSettings;
import com.codingparty.math.MatrixMathHelper;

import math.Vector3f;

public class BirdsEyeCamera extends Camera {

	public BirdsEyeCamera() {
		this(new Vector3f());
	}
	
	public BirdsEyeCamera(Vector3f pos) {
		this(pos, new Vector3f());
	}
	
	public BirdsEyeCamera(Vector3f pos, Vector3f rot) {
		super(pos, rot);
	}
	
	@Override
	public void update(double deltaTime) {
		acceleration.set(0, 0, 0);
		friction.set(0, 0, 0);
		moveCamera(deltaTime);
//		rotateCamera();
		createViewMatrix();
	}
	
	@Override
	protected void createViewMatrix() {
		viewMatrix.setIdentity();
		viewMatrix.rotate((float)Math.toRadians(getPitch()), MatrixMathHelper.X_AXIS);
		viewMatrix.rotate((float)Math.toRadians(getYaw()), MatrixMathHelper.Y_AXIS);
		viewMatrix.rotate((float)Math.toRadians(getRoll()), MatrixMathHelper.Z_AXIS);
//		viewMatrix.translate(new Vector3f(-position.x, -position.y, -position.z));
		translationVector.set(-position.x, -position.y, -position.z);
		viewMatrix.translate(translationVector);
	}
	
	private void moveCamera(double deltaTime) {
		if (ControlSettings.moveForwardKey.isPressed()) {
			acceleration.z -= 1f * deltaTime;
		}
		
		if (ControlSettings.moveBackwardKey.isPressed()) {
			acceleration.z += 1f * deltaTime;
		}
		
		if (ControlSettings.moveLeftKey.isPressed()) {
			acceleration.x += 1f * deltaTime;
		}
		
		if (ControlSettings.moveRightKey.isPressed()) {
		acceleration.x -= 1f * deltaTime;
		}
		 
		if (ControlSettings.moveUpKey.isPressed()) {
			acceleration.y += 1f * deltaTime;
		}
		 
		if (ControlSettings.moveDownKey.isPressed()) {
			acceleration.y -= 1f * deltaTime;
		}
		
		double maxSpeed = MAX_SPEED * deltaTime;
		if (velocity.lengthSquared() >= maxSpeed * maxSpeed) {
			acceleration.set(0, 0, 0);
		}
		
		Vector3f.add(velocity, (Vector3f)(acceleration), velocity);
		
		float velocityLength = velocity.lengthSquared();
		if (velocityLength != 0) {
			friction.set(velocity).normalise().negate().scale(0.005f);
			Vector3f.add(velocity, friction, velocity);
			
			if (velocity.lengthSquared() < MIN_VELOCITY) {
				velocity.set(0, 0, 0);
			}
		}
//			friction.set(velocity);
//			friction.normalise().negate().scale(0.005f);
//			Vector3f.add(velocity, friction, velocity);
//			if (velocity.lengthSquared() < MIN_VELOCITY) {
//				velocity.set(0, 0, 0);
//			}
		Vector3f.add(position, velocity, position);
	}

//	private void rotateCamera() {
//		rotationAcceleration.set(ControlSettings.mouseSensitivity.getValue() * (float)Math.toRadians(CursorPosCallback.getMouseOffsetX()), ControlSettings.mouseSensitivity.getValue() * (float)Math.toRadians(CursorPosCallback.getMouseOffsetY()), 0);
//		Vector3f.add(rotationVelocity, (Vector3f)rotationAcceleration, rotationVelocity);
//		
//		float rotVelLength = rotationVelocity.lengthSquared();
//		if (rotVelLength != 0) {
//			friction.set(rotationVelocity).negate().scale(0.5f);
//			Vector3f.add(rotationVelocity, friction, rotationVelocity);
//			if (rotationVelocity.lengthSquared() < MIN_VELOCITY) rotationVelocity.set(0, 0, 0);
//		}
//		
//		rotation.y += rotationVelocity.x;
//		rotation.x = MathHelper.clampFloat(rotation.x + rotationVelocity.y, -90, 90);
//	}
}
