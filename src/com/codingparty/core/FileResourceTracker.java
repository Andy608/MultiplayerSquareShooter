package com.codingparty.core;

import java.util.ArrayList;
import java.util.List;

import com.codingparty.logger.LoggerUtil;

public class FileResourceTracker {

	private static FileResourceTracker instance;
	
	private List<IRelease> releasableResourceFiles;
	
	private FileResourceTracker() {
		releasableResourceFiles = new ArrayList<>();
	}
	
	public static void init() {
		if (instance == null) {
			instance = new FileResourceTracker();
		}
		else {
			LoggerUtil.logWarn(instance.getClass(), instance.getClass().getSimpleName() + ".class has already been initialized.");
		}
	}
	
	public static void addClass(IRelease c) {
		for (IRelease file : instance.releasableResourceFiles) {
			if (file == c) {
				LoggerUtil.logWarn(instance.getClass(), c.getClass().getSimpleName() + ".class is already in the list.");
				return;
			}
		}
		instance.releasableResourceFiles.add(c);
	}
	
	public static void releaseResource(IRelease c) {
		for (IRelease file : instance.releasableResourceFiles) {
			if (file == c) {
				file.release();
				break;
			}
		}
		instance.releasableResourceFiles.remove(c);//TODO: TEST THIS
	}
	
	public static void releaseProgramResources() {
		for (IRelease file : instance.releasableResourceFiles) {
			file.release();
		}
	}
}
