package com.codingparty.camera;

import com.codingparty.component.callback.CursorPosCallback;
import com.codingparty.component.callback.KeyCallback;
import com.codingparty.component.callback.MouseButtonCallback;
import com.codingparty.component.callback.ScrollCallback;
import com.codingparty.entity.Entity;
import com.codingparty.file.setting.ControlSettings;
import com.codingparty.file.setting.EnumRotateType;
import com.codingparty.file.setting.ProgramSettings;
import com.codingparty.math.MathHelper;
import com.codingparty.math.MatrixMathHelper;

import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

public class TargetedCamera extends LerpCamera {

	private static final float MAX_ROTATION_SPEED_X = 3f;
	private static final float MAX_ROTATION_SPEED_Y = 1f;
	private static final float DEFAULT_RADIUS = 10f;
	
	private Entity targetedEntity;
	private float sphereRadius;
	
	private Matrix4f cameraTransform;
	private Vector3f helpVector;
	private Vector4f position4f;
	
	public TargetedCamera() {
		this(null);
	}
	
	public TargetedCamera(Entity targetEntity) {
		this(targetEntity, DEFAULT_RADIUS);
	}
	
	public TargetedCamera(Entity targetEntity, float offset) {
		this(targetEntity, offset, new Vector3f(50, 90, 0));
	}
	
	public TargetedCamera(Entity targetEntity, float offset, Vector3f rot) {
		targetedEntity = targetEntity;
		sphereRadius = offset;
		rotation = rot;
		position = new Vector3f(0, 0, 0);
		position4f = new Vector4f(0, 0, 0, 1);
		cameraTransform = new Matrix4f();
		helpVector = new Vector3f();
	}
	
	@Override
	protected void createViewMatrix() {
		viewMatrix.setIdentity();
		translationVector.set(-position.x, -position.y, -position.z);
		viewMatrix.translate(translationVector);
		viewMatrix.rotate((float)Math.toRadians(rotation.x), MatrixMathHelper.X_AXIS);
		viewMatrix.rotate((float)Math.toRadians(rotation.y), MatrixMathHelper.Y_AXIS);
		viewMatrix.rotate((float)Math.toRadians(rotation.z), MatrixMathHelper.Z_AXIS);
	}
	

	@Override
	public void update(double deltaTime) {
		rotateCamera();
		moveCamera();
		createViewMatrix();
	}
	
	@Override
	public void render(double deltaTime) {
		ScrollCallback.setOffsetLimits(-10, 5);
		zoomAcceleration = -(float)ScrollCallback.getScrollOffset();
		
		zoomVelocity += zoomAcceleration * deltaTime;
		zoomVelocity *= 0.9f;
		if (Math.abs(zoomVelocity) < 0.01f) {
			zoomVelocity = 0;
		}
		
		zoomFactor = 1f + zoomVelocity;
		sphereRadius = DEFAULT_RADIUS * zoomFactor;
	}
	
	public void setTarget(Entity entity) {
		targetedEntity = entity;
	}
	
	public Entity getTarget() {
		return targetedEntity;
	}
	
	private void moveCamera() {
		if (targetedEntity != null) {
			cameraTransform.setIdentity();
			position4f.set(0, 0, 0, 1);
			
			//Translate camera to entity + offsetTarget
			helpVector.set(targetedEntity.getX(), targetedEntity.getY(), (targetedEntity.getZ() + sphereRadius));
			MatrixMathHelper.translateMatrix(cameraTransform, helpVector);
			
			helpVector.set(-targetedEntity.getX(), -targetedEntity.getY(), -targetedEntity.getZ());
			MatrixMathHelper.translateMatrix(cameraTransform, helpVector);
			
			MatrixMathHelper.rotateMatrix(cameraTransform, rotation);
			
			helpVector.set(targetedEntity.getX(), targetedEntity.getY(), targetedEntity.getZ());
			MatrixMathHelper.translateMatrix(cameraTransform, helpVector);
			
			Matrix4f.transform(cameraTransform, position4f, position4f);
			
			position.set(position4f.x, position4f.y, position4f.z);
		}
	}
	
	float xOffset = 0;
	float yOffset = 0;
	
