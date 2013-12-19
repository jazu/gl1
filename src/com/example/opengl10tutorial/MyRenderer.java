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
	// add a 3s timer when no touches register between when game has ended and user presses restart so no accidental restarts happen
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
	
	float maxxv = 25.0f;
	float maxyv = 25.0f;
	
	float angle = 0.0f;
	
	static final int STATE_STARTED = 1;
	static final int STATE_GAMEOVER = 2;
	static final int STATE_MENU = 3;
	static final int STATE_ABOUT = 4;
	static final int STATE_EXIT = 5;
	
	int gameState = STATE_MENU;  
	
	
	Square ball = new Square(squarew, squareh);
	Square popup = new Square(500.0f, 500.0f);
	Square menustart = new Square(250.0f, 250.0f);
	Square menuexit = new Square(250.0f, 250.0f);
	Square menuabout = new Square(250.0f, 250.0f);
	Square about = new Square(720.0f, 720.0f);
	Square aboutVer = new Square(200.0f, 200.0f);
	
	Pads aboutback = new Pads (300.0f, 720.0f);
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
		menustart.setTextureCoords(StextureCoords);
		menustart.loadGLTextures(gl, getContext(), R.drawable.start);
		menuabout.setTextureCoords(StextureCoords);
		menuabout.loadGLTextures(gl, getContext(), R.drawable.about);
		menuexit.setTextureCoords(StextureCoords);
		menuexit.loadGLTextures(gl, getContext(), R.drawable.exit);
		about.setTextureCoords(StextureCoords);
		about.loadGLTextures(gl, getContext(), R.drawable.abouts);
		aboutVer.setTextureCoords(StextureCoords);
		aboutVer.loadGLTextures(gl, getContext(), R.drawable.aboutv);
	}
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		if(gameState == STATE_MENU) {
			gl.glPushMatrix();
			gl.glTranslatef(scrw/2, scrh/2, 0.0f);
			gl.glRotatef(angle, 0.0f, 0.0f, 0.0f);
			menustart.draw(gl);
			gl.glPopMatrix();
		
			gl.glPushMatrix();
			gl.glTranslatef(scrw/1.3f, scrh/1.4f, 0.0f);
			gl.glRotatef(angle, 0.0f, 0.0f, 0.0f);
			menuabout.draw(gl);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslatef(scrw/5, scrh/5, 0.0f); 
			gl.glRotatef(angle, 0.0f, 0.0f, 0.0f);
			menuexit.draw(gl);
			gl.glPopMatrix();
			
			angle++;
			if(angle >= 90) {
				angle = -angle;
			}
		}
		
		if(gameState == STATE_ABOUT) {
			gl.glPushMatrix();
			gl.glTranslatef(scrw/2, scrh/2, 0.0f);
			about.draw(gl);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslatef(scrw/1.1f, scrh/7.0f, 0.0f);
			aboutVer.draw(gl);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslatef(0, scrh/2, 0.0f);
			aboutback.draw(gl);
			gl.glPopMatrix();
		}
	
		if(gameState == STATE_STARTED) {
			
				gl.glPushMatrix();
				gl.glTranslatef(ballx, bally, 0.0f);
				ball.draw(gl);
				
				if(bally >= scrh-(squarew/2) || bally <= (squarew/2)) { 
					ballyv = -ballyv*1.02f;
				}
				if((ballx <= (padw/2)+(squarew/2) && bally <= lpady+(padh/2)+(squareh/2) && bally >= lpady-(padh/2)-(squareh/2)) || //check paddle-ball collision  
				   (ballx >= scrw-(padw/2)-(squarew/2) && bally <= rpady+(padh/2)+(squareh/2) && bally >= rpady-(padh/2)-(squareh/2))) { 
					ballx = (padw/2)+(squarew/2);
					ballxv = -ballxv*1.1f; 
					
					if(ballxv > 0 )	{
						ballx = (padw/2)+(squarew/2);
						}
						else if(ballxv < 0){ 
							ballxv = +ballxv*1.1f; 
							ballx = scrw-(padw/2)-(squarew/2);
							}
					}
				
				if(ballxv <= -maxxv){  //set max speed for x and y
					ballxv = -25.0f;
				}
				if(ballxv >= maxxv) {
					ballxv = 25.0f;
				}
				if(ballyv <= -maxyv){
					ballyv = -25.0f;
				}
				if(ballyv >= maxyv) {
					ballyv = 25.0f;
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
	
		if (ballx <= 0 || ballx >= scrw) {
			gameState = STATE_GAMEOVER;
		}
		
		if(gameState == STATE_GAMEOVER) {
		
				//popup for losing and resetting 
				gl.glPopMatrix();
				gl.glPushMatrix();
				gl.glTranslatef(scrw/2, scrh/2, 0.0f);
				popup.draw(gl);
				gl.glPopMatrix();
			}
		
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

		
		if(gameState == STATE_MENU) {
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x <= (scrw/2)+125) && (x >= (scrw/2)-125) && (y <= (scrh/2)+125) && (y >= (scrh/2)-125)) {
				gameState = STATE_STARTED;
			}
			
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x <= (scrw/5)+125) && (x >= (scrw/5)-125) && (y <= (scrh/5)+125) && (y >= (scrh/5)-125)) {
				gameState = STATE_EXIT;
			}
				
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x <= (scrw/1.3f)+125) && (x >= (scrw/1.3f)-125) && (y <= (scrh/1.4)+125) && (y >= (scrh/1.4f)-125)) {
				gameState = STATE_ABOUT;
			}
		}
		
		if(gameState == STATE_ABOUT) { 
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x <= 150) && (y <= (scrh/2)+360) && (y >= (scrh/2)-360)) {
				gameState = STATE_MENU;
			}
		}
		
		if(gameState == STATE_GAMEOVER) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {  //reset if game has ended and 
					gameState = STATE_STARTED;						//the user touches screen
					ballx = ballstartx;																	  			 
					bally = ballstarty;
					ballxv = -5.0f;
					ballyv = -5.0f;
					
					
				}
		}
		
		if(gameState == STATE_STARTED) {
				if(event.getAction() == MotionEvent.ACTION_MOVE || ballx <= 0-(scrw/2) || ballx >= scrw+(scrw/2)) {
				
				            	if(x < lside) {
				    				lpady = y;
				    			}
					
				    			if(x > lside) {
				    				rpady = y;
				    			}
				        }
		}
		return true;
		}
	
	}