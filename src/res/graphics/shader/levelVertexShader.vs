#version 430 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec4 color;
layout(location = 3) in vec2 textureCoords;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;

uniform vec3 cameraPosition;
uniform vec3 debugCameraPosition;

uniform vec3 entityColor;

out vec4 vColor;
out vec2 vTextureCoords;

void main() {
	vColor = vec4(entityColor, 1.0);
	vTextureCoords = textureCoords;
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
}