	private void rotateCamera() {
		//WRITE NEW ONE TO INCORPORATE DRAG FROM MOUSE
		
		float xRotation = 0;
		float yRotation = 0;
		
		float defaultArrowSpeed = 30f;
		
//		float cameraYaw = MathHelper.boundedAngle(rotation.y, 360);
//		float entityRotY = MathHelper.boundedAngle(targetedEntity.getRotAboutY(), 360);
//		
//		float entityRotation = (entityRotY);
//
//		if (entityRotation != rotation.y && targetedEntity.isMoving()) {
//			rotation.y = cameraYaw + rotationVelocity.y;
//			rotation.y = MathHelper.lerp(rotation.y, (float)(-(camera.getYaw()) + 90f), 0.1f);
//			
//			rotation.y = MathHelper.lerp(rotation.y, entityRotation, 0.1f);
//			yRotation += MathHelper.clampFloat(ControlSettings.cameraRotationSensitivity.getValue() * (float)Math.toRadians(defaultArrowSpeed), -MAX_ROTATION_SPEED_X, MAX_ROTATION_SPEED_X);
//		}
		
		float rotationSpeedAdjustment = ((1f / sphereRadius) * DEFAULT_RADIUS);
		
		if (ProgramSettings.getRotateCameraOption().equals(EnumRotateType.BOTH) || ProgramSettings.getRotateCameraOption().equals(EnumRotateType.MOUSE)) {
			if (MouseButtonCallback.isRightButtonDown()) {
				Vector2f pointClicked = MouseButtonCallback.getPointClicked();
				
				if (xOffset != CursorPosCallback.getMouseOffsetXFromPoint(pointClicked.getX())) {
					xOffset = CursorPosCallback.getMouseOffsetXFromPoint(pointClicked.getX());
					yRotation = -MathHelper.clampFloat(ControlSettings.cameraRotationSensitivity.getValue() * (float)Math.toRadians(xOffset) * rotationSpeedAdjustment, -MAX_ROTATION_SPEED_X, MAX_ROTATION_SPEED_X);
				}
				else {
					pointClicked.x = CursorPosCallback.getMousePosX();
				}
				
				if (yOffset != CursorPosCallback.getMouseOffsetYFromPoint(pointClicked.getY())) {
					yOffset = CursorPosCallback.getMouseOffsetYFromPoint(pointClicked.getY());
					xRotation = -MathHelper.clampFloat(ControlSettings.cameraRotationSensitivity.getValue() * (float)Math.toRadians(yOffset) * rotationSpeedAdjustment, -MAX_ROTATION_SPEED_Y, MAX_ROTATION_SPEED_Y);
				}
				else {
					pointClicked.y = CursorPosCallback.getMousePosY();
				}
			}
		}
		
		if (ProgramSettings.getRotateCameraOption().equals(EnumRotateType.BOTH) || ProgramSettings.getRotateCameraOption().equals(EnumRotateType.KEYS)) {
			if (KeyCallback.isUpArrowPressed()) {
				xRotation += MathHelper.clampFloat(ControlSettings.cameraRotationSensitivity.getValue() * (float)Math.toRadians(defaultArrowSpeed) * rotationSpeedAdjustment, -MAX_ROTATION_SPEED_Y, MAX_ROTATION_SPEED_Y);
			}
			if (KeyCallback.isDownArrowPressed()) {
				xRotation += MathHelper.clampFloat(ControlSettings.cameraRotationSensitivity.getValue() * (float)Math.toRadians(-defaultArrowSpeed) * rotationSpeedAdjustment, -MAX_ROTATION_SPEED_Y, MAX_ROTATION_SPEED_Y);
			}
			if (KeyCallback.isLeftArrowPressed()) {
				yRotation += MathHelper.clampFloat(ControlSettings.cameraRotationSensitivity.getValue() * (float)Math.toRadians(defaultArrowSpeed) * rotationSpeedAdjustment, -MAX_ROTATION_SPEED_X, MAX_ROTATION_SPEED_X);
			}
			if (KeyCallback.isRightArrowPressed()) {
				yRotation += MathHelper.clampFloat(ControlSettings.cameraRotationSensitivity.getValue() * (float)Math.toRadians(-defaultArrowSpeed) * rotationSpeedAdjustment, -MAX_ROTATION_SPEED_X, MAX_ROTATION_SPEED_X);
			}
		}
		
		rotationAcceleration.set(xRotation, yRotation, 0);
		Vector3f.add(rotationVelocity, (Vector3f)rotationAcceleration, rotationVelocity);
		
		float rotVelLength = rotationVelocity.lengthSquared();
		if (rotVelLength != 0) {
			if (rotationAcceleration.lengthSquared() == 0) {
				friction.set(rotationVelocity).negate().scale(0.08f);
			}
			else {
				friction.set(rotationVelocity).negate().scale((1f / ControlSettings.cameraRotationSensitivity.getValue()));
			}
			Vector3f.add(rotationVelocity, friction, rotationVelocity);
			if (rotationVelocity.lengthSquared() < MIN_VELOCITY) rotationVelocity.set(0, 0, 0);
		}
		
		rotation.y += rotationVelocity.y;
		rotation.x = MathHelper.clampFloat(rotation.x + rotationVelocity.x, 10, 80);
	}
	
	public boolean isManuallyRotating() {
		return (KeyCallback.isUpArrowPressed() || KeyCallback.isDownArrowPressed() || KeyCallback.isLeftArrowPressed() || KeyCallback.isRightArrowPressed() || MouseButtonCallback.isRightButtonDown());
	}
}
