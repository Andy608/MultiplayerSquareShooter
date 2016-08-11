package com.codingparty.entity;

import com.codingparty.model.Models;

import math.Vector3f;

public class EntityPaper extends Entity {

	public EntityPaper() {
		this(new Vector3f(), new Vector3f());
	}
	
	public EntityPaper(Vector3f position, Vector3f rotation) {
		super(position, rotation, Models.paperModel, new Vector3f(8.5f * 4, 1f, 11f * 4));
	}

	@Override
	public void update(double deltaTime) {}
}
