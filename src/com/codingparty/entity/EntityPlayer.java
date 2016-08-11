package com.codingparty.entity;

import com.codingparty.model.Models;

import math.Vector3f;

public abstract class EntityPlayer extends Entity {
	
	protected static final float MAX_SPEED = 10f;
	protected static final float TURN_SPEED = 60f;
	
	protected Vector3f targetPosition;
	protected Vector3f serverPosition;
	protected boolean lerpToPosition;
	
	protected Vector3f serverRotation;
	protected Vector3f targetRotation;
	protected boolean lerpToRotation;
	
	public EntityPlayer(Vector3f position) {
		super(position, Models.playerModel);
		
		targetPosition = new Vector3f(position);
		serverPosition = new Vector3f();
		lerpToPosition = false;
		
		targetRotation = new Vector3f(rotation);
		serverRotation = new Vector3f();
		lerpToRotation = false;
	}
}
