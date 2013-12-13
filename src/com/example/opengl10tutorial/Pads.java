package com.example.opengl10tutorial;

public class Pads extends Mesh {

	public Pads(float width, float height) {
		width /= 2.0f;
		height /= 2.0f;
		
		float vertices[] = {
				-width, height, 0.0f,	//v0 top left
				-width, -height, 0.0f,	//v1 bottom left
				width, -height, 0.0f,	//v2 bottom right
				width, height, 0.0f,	//v3 top right
			};
			
			short[] indices = {
				0,1,2,
				0,2,3	
			};
			setIndices(indices);
			setVertices(vertices);
	}

}
