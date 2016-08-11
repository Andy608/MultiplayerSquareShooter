package com.codingparty.world;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.codingparty.camera.Camera;
import com.codingparty.camera.CameraMatrixManager;
import com.codingparty.entity.Entity;
import com.codingparty.entity.util.Color;
import com.codingparty.file.setting.ProgramSettings;
import com.codingparty.math.MatrixMathHelper;
import com.codingparty.shader.LevelShader;
import com.codingparty.texture.Spritesheets;

import math.Matrix4f;
import math.Vector3f;

public class WorldRenderer {

	//TODO:Create shader handler class that can flip between multiple shaders for different render passes.
	private LevelShader levelShader;
	private Matrix4f transformationMatrix;
	
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	
	public WorldRenderer() {
		levelShader = new LevelShader();
		transformationMatrix = new Matrix4f();
		translation = new Vector3f();
		rotation = new Vector3f();
		scale = new Vector3f();
	}
	
	public void render(World currentWorld) {
		Camera camera = currentWorld.getPlayer().getCamera();
		Camera debugCamera = currentWorld.getDebugCamera();
		
		levelShader.bind();
		levelShader.loadProjectionMatrix(CameraMatrixManager.getProjectionMatrix());
		levelShader.loadCameraPosition(camera);
		levelShader.loadDebugCameraPosition(debugCamera);
		
		if (ProgramSettings.isDebugEnabled()) {
			levelShader.loadViewAndNormalMatrix(debugCamera.getViewMatrix());
//			drawCameraModel(camera);
		}
		else {
			levelShader.loadViewAndNormalMatrix(camera.getViewMatrix());
//			drawCameraModel(debugCamera);
		}
		
		
//		AbstractLevel currentLevel = currentWorld.getGameManager().getLevel();
//		if (currentLevel == null) return;
		
		for (Entity entity : currentWorld.getGameManager().getLevel().getLevelComponents()) {
			Color c = entity.getColor();
			
			GL30.glBindVertexArray(entity.getModel().getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(3);
			levelShader.loadEntityColor(c.r, c.g, c.b);
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			
			//TODO: Move this inside for loop when there are multiple tilesheets images.
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, Spritesheets.spritesheet1.getID());
			
			transformationMatrix.setIdentity();
			translation.set(entity.getX(), entity.getY(), entity.getZ());
			rotation.set(entity.getRotAboutX(), entity.getRotAboutY(), entity.getRotAboutZ());
			scale.set(entity.getScale());
			MatrixMathHelper.translateMatrix(transformationMatrix, translation);
			MatrixMathHelper.rotateMatrix(transformationMatrix, rotation);
			MatrixMathHelper.scaleMatrix(transformationMatrix, scale);
			levelShader.loadTransformationMatrix(transformationMatrix);
			GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			
			GL20.glDisableVertexAttribArray(3);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(0);
		}
		levelShader.unbind();
	}
	
//	private void drawCameraModel(Camera camera) {
//		ModelRaw cameraModel = Models.playerModel;
//		GL30.glBindVertexArray(cameraModel.getVaoID());
//		GL20.glEnableVertexAttribArray(0);
//		GL20.glEnableVertexAttribArray(1);
//		
//		transformationMatrix.setIdentity();
//		translation.set(camera.getX(), camera.getY(), camera.getZ());
//		rotation.set(0, -camera.getYaw(), 0);
//		MatrixMathHelper.translateMatrix(transformationMatrix, translation);
//		MatrixMathHelper.rotateMatrix(transformationMatrix, rotation);
//		levelShader.loadTransformationMatrix(transformationMatrix);
//		GL11.glDrawElements(GL11.GL_TRIANGLES, cameraModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//		
//		GL20.glDisableVertexAttribArray(1);
//		GL20.glDisableVertexAttribArray(0);
//	}
}
