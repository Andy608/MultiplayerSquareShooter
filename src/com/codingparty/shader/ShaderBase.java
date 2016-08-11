package com.codingparty.shader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import math.Matrix3f;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.codingparty.core.IRelease;
import com.codingparty.file.FileResourceLocation;
import com.codingparty.file.ResourceDirectory;
import com.codingparty.logger.LoggerUtil;

public abstract class ShaderBase implements IRelease {

	protected static final ResourceDirectory RESOURCE_DIRECTORY = new ResourceDirectory("res/graphics", "shader", true);
	
	private int shaderProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private int projectionMatrixID;
	private int transformationMatrixID;
	private int viewMatrixID;
	private int normalMatrixID;
	
	private FileResourceLocation vertexLocation;
	private FileResourceLocation fragmentLocation;
	
	private FloatBuffer matrix4Buffer;
	private FloatBuffer matrix3Buffer;
	
	public ShaderBase(FileResourceLocation vertFileLocation, FileResourceLocation fragFileLocation) {
		shaderProgramID = GL20.glCreateProgram();
		vertexLocation = vertFileLocation;
		fragmentLocation = fragFileLocation;
		createVertexShader();
		createFragmentShader();
		link();
		bindUniformVariables();
	}
	
	/**
	 * Takes in the file location of the vertex shader and creates a vertex shader.
	 * @param fileLocation : The location of the shader.
	 */
	private void createVertexShader() {
		String vertexSource = readShaderFile(vertexLocation);
		vertexShaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertexShaderID, vertexSource);
		
		GL20.glCompileShader(vertexShaderID);
		if (GL20.glGetShaderi(vertexShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			LoggerUtil.logError(getClass(), new RuntimeException("[Unable to compile vertex shader: " + 
			vertexLocation.getFileNameWithExtension() + "] " + GL20.glGetShaderInfoLog(vertexShaderID, GL20.glGetShaderi(vertexShaderID, GL20.GL_INFO_LOG_LENGTH))));
		}
		GL20.glAttachShader(shaderProgramID, vertexShaderID);
	}
	
	/**
	 * Takes in the file location of the fragment shader and creates a fragment shader.
	 * @param fileLocation : The location of the shader.
	 */
	private void createFragmentShader() {
		String fragmentSource = readShaderFile(fragmentLocation);
		fragmentShaderID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragmentShaderID, fragmentSource);
		
		GL20.glCompileShader(fragmentShaderID);
		if (GL20.glGetShaderi(fragmentShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			LoggerUtil.logError(getClass(), new RuntimeException("[Unable to compile fragment shader: " + 
			fragmentLocation.getFileNameWithExtension() + "] " + GL20.glGetShaderInfoLog(fragmentShaderID, GL20.glGetShaderi(fragmentShaderID, GL20.GL_INFO_LOG_LENGTH))));
		}
		GL20.glAttachShader(shaderProgramID, fragmentShaderID);
	}
	
	private String readShaderFile(FileResourceLocation loc) {
		StringBuilder shaderSource = new StringBuilder();
		
		try (BufferedReader r = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + loc.getFullPath())))) {
			String s;
			while ((s = r.readLine()) != null) {
				shaderSource.append(s).append(System.lineSeparator());
			}
		} catch (Exception e) {
			LoggerUtil.logError(getClass(), e);
		}
		return shaderSource.toString();
	}
	
	/**
	 * Links the shader program to the current OpenGL context.
	 */
	private void link() {
		GL20.glLinkProgram(shaderProgramID);
		
		if (GL20.glGetProgrami(shaderProgramID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			LoggerUtil.logError(getClass(), new IllegalStateException("Unable to link shader program. Vertex Shader: " + vertexLocation.getFileNameWithExtension() 
			+ ". Fragment Shader: " + fragmentLocation.getFileNameWithExtension() + "."));
		}
	}
	
	protected void bindUniformVariables() {
		projectionMatrixID = getUniformLocation("projectionMatrix");
		transformationMatrixID = getUniformLocation("transformationMatrix");
		viewMatrixID = getUniformLocation("viewMatrix");
		normalMatrixID = getUniformLocation("normalMatrix");
	}
	
	public void bind() {
		GL20.glUseProgram(shaderProgramID);
	}
	
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	protected void loadFloats(int location, float v1, float v2, float v3) {
		GL20.glUniform3f(location, v1, v2, v3);
	}
	
	protected void load3DVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void load2DVector(int location, Vector2f vector) {
		GL20.glUniform2f(location, vector.x, vector.y);
	}
	
	protected void loadBoolean(int location, boolean value) {
		GL20.glUniform1f(location, value ? 1 : 0);
	}
	
	protected void load4Matrix(int location, Matrix4f matrix4f) {
		if (matrix4Buffer == null) {
			matrix4Buffer = BufferUtils.createFloatBuffer(16);
		}
		matrix4f.store(matrix4Buffer);
		matrix4Buffer.flip();
		GL20.glUniformMatrix4fv(location, false, matrix4Buffer);
	}
	
	protected void load3Matrix(int location, Matrix3f matrix3f) {
		if (matrix3Buffer == null) {
			matrix3Buffer = BufferUtils.createFloatBuffer(9);
		}
		matrix3f.store(matrix3Buffer);
		matrix3Buffer.flip();
		GL20.glUniformMatrix3fv(location, false, matrix3Buffer);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		load4Matrix(projectionMatrixID, matrix);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		load4Matrix(transformationMatrixID, matrix);
	}
	
	public void loadViewAndNormalMatrix(Matrix4f matrix) {
		load4Matrix(viewMatrixID, matrix);
		Matrix3f normalMatrix = new Matrix3f();
		
		normalMatrix.m00 = matrix.m00;
		normalMatrix.m01 = matrix.m01;
		normalMatrix.m02 = matrix.m02;
		
		normalMatrix.m10 = matrix.m10;
		normalMatrix.m11 = matrix.m11;
		normalMatrix.m12 = matrix.m12;
		
		normalMatrix.m20 = matrix.m20;
		normalMatrix.m21 = matrix.m21;
		normalMatrix.m22 = matrix.m22;
		
		normalMatrix.invert();
		normalMatrix.transpose();
		load3Matrix(normalMatrixID, normalMatrix);
	}
	
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(shaderProgramID, uniformName);
	}
	
	@Override
	public void release() {
		LoggerUtil.logInfo(getClass(), "Releasing shader.");
		unbind();
		
		GL20.glDetachShader(shaderProgramID, vertexShaderID);
		GL20.glDetachShader(shaderProgramID, fragmentShaderID);
		
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		
		GL20.glDeleteProgram(shaderProgramID);
	}
}
