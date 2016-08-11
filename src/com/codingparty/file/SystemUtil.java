package com.codingparty.file;

public class SystemUtil {

	public static EnumOS getOSType() {
        String s = System.getProperty("os.name").toLowerCase();
        return s.contains("win") ? EnumOS.WINDOWS : (s.contains("mac") ? EnumOS.OSX : (s.contains("linux") ? EnumOS.LINUX : (s.contains("unix") ? EnumOS.LINUX : 
        	(s.contains("solaris") ? EnumOS.SOLARIS : (s.contains("sunos") ? EnumOS.SOLARIS : EnumOS.UNKNOWN)))));
    }
	
	public static enum EnumOS {
    	WINDOWS,
    	OSX,
    	LINUX,
        SOLARIS,
        UNKNOWN;
    }
}
