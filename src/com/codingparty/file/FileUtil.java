package com.codingparty.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.codingparty.logger.LoggerUtil;

public class FileUtil {

	public static final String ENTER = System.lineSeparator();
	private static final String SEP = System.getProperty("file.separator");
	
	public static String[] getAllLinesFromExternalFileAsArray(File file) throws IOException {
		List<String> lineValues = null;
		lineValues = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
		return lineValues.toArray(new String[lineValues.size()]);
	}
	
	public static List<String> getAllLinesFromFileAsList(File file) throws IOException {
		List<String> lineValues = null;
		lineValues = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
		return lineValues;
	}
	
	public static String[] getAllLinesFromInternalFileAsArray(FileResourceLocation location) {
		String s = "/" + location.getFullPath();
		
		try (BufferedReader r = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream(s)))) {
			List<String> result = new ArrayList<>();
            for (;;) {
                String line = r.readLine();
                if (line == null)
                    break;
                result.add(line);
            }
            return result.toArray(new String[result.size()]);
		} catch (Exception e) {
			LoggerUtil.logWarn(FileUtil.class, e, "Could not read file location: " + location, true);
			return null;
		}
	}
	
	public static String getFileSeparator(boolean isJar) {
		return !isJar ? SEP : "/";
	}
}
