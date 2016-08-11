package com.codingparty.math;

import java.util.Iterator;
import java.util.List;

public class ArrayUtil {
	public static int[] convertListToIntegerArray(List<Integer> integers) {
	    int[] intArray = new int[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    
	    for (int i = 0; i < intArray.length; i++) {
	    	intArray[i] = iterator.next().intValue();
	    }
	    return intArray;
	}
	
	public static float[] convertListToFloatArray(List<Float> floats) {
	    float[] floatArray = new float[floats.size()];
	    Iterator<Float> iterator = floats.iterator();
	    
	    for (int i = 0; i < floatArray.length; i++) {
	    	floatArray[i] = iterator.next().floatValue();
	    }
	    return floatArray;
	}
}
