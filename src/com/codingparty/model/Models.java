package com.codingparty.model;

import com.codingparty.model.util.ModelBuilder;
import com.codingparty.model.util.ModelRaw;
import com.codingparty.model.util.ModelResourceManager;
import com.codingparty.texture.Spritesheets;

public class Models {
	
	private static ModelSpriteBlueprint playerBlueprint;
	public static ModelRaw playerModel;
	
	private static ModelSpriteBlueprint paperBlueprint;
	public static ModelRaw paperModel;
	
	public static void buildModels() {
		playerBlueprint = new ModelSpriteBlueprint(Spritesheets.spritesheet1, 0, 0, (1f / 16f), (1f / 16f));
		playerModel = ModelBuilder.buildModel(playerBlueprint);
		ModelResourceManager.addModel(playerModel);
		
		paperBlueprint = new ModelSpriteBlueprint(Spritesheets.spritesheet1, (1f / 256f) * 188f, (1f / 256f) * 168f, 1f, 1f);
		paperModel = ModelBuilder.buildModel(paperBlueprint);
		ModelResourceManager.addModel(paperModel);
	}

}
