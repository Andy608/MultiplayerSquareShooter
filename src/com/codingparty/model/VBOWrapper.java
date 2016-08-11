package com.codingparty.model;

import org.lwjgl.opengl.GL30;

import com.codingparty.core.IRelease;

public class VBOWrapper implements IRelease {

	private int vboID;
	private float[] vboData;
	private int vboLength;
	
	public VBOWrapper(int id, float[] data, int length) {
		vboID = id;
		vboData = data;
		vboLength = length;
	}
	
	public int getID() {
		return vboID;
	}
	
	public void setID(int id) {
		GL30.glDeleteVertexArrays(vboID);
		vboID = id;
	}
	
	public int getVBOLength() {
		return vboLength;
	}
	
	public float[] getData() {
		return vboData;
	}
	
	@Override
	public void release() {
		vboData = null;
	}
}
