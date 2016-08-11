package com.codingparty.math;

import java.util.Random;

import math.Vector2f;
import math.Vector3f;

public class MathHelper {

	private static float[] SIN_TABLE = new float[65536];
	public static final Random RAND = new Random();
	
	public static final int clampInt(int value, int min, int max) {
		return (value > max) ? max : (value < min) ? min : value; 
	}
	
	public static final float clampFloat(float value, float min, float max) {
		return (value > max) ? max : (value < min) ? min : value;
	}
	
	public static final float sin(float degrees) {
        return SIN_TABLE[(int)(Math.toRadians(degrees) * 10430.378F) & 65535];
    }

    public static final float cos(float degrees) {
    	if (degrees == 90 || degrees == 270) return 0f;
    	else return SIN_TABLE[(int)(Math.toRadians(degrees) * 10430.378F + 16384.0F) & 65535];
    }
    
    public static final float stepLerp(float value, float target, float step) {
    	if (target < value) {
    		return clampFloat(value - step, target, value);
    	}
    	else {
    		return clampFloat(value + step, value, target);
    	}
    }
    
    public static final float lerp(float a, float b, float alpha) {
    	float lerp = a + alpha * (b - a);
    	return lerp;
    }
    
    public static final float boundedAngle(float angle, float maxAngle) {
    	while (angle < 0) {
    		angle += maxAngle;
    	}
    	
    	while (angle >= maxAngle) {
    		angle -= maxAngle;
    	}
    	
    	return angle;
    }
    
    public static final int min(int... values) {
    	int min = values[0];
    	for (int value : values) {
    		if (value < min) {
    			min = value;
    		}
    	}
    	return min;
    }
    
    public static final int max(int... values) {
    	int max = values[0];
    	for (int value : values) {
    		if (value > max) {
    			max = value;
    		}
    	}
    	return max;
    }
    
    public static float distanceSquared(Vector3f first, Vector3f second) {
		return ((first.x - second.x) * (first.x - second.x)) + ((first.y - second.y) * (first.y - second.y)) + ((first.z - second.z) * (first.z - second.z));
	}
	
    public static final Vector2f makeAnglesClose(float angle1, float angle2, Vector2f angles) {
    	
    	if (angle1 - angle2 > 180) {
    		angle2 += 360;
		}
		else if (angle1 - angle2 < -180) {
			angle1 += 360;
		}
    	
    	angles.set(angle1, angle2);
    	return angles;
    }
    
	static {
		for (int i = 0; i < SIN_TABLE.length; i++) {
            SIN_TABLE[i] = (float)Math.sin((double)i * Math.PI * 2.0D / 65536.0D);
        }
	}
}
