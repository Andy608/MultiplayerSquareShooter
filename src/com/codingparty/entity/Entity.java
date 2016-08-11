package com.codingparty.entity;

import com.codingparty.entity.util.Color;
import com.codingparty.model.util.ModelRaw;

import math.Vector3f;

public abstract class Entity {

	protected static final float MIN_VELOCITY = 0.0001f;
	
	protected Vector3f acceleration;
	protected Vector3f velocity;
	protected Vector3f friction;
	
	protected Vector3f rotationAcceleration;
	protected Vector3f rotationVelocity;
	protected Vector3f position;
	protected Vector3f prevPosition;
	protected Vector3f rotation;
	protected Vector3f scale;
	private ModelRaw model;
	private Color color;
	
	public Entity(ModelRaw rawModel) {
		this(new Vector3f(), rawModel);
	}
	
	public Entity(Vector3f pos, ModelRaw rawModel) {
		this(pos, new Vector3f(), rawModel, new Vector3f(1, 1, 1));
	}
	
	public Entity(Vector3f pos, Vector3f rot, ModelRaw rawModel, Vector3f entityScale) {
		this(pos, new Vector3f(), rawModel, entityScale, new Color(1, 1, 1, 1));
	}
	
	public Entity(Vector3f pos, Vector3f rot, ModelRaw rawModel, Vector3f entityScale, Color entityColor) {
		position = pos;
		prevPosition = new Vector3f();
		rotation = rot;
		model = rawModel;
		
		acceleration = new Vector3f();
		velocity = new Vector3f();
		friction = new Vector3f();
		rotationAcceleration = new Vector3f();
		rotationVelocity = new Vector3f();
		scale = entityScale;
		color = entityColor;
	}
	
	public abstract void update(double deltaTime);
	
	public ModelRaw getModel() {
		return model;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public float getZ() {
		return position.z;
	}
	
	public float getRotAboutX() {
		return rotation.x;
	}
	
	public float getRotAboutY() {
		return rotation.y;
	}
	
	public float getRotAboutZ() {
		return rotation.z;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
	}
	
	public void setRotation(float x, float y, float z) {
		rotation.set(x, y, z);
	}
	
	public void setScale(float x, float y, float z) {
		scale.set(x, y, z);
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(float r, float g, float b, float a) {
		color.set(r, g, b, a);
	}
	
	public void setColor(Color c) {
		color.set(c);
	}
}
