package com.codingparty.model.util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.codingparty.model.ModelSpriteBlueprint;
import com.codingparty.model.VBOWrapper;

/**
 * ModelBuilder
 * For now, this class creates basic models needed for the game.
 * This class does not bind specific textures to models. It just creates the model.
 * Provides a default white color to all models that is configurable through uniform variables.
 * 
 * @author Andrew Rimpici
 */
public class ModelBuilder {

//	/**
//	 * Builds a 3D model. It is assumed that all positions are in 3D spaces!
//	 * @param positions : The model's default positions in the world.
//	 * @param indices : The indices for the model.
//	 * @return : A model made up from the information provided.
//	 */
//	public static ModelRaw buildModel(float[] positions, int[] indices) {
//		int vaoID = createVAO();
//		VBOWrapper[] vboIDs = new VBOWrapper[2];
//		bindVAO(vaoID);
//		bindIndicesBuffer(indices);
//		int positionVBO = bindAttribToVAO(0, positions, 3);
//		float[] colors = colorizeModel(indices.length);
//		int colorVBO = bindAttribToVAO(1, colorizeModel(indices.length), 4);
//		vboIDs[0] = new VBOWrapper(positionVBO, positions, 3);
//		vboIDs[1] = new VBOWrapper(colorVBO, colors, 4);
//		unbindVAO();
//		ModelRaw model = new ModelRaw(vaoID, indices, vboIDs);
//		ModelResourceManager.addModel(model);
//		return model;
//	}
//	
//	public static ModelRaw buildModel(float[] positions, int[] indices, float[] normals) {
//		int vaoID = createVAO();
//		VBOWrapper[] vboIDs = new VBOWrapper[3];
//		bindVAO(vaoID);
//		bindIndicesBuffer(indices);
//		int positionVBO = bindAttribToVAO(0, positions, 3);
//		float[] colors = colorizeModel(indices.length);
//		int colorVBO = bindAttribToVAO(1, colorizeModel(indices.length), 4);
//		int normalVBO = bindAttribToVAO(0, normals, 3);
//		vboIDs[0] = new VBOWrapper(positionVBO, positions, 3);
//		vboIDs[1] = new VBOWrapper(colorVBO, colors, 4);
//		vboIDs[2] = new VBOWrapper(normalVBO, normals, 3);
//		unbindVAO();
//		ModelRaw model = new ModelRaw(vaoID, indices, vboIDs);
//		ModelResourceManager.addModel(model);
//		return model;
//	}
	
	public static ModelRaw buildModel(float[] positions, int[] indices, float[] normals, float[] textureCoords) {
		int vaoID = createVAO();
		VBOWrapper[] vboIDs = new VBOWrapper[4];
		bindVAO(vaoID);
		bindIndicesBuffer(indices);
		int positionVBO = bindAttribToVAO(0, positions, 3);
		float[] colors = colorizeModel(indices.length);
		int colorVBO = bindAttribToVAO(1, colorizeModel(indices.length), 4);
		int normalVBO = bindAttribToVAO(2, normals, 3);
		int textureCoordVBO = bindAttribToVAO(3, textureCoords, 2);
		vboIDs[0] = new VBOWrapper(positionVBO, positions, 3);
		vboIDs[1] = new VBOWrapper(colorVBO, colors, 4);
		vboIDs[2] = new VBOWrapper(normalVBO, normals, 3);
		vboIDs[3] = new VBOWrapper(textureCoordVBO, textureCoords, 2);
		unbindVAO();
		ModelRaw model = new ModelRaw(vaoID, indices, vboIDs);
		ModelResourceManager.addModel(model);
		return model;
	}
	
	public static ModelRaw buildModel(ModelSpriteBlueprint blueprint) {
		float[] positions = blueprint.getPositions();
		float[] normals = blueprint.getVertexNormals();
		float[] texCoords = blueprint.getTextureCoords();
		
//		texCoords[0] = texCoords[0];
//		texCoords[1] = texCoords[1];
//		texCoords[2] = texCoords[2];
//		texCoords[3] = texCoords[3];
		
		int[] indices = blueprint.getIndices();
		
		int vaoID = createVAO();
		VBOWrapper positionsVBO;
		VBOWrapper colorsVBO;
		VBOWrapper normalsVBO;
		VBOWrapper textureCoordsVBO;
		bindVAO(vaoID);
		bindIndicesBuffer(indices);
		int positionVBO = bindAttribToVAO(0, positions, 3);
		float[] colors = colorizeModel(indices.length);
		int colorVBO = bindAttribToVAO(1, colorizeModel(indices.length), 4);
		int normalVBO = bindAttribToVAO(2, normals, 3);
		int textureCoordVBO = bindAttribToVAO(3, texCoords, 2);
		positionsVBO = new VBOWrapper(positionVBO, positions, 3);
		colorsVBO = new VBOWrapper(colorVBO, colors, 4);
		normalsVBO = new VBOWrapper(normalVBO, normals, 3);
		textureCoordsVBO = new VBOWrapper(textureCoordVBO, texCoords, 2);
		unbindVAO();
		ModelRaw model = new ModelRaw(vaoID, indices, new VBOWrapper[] {positionsVBO, colorsVBO, normalsVBO, textureCoordsVBO});
		ModelResourceManager.addModel(model);
		return model;
	}
	
	private static int createVAO() {
		return GL30.glGenVertexArrays();
	}
	
	private static void bindVAO(int vaoID) {
		GL30.glBindVertexArray(vaoID);
	}
	
	private static int bindAttribToVAO(int arrayIndex, float[] bufferData, int dataLengthPerVertex) {
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferHelper.toReadableFloatBuffer(bufferData), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(arrayIndex, dataLengthPerVertex, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vbo;
	}
	
	private static void bindIndicesBuffer(int[] indices) {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.glGenBuffers());
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferHelper.toReadableIntBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private static float[] colorizeModel(int vertCount) {
		float[] colors = new float[vertCount * 4];
		for (int i = 0; i < colors.length; i++)
			colors[i] = 1.0f;
		return colors;
	}
}
