package com.codingparty.model.util;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL30;

import com.codingparty.core.FileResourceTracker;
import com.codingparty.core.IRelease;
import com.codingparty.logger.LoggerUtil;
import com.codingparty.model.VBOWrapper;

public class ModelResourceManager implements IRelease {

	private static ModelResourceManager instance;
	private List<ModelRaw> activeModels;
	
	//TODO: Make a resource manager for the world. That will keep track of all the entities and models for that world and once the world is exited all the memory will be released from that world.
	private ModelResourceManager() {
		activeModels = new ArrayList<>();
	}
	
	public static void init() {
		if (instance == null || instance.activeModels == null) {
			instance = new ModelResourceManager();
			FileResourceTracker.addClass(instance);
		}
		else {
			LoggerUtil.logWarn(instance.getClass(), instance.getClass().getSimpleName() + ".class has already been initialized.");
		}
	}
	
	public static void addModel(ModelRaw model) {
		if (model != null) {
			instance.activeModels.add(model);
//			System.out.println("ADDED MODEL");
		}
		else {
			System.out.println("NULL MODEL!! " + model);
		}
	}
	
	public static void rebuildVAOs() {
		instance.release();
		
		System.out.println("REBUILDING VAOs and VBOs");
		
		for (ModelRaw model : instance.activeModels) {
			model.rebuildVAO();
		}
	}
	
	public static ModelResourceManager getInstance() {
		return instance;
	}
	
	@Override
	public void release() {
		LoggerUtil.logInfo(getClass(), "Releasing VAOs and VBOs.");

		for (int i = 0; i < instance.activeModels.size(); i++) {
			ModelRaw m = instance.activeModels.get(i);
			GL30.glDeleteVertexArrays(m.getVaoID());
			
			for (VBOWrapper vboID : m.getVBOs()) {
				GL30.glDeleteVertexArrays(vboID.getID());
			}
		}
	}
}
