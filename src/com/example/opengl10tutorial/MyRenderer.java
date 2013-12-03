package com.example.opengl10tutorial;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

import com.example.opengl10tutorial.Cube;
import com.example.opengl10tutorial.Square;

public class MyRenderer extends GLSurfaceView implements Renderer {
		
	public MyRenderer(Context context) {
		super(context);
		setRenderer(this);
	}

	Square square = new Square();	
	Plane plane = new Plane();
	
	Cube cube = new Cube(2,2,2);
	
	float angle = 1.0f;
	
	float[] varit = {	0.7f, 0.6f, 0.5f, 0.5f, // point 0 
						1.0f, 0.5f, 0.85f, 0.5f, // point 1 
						0.5f, 0.5f, 0.5f, 0.9f, // point 2 
						0.68f, 0.5f, 1.0f, 0.5f, // point 3
						0.5f, 0.5f, 0.5f, 0.5f, // point 4
						0.2f, 1f, 0.7f, 1f, // point 5
						0f, 0f, 1f, 1f, // point 6
						1f, 0.4f, 1f, 1f, // point 7
					};
	 private float CtextureCoords[] = {    		
	    		//Mapping coordinates for the vertices
	    		0.0f, 1.0f,
	    		1.0f, 1.0f,
	    		1.0f, 0.0f,
	    		0.0f, 0.0f, 
	    		
	    		0.0f, 1.0f,
	    		1.0f, 1.0f,
	    		1.0f, 0.0f,
	    		0.0f, 0.0f, 

	    							};
	 private float StextureCoords[] = {    		
	    		//Mapping coordinates for the vertices
	    		0.0f, 0.0f,
	    		0.0f, 1.0f,
	    		1.0f, 1.0f,
	    		1.0f, 0.0f, 

	    							};
	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glClearColor(0.9f, 0.5f, 0.0f, 0.8f);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		cube.setColors(varit);
		cube.setTextureCoords(CtextureCoords);
		cube.loadGLTextures(gl, getContext(), R.drawable.nehe);
		square.setTextureCoords(StextureCoords);
		square.loadGLTextures(gl, getContext(), R.drawable.guy2);
	}
	
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f,0.0f,-10.0f);
//		gl.glRotatef(angle, 1.0f, 1.0f, 0.0f);
		square.draw(gl);
//		plane.draw(gl);
//		cube.draw(gl);	
//		angle++;
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height,
						   0.1f, 100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
	
	} 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		float oldX = x;
		float oldY = y;
		
		if(event.getAction() == MotionEvent.ACTION_MOVE) {
			float eroVanhaanXnaattiin = x - oldX;
			float eroVanhaanYnaattiin = y - oldY;
			
		//	// int  = this.getHeight() / 10;
			
		}
		return true;
	}
}

