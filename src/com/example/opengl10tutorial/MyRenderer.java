package com.example.opengl10tutorial;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import com.example.opengl10tutorial.Cube;
import com.example.opengl10tutorial.Square;

public class MyRenderer implements Renderer {
		
	Square square = new Square();	
	Plane plane = new Plane();
	
	Cube cube = new Cube(1,1,1);
	
	float angle = 1.0f;
	
	float[] varit = {	1f, 0.6f, 0f, 1f, // point 0 
						0f, 1f, 0.6f, 1f, // point 1 
						0f, 0f, 0.667f, 1f, // point 2 
						1f, 0f, 1f, 1f, // point 3
						1f, 0f, 0f, 1f, // point 4
						0.2f, 1f, 0.7f, 1f, // point 5
						0f, 0f, 1f, 1f, // point 6
						1f, 0.4f, 1f, 1f, // point 7
					};
	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.9f, 0.5f, 0.0f, 0.8f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		cube.setColors(varit);
	}
	
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f,0.0f,-10.0f);
		gl.glRotatef(angle, 1.0f, 1.0f, 0.0f);
//		square.draw(gl);
//		plane.draw(gl);
		cube.draw(gl);		
		angle++;
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height,
						   0.1f, 100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
	
	} 
}

