package com.example.opengl10tutorial;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

import com.example.opengl10tutorial.R;

public class MyRenderer extends GLSurfaceView implements Renderer {

//TODO:
	// multitouch
	//
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
	
	float ballstartx = 0.0f;
	float ballstarty = 0.0f;
	
	float ballxv = -5.0f;
	float ballyv = -5.0f;

	float padstarty = 0;
	
	float squarew = 100.0f;
	float squareh = 100.0f;
	
	float maxxv = 20.0f;
	float maxyv = 20.0f;
	
	Square ball = new Square(squarew, squareh);
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
		
		gl.glTranslatef(ballx, bally, 0.0f);
		ball.draw(gl);
		
		if(bally >= scrh-(squarew/2) || bally <= (squarew/2)) { 
			ballyv = -ballyv;
		}
		if((ballx <= (padw/2)+(squarew/2) && bally <= lpady+(padh/2)+(squareh/2) && bally >= lpady-(padh/2)-(squareh/2)) || //check paddle-ball collision  
		   (ballx >= scrw-(padw/2)-(squarew/2) && bally <= rpady+(padh/2) && bally >= rpady-(padh/2))) { 
			ballxv = -ballxv*1.1f; 
			}
	
//		if(ballx <= ){ //check if ball is inside the pad, if so translate ballx EXACTLY outside the padw/2 
//			
//		}
		
		if (ballx <= 0 || ballx >= scrw) { //popup for losing and resetting 
			gl.glPopMatrix();
			gl.glPushMatrix();
			gl.glTranslatef(scrw/2, scrh/2, 0.0f);
			popup.draw(gl);
			gl.glPopMatrix();
			
		}
		
		if(ballxv >= maxxv ||  ballyv >= maxyv) {  //set max speed for x and y
			ballxv = -19.0f;
			ballyv = -19.0f;
		}
															//TODO: now if either one (xv or yv) is over the limit both will be reduced to 19, make them separate!
		if(ballxv <= -maxxv || ballyv <= -maxyv) {
			ballxv = 19.0f;
			ballyv = 19.0f;
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
//		bally+=ballyv;
		
		
		
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
		ballx = ballstartx;
		bally = ballstarty;
		padstarty = scrh-scrh;
		
	} 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float y = getHeight()-event.getY();
		float x = event.getX();
		int lside = this.getWidth() / 2;
		
		if(event.getAction() == MotionEvent.ACTION_DOWN && ballx <= scrw-scrw-(scrw/2) || ballx >= scrw+(scrw/5)) {  //reset if game has ended and 
			ballx = ballstartx;																	   //the user touches screen
			bally = ballstarty;
			ballxv = -10.0f;
			ballyv = -10.0f;
		}
		
		if(event.getAction() == MotionEvent.ACTION_MOVE) {
			
			
		            	if(x < lside) {
		    				lpady = y;
		    			}
			
		    			if(x > lside) {
		    				rpady = y;
		    			}
		        }
		return true;
		}
	}
