#version 330 core

layout (location = 0) in vec3 aPos;

uniform mat4 wvpMatrix;
uniform vec4 diffuse;

out vec4 vertexColor;

void main() {
    gl_Position = wvpMatrix * vec4(aPos, 1.0);
    vertexColor = diffuse;
}