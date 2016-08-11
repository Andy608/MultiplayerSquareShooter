package com.codingparty.entity;

import com.codingparty.camera.TargetedCamera;
import com.codingparty.file.setting.ControlSettings;
import com.codingparty.file.setting.ProgramSettings;
import com.codingparty.math.MathHelper;

import math.Vector2f;
import math.Vector3f;

public class EntityPlayerSP extends EntityPlayer {

	private static Vector2f helperVector;
	private float cameraYaw;
	private float rotationYRespectCamera;
	private TargetedCamera camera;
	
	public EntityPlayerSP() {
		this(new Vector3f(0, 1, 0), new TargetedCamera());
	}
	
	public EntityPlayerSP(Vector3f position, TargetedCamera c) {
		super(position);
		camera = c;
		helperVector = new Vector2f();
		camera.setTarget(this);
	}
	
	public void update(double deltaTime) {
		
		float yRotation = 0;
		
		cameraYaw = MathHelper.boundedAngle(camera.getYaw(), 360);
		
		if (ControlSettings.moveForwardKey.isPressed()) {
			acceleration.x += (float)(MathHelper.cos(rotation.y));
			acceleration.z -= (float)(MathHelper.sin(rotation.y));
			
			if (!camera.isManuallyRotating() && ProgramSettings.isCameraUsingStickyRotation()) {
				rotationYRespectCamera = MathHelper.boundedAngle(-rotation.y + 90, 360);
				MathHelper.makeAnglesClose(cameraYaw, rotationYRespectCamera, helperVector);
				camera.setYaw(MathHelper.lerp(helperVector.x, helperVector.y, 0.05f));
			}
		}
		
		if (ControlSettings.moveBackwardKey.isPressed()) {
			acceleration.x += (float)-(MathHelper.cos(rotation.y));
			acceleration.z -= (float)-(MathHelper.sin(rotation.y));
			
			if (!camera.isManuallyRotating() && ProgramSettings.isCameraUsingStickyRotation()) {
				rotationYRespectCamera = MathHelper.boundedAngle(-rotation.y - 90, 360);
				MathHelper.makeAnglesClose(cameraYaw, rotationYRespectCamera, helperVector);
				camera.setYaw(MathHelper.lerp(helperVector.x, helperVector.y, 0.05f));
			}
		}
		
		if (ControlSettings.moveLeftKey.isPressed()) {
//			rotation.y = MathHelper.lerp(rotation.y, rotation.y + 30f, 0.1f);
			yRotation += (float)Math.toRadians(TURN_SPEED);
		}
		
		if (ControlSettings.moveRightKey.isPressed()) {
//			rotation.y = MathHelper.lerp(rotation.y, rotation.y - 30f, 0.1f);
			yRotation -= (float)Math.toRadians(TURN_SPEED);
		}
		 
//		 if (ControlSettings.moveUpKey.isPressed()) {
//			 acceleration.y += 1f;
//		 }
		
		//TODO: SEE IF THERE IS A BETTER WAY TO DO THIS.
		if (ControlSettings.moveDownKey.isPressed()) {
			position.y = MathHelper.lerp(position.y, position.y - 1f, 0.1f);
			position.y = MathHelper.clampFloat(position.y, 0.2f, position.y); //TODO: THE MIN/MAX WILL BE A VALUE IN THE LEVEL CLASS
		}
		else {
			position.y = MathHelper.lerp(position.y, position.y + 1f, 0.1f);
			position.y = MathHelper.clampFloat(position.y, position.y, 1f); //TODO: THE MIN/MAX WILL BE A VALUE IN THE LEVEL CLASS
		}
		
		rotationAcceleration.set(0, yRotation, 0);
		Vector3f.add(rotationVelocity, (Vector3f)rotationAcceleration, rotationVelocity);
		 
		float rotVelLength = rotationVelocity.lengthSquared();
		if (rotVelLength != 0) {
			if (rotationAcceleration.lengthSquared() == 0) {
				friction.set(rotationVelocity).negate().scale(0.8f);
			}
			else {
				friction.set(rotationVelocity).negate().scale((1f / ControlSettings.cameraRotationSensitivity.DEFAULT_VALUE));
			}
			Vector3f.add(rotationVelocity, friction, rotationVelocity);
			if (rotationVelocity.lengthSquared() < MIN_VELOCITY) rotationVelocity.set(0, 0, 0);
		}
		
		rotation.y += rotationVelocity.y;
//		rotation.x = MathHelper.clampFloat(rotation.x + rotationVelocity.y, 10, 80);
		
		////////////////////////////////////////
		
		double maxSpeed = MAX_SPEED * deltaTime;
		if (velocity.lengthSquared() >= maxSpeed * maxSpeed) {
			acceleration.set(0, 0, 0);
		}
		Vector3f.add(velocity, (Vector3f)(acceleration.scale((float)deltaTime)), velocity);
		
		float velocityLength = velocity.lengthSquared();
		if (velocityLength != 0) {
			friction.set(velocity);
			friction.normalise().negate().scale(0.004f);
			Vector3f.add(velocity, friction, velocity);
			if (velocity.lengthSquared() < MIN_VELOCITY) {
				velocity.set(0, 0, 0);
			}
		}
		Vector3f.add(targetPosition, velocity, targetPosition);
		
		if (lerpToPosition) {
			position.x = MathHelper.lerp(position.x, serverPosition.x, 0.1f);
			position.y = MathHelper.lerp(position.y, serverPosition.y, 0.1f);
			position.z = MathHelper.lerp(position.z, serverPosition.z, 0.1f);
			
			if (MathHelper.distanceSquared(serverPosition, position) < 0.001f) {
				targetPosition.set(serverPosition);
				lerpToPosition = false;
			}
		}
		else {
			position.set(targetPosition);
		}
		
		camera.update(deltaTime);
	}
	
	public void updatePosition(float serverPosX, float serverPosY, float serverPosZ) {
		serverPosition.set(serverPosX, serverPosY, serverPosZ);
		if (MathHelper.distanceSquared(serverPosition, position) > 10f) {
			System.out.println("HELLO");
			lerpToPosition = true;
		}
	}
	
	public TargetedCamera getCamera() {
		return camera;
	}
}
