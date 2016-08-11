package com.codingparty.file.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import com.codingparty.camera.CameraMatrixManager;
import com.codingparty.component.Window;
import com.codingparty.file.FileResourceLocation;
import com.codingparty.file.FileResourceLocation.EnumFileExtension;
import com.codingparty.logger.LoggerUtil;
import com.codingparty.model.util.ModelResourceManager;

import math.Vector2f;

public class ProgramSettings extends SettingsBase {

	private static final FileResourceLocation PROGRAM_SETTINGS = new FileResourceLocation(SETTINGS_DIR, "program_settings", EnumFileExtension.PROPERTIES);
	private static ProgramSettings instance;

	private Vector2fSetting windowSize;
	private Vector2fSetting windowPosition;
	
	private BooleanSetting saveWindowSize;
	private BooleanSetting saveWindowPosition;
	
	private BooleanSetting fullscreen;
	
	private BooleanSetting vSync;
	private ClampedIntegerSetting fieldOfView;
	private BooleanSetting isPerspectiveView;
	
	private BooleanSetting isCameraStickyRotation;
	
	//Use the drag functionality to rotate the camera or the arrow keys.
	//True = mouse drag, false = arrow keys
	private ClampedIntegerSetting rotateCameraOption;
	private BooleanSetting isDebug;
	
	private ProgramSettings() {
		initDefaultSettings();
	}
	
	public static void init() throws IllegalStateException {
		if (instance == null) {
			instance = new ProgramSettings();
		}
		else {
			LoggerUtil.logWarn(instance.getClass(), instance.getClass().getSimpleName() + ".class has already been initialized.");
		}
	}
	
	@Override
	protected void initDefaultSettings() {
		windowSize = new Vector2fSetting("window_size", new Vector2f(Window.getPrimaryMonitorWidth() / 2, Window.getPrimaryMonitorHeight() / 2));
		saveWindowSize = new BooleanSetting("save_window_size", true);
		windowPosition = new Vector2fSetting("window_position", new Vector2f((Window.getPrimaryMonitorWidth() - windowSize.getDefaultVector2f().x) / 2, (Window.getPrimaryMonitorHeight() - windowSize.getDefaultVector2f().y) / 2));
		saveWindowPosition = new BooleanSetting("save_window_position", true);
		fullscreen = new BooleanSetting("fullscreen", false);
		vSync = new BooleanSetting("vSync", true);
		fieldOfView = new ClampedIntegerSetting("fov", 70, 30, 110);
		isPerspectiveView = new BooleanSetting("perspective_view", true);
		isCameraStickyRotation = new BooleanSetting("sticky_camera_rotation", true);
		rotateCameraOption = new ClampedIntegerSetting("rotate_camera_option", 0, 0, EnumRotateType.values().length);
		isDebug = new BooleanSetting("debug", false);
	}

