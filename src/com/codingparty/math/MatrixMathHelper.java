package com.codingparty.math;

import math.Matrix4f;
import math.Vector3f;

public class MatrixMathHelper {
	public static final Vector3f X_AXIS = new Vector3f(1, 0, 0);
	public static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);
	public static final Vector3f Z_AXIS = new Vector3f(0, 0, 1);
	private static final Vector3f DEFAULT_SCALE = new Vector3f(1, 1, 1);
	
	public static final Matrix4f DEFAULT_SCALE_MATRIX = new Matrix4f();
	
	public static Matrix4f rotateMatrix(Matrix4f source, float x, float y, float z) {
		source.rotate((float)(Math.toRadians(x)), X_AXIS);
		source.rotate((float)(Math.toRadians(y)), Y_AXIS);
		source.rotate((float)(Math.toRadians(z)), Z_AXIS);
		return source;
	}
	
	public static Matrix4f rotateMatrix(Matrix4f source, Vector3f rotation) {
		source.rotate((float)(Math.toRadians(rotation.x)), X_AXIS);
		source.rotate((float)(Math.toRadians(rotation.y)), Y_AXIS);
		source.rotate((float)(Math.toRadians(rotation.z)), Z_AXIS);
		return source;
	}
	
	public static Matrix4f scaleMatrix(Matrix4f source, Vector3f scale) {
		if (scale == null) source.scale(DEFAULT_SCALE);
		else source.scale(scale);
		return source;
	}
	
	public static Matrix4f translateMatrix(Matrix4f source, Vector3f translation) {
		source.translate(translation);
		return source;
	}
	
	/**
	 * Builds a custom transformation matrix depending on the order of v1, v2, and v3.
	 * @param source : The transformationMatrix source.
	 * @param m1 : The first matrix to be multiplied.
	 * @param m2 : The second matrix to be multiplied.
	 * @param m3 : The third matrix to be multiplied.
	 */
	public static Matrix4f buildCustomTransformationMatrix(Matrix4f source, Matrix4f m1, Matrix4f m2, Matrix4f m3) {
		//A REGULAR TRANSFORMATION MATRIX WOULD COME OUT OF: m1 = scale, m2 = rotation, m3 = translation
		//A TRANSFORMATION MATRIX THAT ROTATED ABOUT A POINT IN SPACE WOULD COME OUT OF: m1 = scale, m2 = translation, m3 = rotation
		Matrix4f.mul(source, m1, source);
		Matrix4f.mul(source, m2, source);
		Matrix4f.mul(source, m3, source);
		return source;
	}
	
	public static Matrix4f buildNormalTransformationMatrix(Matrix4f source, Vector3f translation, Vector3f rotation, Vector3f scale) {
		if (translation != null) source.translate(translation);
		
		if (rotation != null) {
			source.rotate((float)(Math.toRadians(rotation.x)), X_AXIS);
			source.rotate((float)(Math.toRadians(rotation.y)), Y_AXIS);
			source.rotate((float)(Math.toRadians(rotation.z)), Z_AXIS);
		}
		
		if (scale == null) source.scale(DEFAULT_SCALE);
		else source.scale(scale);
		return source;
	}
	
	public static void buildNormalTransformationMatrix(Matrix4f source, Vector3f translation, Vector3f rotation) {
		buildNormalTransformationMatrix(source, translation, rotation, null);
	}
	
	public static void buildNormalTransformationMatrix(Matrix4f source, Vector3f translation) {
		buildNormalTransformationMatrix(source, translation, null);
	}
}
