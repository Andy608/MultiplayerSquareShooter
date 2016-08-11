package com.codingparty.camera;

import com.codingparty.component.callback.CursorPosCallback;
import com.codingparty.file.setting.ControlSettings;
import com.codingparty.math.MathHelper;
import com.codingparty.math.MatrixMathHelper;

import math.Vector3f;

public class FreeRoamCamera extends Camera {

	public FreeRoamCamera() {
		this(new Vector3f());
	}
	
	public FreeRoamCamera(Vector3f pos) {
		super(pos, new Vector3f());
	}
	
	public FreeRoamCamera(Vector3f pos, Vector3f rot) {
		super(pos, rot);
	}
	
	@Override
	public void update(double deltaTime) {
		acceleration.set(0, 0, 0);
		friction.set(0, 0, 0);
		moveCamera(deltaTime);
		rotateCamera();
		createViewMatrix();
	}
	
	@Override
	protected void createViewMatrix() {
		viewMatrix.setIdentity();
		viewMatrix.rotate((float)Math.toRadians(getPitch()), MatrixMathHelper.X_AXIS);
		viewMatrix.rotate((float)Math.toRadians(getYaw()), MatrixMathHelper.Y_AXIS);
		viewMatrix.rotate((float)Math.toRadians(getRoll()), MatrixMathHelper.Z_AXIS);
		translationVector.set(-position.x, -position.y, -position.z);
		viewMatrix.translate(translationVector);
	}
	
	private void moveCamera(double deltaTime) {
		if (ControlSettings.moveForwardKey.isPressed()) {
			acceleration.x += (float)(MathHelper.sin(getYaw())/* * MathHelper.cos(getPitch())*/);
//			acceleration.y -= (float)(MathHelper.sin(getPitch()));
			acceleration.z -= (float)(MathHelper.cos(getYaw())/* * MathHelper.cos(getPitch())*/);
		}
		
		if (ControlSettings.moveBackwardKey.isPressed()) {
			acceleration.x -= (float)(MathHelper.sin(getYaw())/* * MathHelper.cos(getPitch())*/);
//			acceleration.y += (float)(MathHelper.sin(getPitch()));
			acceleration.z += (float)(MathHelper.cos(getYaw())/* * MathHelper.cos(getPitch())*/);
		}
		
		 if (ControlSettings.moveLeftKey.isPressed()) {
			 acceleration.x -= (float)(MathHelper.sin(getYaw() + 90));
			 acceleration.z += (float)(MathHelper.cos(getYaw() + 90));
		 }
		
		 if (ControlSettings.moveRightKey.isPressed()) {
			 acceleration.x += (float)(MathHelper.sin(getYaw() + 90));
			 acceleration.z -= (float)(MathHelper.cos(getYaw() + 90));
		 }
		 
		 if (ControlSettings.moveUpKey.isPressed()) {
			 acceleration.y += 1f;
		 }
		 
		 if (ControlSettings.moveDownKey.isPressed()) {
			 acceleration.y -= 1f;
		 }
		
		double maxSpeed = MAX_SPEED * deltaTime;
		if (velocity.lengthSquared() >= maxSpeed * maxSpeed) {
			acceleration.set(0, 0, 0);
		}
		Vector3f.add(velocity, (Vector3f)(acceleration.scale((float)deltaTime)), velocity);
		
		float velocityLength = velocity.lengthSquared();
		if (velocityLength != 0) {
			friction.set(velocity);
			friction.normalise().negate().scale(0.005f);
			Vector3f.add(velocity, friction, velocity);
			if (velocity.lengthSquared() < MIN_VELOCITY) {
				velocity.set(0, 0, 0);
			}
		}
		Vector3f.add(position, velocity, position);
	}
	
	private void rotateCamera() {
		rotationAcceleration.set(ControlSettings.cameraRotationSensitivity.getValue() * (float)Math.toRadians(CursorPosCallback.getMouseOffsetX()), ControlSettings.cameraRotationSensitivity.getValue() * (float)Math.toRadians(CursorPosCallback.getMouseOffsetY()), 0);
		Vector3f.add(rotationVelocity, (Vector3f)rotationAcceleration, rotationVelocity);
		
		float rotVelLength = rotationVelocity.lengthSquared();
		if (rotVelLength != 0) {
			if (rotationAcceleration.lengthSquared() == 0) {
				friction.set(rotationVelocity).negate().scale(0.80f);
			}
			else {
				friction.set(rotationVelocity).negate().scale((1f / ControlSettings.cameraRotationSensitivity.getValue()));
			}
			Vector3f.add(rotationVelocity, friction, rotationVelocity);
			if (rotationVelocity.lengthSquared() < MIN_VELOCITY) rotationVelocity.set(0, 0, 0);
		}
		
		rotation.y += rotationVelocity.x;
		rotation.x = MathHelper.clampFloat(rotation.x + rotationVelocity.y, -90, 90);
	}
}