	@Override
	public void loadSettingsFromFile() {
		File location = new File(PROGRAM_SETTINGS.getFullPath());
		setDefaultSettings();
		
		if (location.exists()) {
			try {
				String[] settings = readOptionsFile(location);
				
				for (String s : settings) {
					String settingAttrib = s.substring(0, s.indexOf(DEFAULT_DELIMITER));
					
					if (settingAttrib.equals(windowSize.getSettingName())) {
						try {
							int width = getSingleIntegerFromSetting(s, (int)windowSize.getDefaultVector2f().x, DEFAULT_DELIMITER, SEPARATOR);
							int height = getSingleIntegerFromSetting(s, (int)windowSize.getDefaultVector2f().y, SEPARATOR);
							
							if (width >= Window.getPrimaryMonitorWidth() || height >= Window.getPrimaryMonitorHeight()) {
								windowSize.resetVector2f();
							}
							else {
								windowSize.setCustomVector2f(getVector2fValue(new Vector2f(width, height), windowSize.getDefaultVector2f()));
							}
						} catch (NumberFormatException e) {
							LoggerUtil.logWarn(getClass(), e, PROGRAM_SETTINGS.getFileNameWithExtension() + " is corrupt! Did you edit this file? Unable to get correct windowSize. Using default value instead.", true);
							windowSize.resetVector2f();
						}
						continue;
					}
					else if (settingAttrib.equals(saveWindowSize.getSettingName())) {
						saveWindowSize.setCustomBoolean(getSingleBooleanFromSetting(s, saveWindowSize.getDefaultBoolean(), DEFAULT_DELIMITER));
						continue;
					}
					else if (settingAttrib.equals(windowPosition.getSettingName())) {
						try {
							int xPos = getSingleIntegerFromSetting(s, (int)windowPosition.getDefaultVector2f().x, DEFAULT_DELIMITER, SEPARATOR);
							int yPos = getSingleIntegerFromSetting(s, (int)windowPosition.getDefaultVector2f().y, SEPARATOR);
							
							if (xPos <= 0 || xPos >= Window.getPrimaryMonitorWidth() || yPos <= 0 || yPos >= Window.getPrimaryMonitorHeight()) {
								windowPosition.resetVector2f();
							}
							else {
								windowPosition.setCustomVector2f(getVector2fValue(new Vector2f(xPos, yPos), windowPosition.getDefaultVector2f()));
							}
						} catch (NumberFormatException e) {
							LoggerUtil.logWarn(getClass(), e, PROGRAM_SETTINGS.getFileNameWithExtension() + " is corrupt! Did you edit this file? Unable to get correct windowPosition. Using default value instead.", true);
							windowPosition.resetVector2f();
						}
						continue;
					}
					else if (settingAttrib.equals(saveWindowPosition.getSettingName())) {
						saveWindowPosition.setCustomBoolean(getSingleBooleanFromSetting(s, saveWindowPosition.getDefaultBoolean(), DEFAULT_DELIMITER));
						continue;
					}
					else if (settingAttrib.equals(fullscreen.getSettingName())) {
						fullscreen.setCustomBoolean(getSingleBooleanFromSetting(s, fullscreen.getDefaultBoolean(), DEFAULT_DELIMITER));
						continue;
					}
					else if (settingAttrib.equals(vSync.getSettingName())) {
						vSync.setCustomBoolean(getSingleBooleanFromSetting(s, vSync.getCustomBoolean(), DEFAULT_DELIMITER));
						continue;
					}
					else if (settingAttrib.equals(fieldOfView.getSettingName())) {
						fieldOfView.setCustomInteger(getSingleIntegerFromSetting(s, fieldOfView.getDefaultInteger(), DEFAULT_DELIMITER));
					}
					else if (settingAttrib.equals(isPerspectiveView.getSettingName())) {
						isPerspectiveView.setCustomBoolean(getSingleBooleanFromSetting(s, isPerspectiveView.getCustomBoolean(), DEFAULT_DELIMITER));
					}
					else if (settingAttrib.equals(isCameraStickyRotation.getSettingName())) {
						isCameraStickyRotation.setCustomBoolean(getSingleBooleanFromSetting(s, isCameraStickyRotation.getCustomBoolean(), DEFAULT_DELIMITER));
					}
					else if (settingAttrib.equals(rotateCameraOption.getSettingName())) {
						rotateCameraOption.setCustomInteger(getSingleIntegerFromSetting(s, rotateCameraOption.getDefaultInteger(), DEFAULT_DELIMITER));
					}
					else if (settingAttrib.equals(isDebug.getSettingName())) {
						isDebug.setCustomBoolean(getSingleBooleanFromSetting(s, isDebug.getCustomBoolean(), DEFAULT_DELIMITER));
					}
					else {
						throw new IllegalStateException(s + " is not an expected option.");
					}
				}
				
			} catch (Exception e) {
				LoggerUtil.logWarn(getClass(), e, PROGRAM_SETTINGS.getFileNameWithExtension() + " is corrupt! Using default values.", true);
			}
		}
		else {
			new File(PROGRAM_SETTINGS.getParentDirectory().getFullDirectory()).mkdirs();
		}
	}

	@Override
	protected void setDefaultSettings() {
		windowSize.resetVector2f();
		saveWindowSize.resetBoolean();
		windowPosition.resetVector2f();
		saveWindowPosition.resetBoolean();
		fullscreen.resetBoolean();
		vSync.resetBoolean();
		fieldOfView.resetInteger();
		isPerspectiveView.resetBoolean();
		isCameraStickyRotation.resetBoolean();
		rotateCameraOption.resetInteger();
		isDebug.resetBoolean();
	}

