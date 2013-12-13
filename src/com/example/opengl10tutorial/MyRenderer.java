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

	float scrw = 0.0f;
	float scrh = 0.0f;
	
	float padw = 50.0f;
	float padh = 200.0f;
	
	float lpady = 0.0f;
	float lpadx = 0.0f;
	
	float rpady = 0.0f;
	float rpadx = 0.0f;
	
	float ballx = 0.0f;
	float bally = 0.0f;
	
	float ballstartx = 0;
	float ballstarty = 0;
	
	float ballxv = -5.0f;
	float ballyv = 5.0f;

	float padstartx = 0;
	float padstarty = 0;
	
	
	Square ball = new Square(100.0f, 100.0f);
	Square popup = new Square(500.0f, 500.0f);
	Pads lpad = new Pads(padw, padh);
	Pads rpad = new Pads(padw, padh);
	
	float[] varit = {	0.7f, 0.6f, 0.5f, 0.5f, // point 0 
						1.0f, 0.5f, 0.85f, 0.5f, // point 1 
						0.5f, 0.5f, 0.5f, 0.9f, // point 2 
						0.68f, 0.5f, 1.0f, 0.5f, // point 3
						0.5f, 0.5f, 0.5f, 0.5f, // point 4
						0.2f, 1f, 0.7f, 1f, // point 5
						0f, 0f, 1f, 1f, // point 6
						1f, 0.4f, 1f, 1f, // point 7
					};
	    						
	 private float StextureCoords[] = {    		
	    		//Mapping coordinates for the vertices
	    		0.0f, 0.0f,
	    		0.0f, 1.0f,
	    		1.0f, 1.0f,
	    		1.0f, 0.0f, 

	    							};
	//
	
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
		ball.setTextureCoords(StextureCoords);
		ball.loadGLTextures(gl, getContext(), R.drawable.guy2);
		popup.setTextureCoords(StextureCoords);
		popup.loadGLTextures(gl, getContext(), R.drawable.loser);
	}
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		gl.glPushMatrix();
		
		gl.glTranslatef(ballstartx, ballstarty, 0.0f);
		gl.glTranslatef(ballx, bally, 0.0f);
		ball.draw(gl);
		
		if(bally >= scrh/2-35) { 
			ballyv = -2;
		}
		if(bally <= -scrh/2+35) {
			ballyv = 2;
		}
		if(ballx <= -scrw/2+55 && bally <= lpady+100 && bally >= lpady-100) { 
			ballxv=5;
			}
		
		if(ballx >= scrw/2-55 && bally <= rpady+100 && bally >= rpady-100) { 
			ballxv=-5;
			}
	
		if (ballx <= -scrw/2 || ballx >= scrw/2) { //to do: popup for losing and resetting 
			gl.glPopMatrix();
			gl.glPushMatrix();
			gl.glTranslatef(ballstartx, ballstarty, 0.0f);
			popup.draw(gl);
			gl.glPopMatrix();
			
		}
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(scrw-scrw, padstarty, 0.0f);
		gl.glTranslatef(scrw-scrw, lpady, 0.0f);
		lpad.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(0, padstarty, 0.0f);
		gl.glTranslatef(scrw, rpady, 0.0f);
		rpad.draw(gl);
		gl.glPopMatrix();
		
		ballx+=ballxv;
		bally+=ballyv;
		
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		scrw = width;
		scrh = height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl,0,width, 0, height);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		ballstartx = scrw/2;
		ballstarty = scrh/2;
//		padstartx = scrw/2;
		padstarty = scrh/2;
		
	} 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float y = (getHeight()/2)-event.getY();
		float x = event.getX();
		int lside = this.getWidth() / 2;
				
				if(x < lside) {
					lpady = y;
				}
				
				if(x > lside) {
					rpady = y;
				}
		return true;
	}
}