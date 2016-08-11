package com.codingparty.model;

import com.codingparty.texture.Texture;

public class ModelSpriteBlueprint extends ModelBlueprint {

	protected String spriteSheetName;
	protected float spriteSheetStartX;
	protected float spriteSheetStartY;
	protected float spriteSheetEndX;
	protected float spriteSheetEndY;
	
	public ModelSpriteBlueprint(Texture sheet, float startX, float startY, float endX, float endY) {
		spriteSheetName = sheet.getFileName();
		spriteSheetStartX = startX;
		spriteSheetStartY = startY;
		spriteSheetEndX = endX;
		spriteSheetEndY = endY;
		initModelBluePrint();
	}
	
	protected void initModelBluePrint() {
		vertexPositions = new float[] {
				-0.5f, 0.0f, 0.5f,
				-0.5f, 0.0f, -0.5f,
				0.5f, 0.0f,  0.5f,
				0.5f, 0.0f, -0.5f};
		
		vertexNormals = new float[] {
				0.0f, 1.0f, 0.0f, 
				0.0f, 1.0f, 0.0f, 
				0.0f, 1.0f, 0.0f, 
				0.0f, 1.0f, 0.0f};
		
		textureCoordinates = new float[] {
				spriteSheetStartX, spriteSheetEndY,
				spriteSheetStartX, spriteSheetStartY,
				spriteSheetEndX, spriteSheetEndY,
				spriteSheetEndX, spriteSheetStartY};
		
		indices = new int[] {0, 1, 2, 2, 1, 3};
	}
	
	public float getStartX() {
		return spriteSheetStartX;
	}
	
	public float getStartY() {
		return spriteSheetStartY;
	}
	
	public float getEndX() {
		return spriteSheetEndX;
	}
	
	public float getEndY() {
		return spriteSheetEndY;
	}
	
	public String getSpriteSheetName() {
		return spriteSheetName;
	}
}