	@Override
	public void storeSettingsInFile() {
		try (PrintStream writer = new PrintStream(PROGRAM_SETTINGS.getFullPath(), "UTF-8")) {
			writer.println(windowSize.getSettingName() + DEFAULT_DELIMITER + (int)windowSize.getCustomVector2f().x + "," + (int)windowSize.getCustomVector2f().y);
			writer.println(saveWindowSize.getSettingName() + DEFAULT_DELIMITER + saveWindowSize.getCustomBoolean());
			writer.println(windowPosition.getSettingName() + DEFAULT_DELIMITER + (int)windowPosition.getCustomVector2f().x + "," + (int)windowPosition.getCustomVector2f().y);
			writer.println(saveWindowPosition.getSettingName() + DEFAULT_DELIMITER + saveWindowPosition.getCustomBoolean());
			writer.println(fullscreen.getSettingName() + DEFAULT_DELIMITER + fullscreen.getCustomBoolean());
			writer.println(vSync.getSettingName() + DEFAULT_DELIMITER + vSync.getCustomBoolean());
			writer.println(fieldOfView.getSettingName() + DEFAULT_DELIMITER + fieldOfView.getCustomInteger());
			writer.println(isPerspectiveView.getSettingName() + DEFAULT_DELIMITER + isPerspectiveView.getCustomBoolean());
			writer.println(isCameraStickyRotation.getSettingName() + DEFAULT_DELIMITER + isCameraStickyRotation.getCustomBoolean());
			writer.println(rotateCameraOption.getSettingName() + DEFAULT_DELIMITER + rotateCameraOption.getCustomInteger());
			writer.print(isDebug.getSettingName() + DEFAULT_DELIMITER + isDebug.getCustomBoolean());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			LoggerUtil.logError(getClass(), e);
		}
	}
	
	/**
	 * *ALWAYS USE THIS METHOD IF YOU ARE LOOKING FOR THE CURRENT WINDOW WIDTH*
	 * Checks if the custom window size should be used. If it should be used, then it returns the custom size, else it returns the default size.
	 * @return : The current window width.
	 */
	public static int getCurrentWindowWidth() {
		if (ProgramSettings.isFullscreenEnabled()) {
			return Window.getPrimaryMonitorWidth();
		}
		else if (ProgramSettings.isWindowSizeSaved()) {
			return getSavedWindowWidth();
		}
		else {
			return getDefaultWindowWidth();
		}
	}
	
	/**
	 * *ALWAYS USE THIS METHOD IF YOU ARE LOOKING FOR THE CURRENT WINDOW HEIGHT*
	 * Checks if the custom window size should be used. If it should be used, then it returns the custom size, else it returns the default size.
	 * @return : The current window height.
	 */
	public static int getCurrentWindowHeight() {
		if (ProgramSettings.isFullscreenEnabled()) {
			return Window.getPrimaryMonitorHeight();
		}
		else if (ProgramSettings.isWindowSizeSaved()) {
			return getSavedWindowHeight();
		}
		else {
			return getDefaultWindowHeight();
		}
	}
	
	/**
	 * Updates the custom window sizes. *Does not update when going into fullscreen mode.*
	 * @param x : The window width.
	 * @param y : The window height.
	 */
	public static void updateWindowSize(int x, int y) {
		instance.windowSize.setCustomVector2f(x, y);
	}
	
	/**
	 * @return : The default window width setting.
	 */
	public static int getDefaultWindowWidth() {
		return (int)instance.windowSize.getDefaultVector2f().x;
	}
	
	/**
	 * @return : The default window height setting.
	 */
	public static int getDefaultWindowHeight() {
		return (int)instance.windowSize.getDefaultVector2f().y;
	}
	
	/**
	 * @return : The custom window width setting.
	 */
	public static int getSavedWindowWidth() {
		return (int)instance.windowSize.getCustomVector2f().x;
	}
	
	/**
	 * @return : The custom window height setting.
	 */
	public static int getSavedWindowHeight() {
		return (int)instance.windowSize.getCustomVector2f().y;
	}
	
	/**
	 * Updates the boolean that decides if the window size should be used the next time the game is opened.
	 * @param b : The boolean indicator.
	 */
	public static void updateSaveWindowSize(boolean b) {
		instance.saveWindowSize.setCustomBoolean(b);
	}
	
	/**
	 * @return : Whether or not the current window size is saved/will be used on reopening.
	 */
	public static boolean isWindowSizeSaved() {
		return instance.saveWindowSize.getCustomBoolean();
	}
	
	/**
	 * *ALWAYS USE THIS METHOD IF YOU ARE LOOKING FOR THE CURRENT WINDOW X POSITION*
	 * Checks if the custom window position should be used. If it should be used, then it returns the custom x position, else it returns the default x position.
	 * @return : The current window x position.
	 */
	public static int getCurrentWindowPositionX() {
		if (ProgramSettings.isWindowPositionSaved()) {
			return getSavedWindowPositionX();
		}
		else {
			return (Window.getPrimaryMonitorWidth() - ProgramSettings.getCurrentWindowWidth()) / 2;
		}
	}
	
