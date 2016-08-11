package com.codingparty.texture;

import com.codingparty.file.FileResourceLocation;
import com.codingparty.file.FileResourceLocation.EnumFileExtension;
import com.codingparty.file.ResourceDirectory;

public class Spritesheets {

	public static final int TILESET_PIXEL_WIDTH = 256;
	private static final ResourceDirectory SPRITE_DIRECTORY = new ResourceDirectory(TextureLoader.IMAGES_DIRECTORY.getFullDirectory(), "sprites", true);
	
	public static Texture spritesheet1;
	
	public static void init() {
		spritesheet1 = TextureLoader.loadTexture(new FileResourceLocation(SPRITE_DIRECTORY, "spritesheet1", EnumFileExtension.PNG));
	}
}
