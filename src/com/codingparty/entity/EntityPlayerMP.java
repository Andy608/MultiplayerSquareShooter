package com.codingparty.entity;

import com.codingparty.math.MathHelper;

import math.Vector3f;

public class EntityPlayerMP extends EntityPlayer {
	
//	private Vector3f previousPosition;
//	private Vector3f previousRotation;
	
	public EntityPlayerMP() {
		super(new Vector3f(0, 1, 0));
//		previousPosition = new Vector3f();
//		previousRotation = new Vector3f();
	}
	
	@Override
	public void update(double deltaTime) {
		
		if (lerpToPosition) {
//			previousPosition.x = MathHelper.lerp(previousPosition.x, targetPosition.x, 0.1f);
//			previousPosition.y = MathHelper.lerp(previousPosition.y, targetPosition.y, 0.1f);
//			previousPosition.z = MathHelper.lerp(previousPosition.z, targetPosition.z, 0.1f);
			
//			position.x = MathHelper.lerp(position.x, previousPosition.x, 0.1f);
//			position.y = MathHelper.lerp(position.y, previousPosition.y, 0.1f);
//			position.z = MathHelper.lerp(position.z, previousPosition.z, 0.1f);
			
			targetPosition.x = MathHelper.lerp(targetPosition.x, serverPosition.x, 0.06f);
			targetPosition.y = MathHelper.lerp(targetPosition.y, serverPosition.y, 0.06f);
			targetPosition.z = MathHelper.lerp(targetPosition.z, serverPosition.z, 0.06f);
			
			position.x = MathHelper.lerp(position.x, targetPosition.x, 0.06f);
			position.y = MathHelper.lerp(position.y, targetPosition.y, 0.06f);
			position.z = MathHelper.lerp(position.z, targetPosition.z, 0.06f);
			
			if (MathHelper.distanceSquared(serverPosition, position) < 0.001f) {
				lerpToPosition = false;
			}
		}
		
		if (lerpToRotation) {
//			previousRotation.x = MathHelper.lerp(previousRotation.x, targetRotation.x, 0.1f);
//			previousRotation.y = MathHelper.lerp(previousRotation.y, targetRotation.y, 0.1f);
//			previousRotation.z = MathHelper.lerp(previousRotation.z, targetRotation.z, 0.1f);
//			
//			rotation.x = MathHelper.lerp(rotation.x, previousRotation.x, 0.1f);
//			rotation.y = MathHelper.lerp(rotation.y, previousRotation.y, 0.1f);
//			rotation.z = MathHelper.lerp(rotation.z, previousRotation.z, 0.1f);
			
			targetRotation.x = MathHelper.lerp(targetRotation.x, serverRotation.x, 0.06f);
			targetRotation.y = MathHelper.lerp(targetRotation.y, serverRotation.y, 0.06f);
			targetRotation.z = MathHelper.lerp(targetRotation.z, serverRotation.z, 0.06f);
			
			rotation.x = MathHelper.lerp(rotation.x, targetRotation.x, 0.06f);
			rotation.y = MathHelper.lerp(rotation.y, targetRotation.y, 0.06f);
			rotation.z = MathHelper.lerp(rotation.z, targetRotation.z, 0.06f);
			
			if (MathHelper.distanceSquared(serverRotation, rotation) < 0.001f) {
				lerpToRotation = false;
			}
		}
	}
	
	public void updatePosition(float posX, float posY, float posZ) {
//		previousPosition.set(targetPosition);
		targetPosition.set(serverPosition);
		serverPosition.set(posX, posY, posZ);
		
		if (MathHelper.distanceSquared(targetPosition, serverPosition) > 0f) {
			lerpToPosition = true;
		}
	}
	
	public void updateRotation(float rotX, float rotY, float rotZ) {
//		previousRotation.set(targetRotation);
		targetRotation.set(serverRotation);
		serverRotation.set(rotX, rotY, rotZ);
		
		if (MathHelper.distanceSquared(targetRotation, serverRotation) > 0f) {
			lerpToRotation = true;
		}
	}

	//Get position and shit from server.
}
