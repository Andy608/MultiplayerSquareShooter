package com.codingparty.texture;

import org.lwjgl.opengl.GL11;

import com.codingparty.core.IRelease;
import com.codingparty.file.FileResourceLocation;

public class Texture implements IRelease {

	private int textureID;
	private FileResourceLocation textureLocation;
	
	public Texture(int id, FileResourceLocation location) {
		textureID = id;
		textureLocation = location;
	}
	
	public String getFileName() {
		return textureLocation.getFileName();
	}
	
	public FileResourceLocation getFilePath() {
		return textureLocation;
	}
	
	public int getID() {
		return textureID;
	}
	
	@Override
	public void release() {
		GL11.glDeleteTextures(textureID);
	}
}
