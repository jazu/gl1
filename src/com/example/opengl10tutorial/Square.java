package com.example.opengl10tutorial;


	public class Square extends Mesh {
	
	public Square() {
		float vertices[] = {
			-1.0f, 1.0f, 0.0f,	//v0 top left
			-1.0f, -1.0f, 0.0f,	//v1 bottom left
			1.0f, -1.0f, 0.0f,	//v2 bottom right
			1.0f, 1.0f, 0.0f,	//v3 top right
		};
		
		short[] indices = {
			0,1,2,
			0,2,3	
		};
		setIndices(indices);
		setVertices(vertices);
}
	}
