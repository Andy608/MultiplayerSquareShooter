package com.codingparty.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.codingparty.core.FileResourceTracker;
import com.codingparty.core.IRelease;
import com.codingparty.file.FileResourceLocation;
import com.codingparty.file.FileResourceLocation.EnumFileExtension;
import com.codingparty.file.FileUtil;
import com.codingparty.file.ResourceDirectory;
import com.codingparty.logger.LoggerUtil;

public class TextureLoader implements IRelease {

	public static final ResourceDirectory IMAGES_DIRECTORY = new ResourceDirectory("res/graphics", "images", true);
	public static final int BYTES_PER_PIXEL = 4;
	
	private static TextureLoader instance;
	private static Texture defaultTexture;
	private List<Texture> textures;

	private TextureLoader() {
		textures = new ArrayList<>();
	}
	
	public static void init() {
		if (instance == null || instance.textures == null) {
			instance = new TextureLoader();
			FileResourceLocation defaultTextureLocation = new FileResourceLocation(IMAGES_DIRECTORY, "default", EnumFileExtension.PNG);
			defaultTexture = loadTexture(defaultTextureLocation);
			FileResourceTracker.addClass(instance);
		}
		else {
			LoggerUtil.logWarn(instance.getClass(), instance.getClass().getSimpleName() + ".class has already been initialized.");
		}
	}
	
	public static Texture loadTexture(FileResourceLocation textureLocation) {
//		File imageFile = new File(textureLocation.getFullPath());
		
//		System.out.println(imageFile.getAbsolutePath());
		
//		if (imageFile.exists()) {
		InputStream stream = TextureLoader.class.getClass().getResourceAsStream(FileUtil.getFileSeparator(true) + textureLocation.getFullPath());
		if (stream != null) {
			try {
				BufferedImage image = ImageIO.read(stream);
				
				int pixels[] = new int[image.getWidth() * image.getHeight()];
				image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
				ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length * BYTES_PER_PIXEL);
				
				for (int y = 0; y < image.getHeight(); y++) {
					for (int x = 0; x < image.getWidth(); x++) {
						int pixel = pixels[x + (image.getWidth() * y)];
						buffer.put((byte) ((pixel >> 16) & 0xFF));	//Red component
						buffer.put((byte) ((pixel >> 8) & 0xFF));	//Green component
						buffer.put((byte) (pixel & 0xFF));			//Blue component
						buffer.put((byte) ((pixel >> 24) & 0xFF));	//Alpha component
					}
				}
				buffer.flip();
				
				int textureID = GL11.glGenTextures();
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
				
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
				
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
				
				GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
//				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
				
				Texture t = new Texture(textureID, textureLocation);
				instance.textures.add(t);
				
				//TODO: Come back to this. And change this depending on options.
				return t;
			} catch (IOException e) {
				LoggerUtil.logError(TextureLoader.class, "Could not load image from file: " + textureLocation.getFullPath(), e);
			}
		}
		else {
			LoggerUtil.logWarn(TextureLoader.class, "Image does not exist at location: " + textureLocation.getFullPath());
		}
		return defaultTexture;
	}
	
	@Override
	public void release() {
		for (Texture t : textures) {
			t.release();
		}
		textures = null;
	}
}