	/**
	 * *ALWAYS USE THIS METHOD IF YOU ARE LOOKING FOR THE CURRENT WINDOW Y POSITION*
	 * Checks if the custom window position should be used. If it should be used, then it returns the custom y position, else it returns the default y position.
	 * @return : The current window y position.
	 */
	public static int getCurrentWindowPositionY() {
		if (ProgramSettings.isWindowPositionSaved()) {
			return getSavedWindowPositionY();
		}
		else {
			return (Window.getPrimaryMonitorHeight() - ProgramSettings.getCurrentWindowHeight()) / 2;
		}
	}
	
	/**
	 * Updates the custom window position vector.
	 * @param x : The x position of the window.
	 * @param y : The y position of the window.
	 */
	public static void updateWindowPosition(int x, int y) {
		instance.windowPosition.setCustomVector2f(x, y);
	}
	
	/**
	 * Updates the boolean that decides if the window position should be used next time the game opens.
	 * @param b : The boolean indicator.
	 */
	public static void updateSaveWindowPosition(boolean b) {
		instance.saveWindowPosition.setCustomBoolean(b);
	}
	
	/**
	 * @return : Whether or not the window position is saved.
	 */
	public static boolean isWindowPositionSaved() {
		return instance.saveWindowPosition.getCustomBoolean();
	}
	
	/**
	 * @return : The custom window x position.
	 */
	public static int getSavedWindowPositionX() {
		return (int)instance.windowPosition.getCustomVector2f().x;
	}
	
	/**
	 * @return : The custom window y position.
	 */
	public static int getSavedWindowPositionY() {
		return (int)instance.windowPosition.getCustomVector2f().y;
	}
	
	/**
	 * @return : The default window x position.
	 */
	public static int getDefaultWindowPositionX() {
		return (int)instance.windowPosition.getDefaultVector2f().x;
	}
	
	/**
	 * @return : The default window y position.
	 */
	public static int getDefaultWindowPositionY() {
		return (int)instance.windowPosition.getDefaultVector2f().y;
	}
	
	/**
	 * Toggles the fullscreen indicator between fullscreen and windowed mode.
	 * @param b : The boolean indicator.
	 */
	public static void updateFullscreen(boolean b) {
		instance.storeSettingsInFile();
		instance.fullscreen.setCustomBoolean(b);
		Window.buildScreen();
		ModelResourceManager.rebuildVAOs();
//		ControlOptions.setPaused(ControlOptions.isPaused());
		CameraMatrixManager.buildProjectionMatrix();
//		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);//TODO:MOVE THIS
	}
	
	/**
	 * @return : Boolean indicator saying if fullscreen is enabled.
	 */
	public static boolean isFullscreenEnabled() {
		return instance.fullscreen.getCustomBoolean();
	}
	
	public static boolean isVSyncEnabled() {
		return instance.vSync.getCustomBoolean();
	}
	
	public static void updateVSync(boolean b) {
		instance.vSync.setCustomBoolean(b);
	}
	
	public static int getCustomFOV() {
		return instance.fieldOfView.getCustomInteger();
	}
	
	public static void updateFOV(int fov) {
		instance.fieldOfView.setCustomInteger(fov);
	}
	
	public static int getDefaultFOV() {
		return instance.fieldOfView.getDefaultInteger();
	}
	
	public static boolean isPerspectiveView() {
		return instance.isPerspectiveView.getCustomBoolean();
	}
	
	public static void updateProjection(boolean isPerspective) {
		instance.isPerspectiveView.setCustomBoolean(isPerspective);
	}
	
	public static boolean isCameraUsingStickyRotation() {
		return instance.isCameraStickyRotation.getCustomBoolean();
	}
	
	public static void setCameraStickyRotation(boolean b) {
		instance.isCameraStickyRotation.setCustomBoolean(b);
	}
	
	public static EnumRotateType getRotateCameraOption() {
		return EnumRotateType.values()[instance.rotateCameraOption.customSetting];
	}
	
	public static void setRotateCameraWithMouseDrag(boolean useDrag) {
		instance.isPerspectiveView.setCustomBoolean(useDrag);
	}
	
	public static boolean isDebugEnabled() {
		return instance.isDebug.getCustomBoolean();
	}
	
	public static void setDebug(boolean b) {
		instance.isDebug.setCustomBoolean(b);
	}
	
	public static ProgramSettings getInstance() {
		return instance;
	}
}
