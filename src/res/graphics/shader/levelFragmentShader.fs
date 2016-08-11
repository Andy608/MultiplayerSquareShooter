#version 430 core

in vec4 vColor;
centroid in vec2 vTextureCoords;

out vec4 color;

uniform sampler2D textureSampler;

void main() {
	color = vColor * texture(textureSampler, vTextureCoords);
}
