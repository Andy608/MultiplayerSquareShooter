package com.codingparty.file;

import com.codingparty.core.Info;
import com.codingparty.file.SystemUtil.EnumOS;

public final class ResourceHelper {

	protected static final char DEFAULT_DELIMITER = '/';
	
	public static final ResourceDirectory GAME_APPDATA_DIRECTORY;
//	public static final ResourceDirectory INSTALLATION_DIRECTORY;
	private static final EnumOS SYSTEM_OS = SystemUtil.getOSType();
	
	static {
		switch (SYSTEM_OS) {
		case WINDOWS: {
			GAME_APPDATA_DIRECTORY = new ResourceDirectory(System.getenv("APPDATA"), Info.NAME, false); break;
//			INSTALLATION_DIRECTORY = new ResourceDirectory("C:/Program Files (x86)/" + Info.NAME, "natives", false); break;
		}
		case OSX: {
			GAME_APPDATA_DIRECTORY = new ResourceDirectory(System.getProperty("user.home") + FileUtil.getFileSeparator(false) + "Library" + FileUtil.getFileSeparator(false) + "Application Support", Info.NAME, false); break;
//			INSTALLATION_DIRECTORY = new ResourceDirectory("C:/Program Files (x86)/" + Info.NAME, "natives", false); break; //TODO: CHANGE THIS TO THE CORRECT DIRECTORY.
		}
		default: {
			GAME_APPDATA_DIRECTORY = new ResourceDirectory(System.getProperty("user.home"), Info.NAME, false); break;
//			INSTALLATION_DIRECTORY = new ResourceDirectory("C:/Program Files (x86)/" + Info.NAME, "natives", false); break; //TODO: CHANGE THIS TO THE CORRECT DIRECTORY.
		}
		}
	}
}
