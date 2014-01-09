package com.example.opengl10tutorial;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

import com.example.opengl10tutorial.R;

public class MyRenderer extends GLSurfaceView implements Renderer {
	//TODO:
	// change packet name
	// make new signing keys
	// MAYBE add menu/game music
	// test app on diff screens
	// comment the code
	public MyRenderer(Context context) {
		super(context);
		setRenderer(this);
	}

	float scrw = 0.0f;
	float scrh = 0.0f;
	float scrw1 = 0.0f;
	float scrh1 = 0.0f;
	
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
	
	float maxxv = 30.0f;
	float maxyv = 30.0f;
	
	float angle = 0.0f;
	
	int lscore = 0;
	int rscore = 0;
	
	float buttonheightratio = 0;
	float buttonwidhtratio = 0;
	
	//
	
	static final int STATE_STARTED = 1;
	static final int STATE_GAMEOVER = 2;
	static final int STATE_MENU = 3;
	static final int STATE_ABOUT = 4;
	static final int STATE_HELP = 5;
	
	int gameState = STATE_MENU;  
	
	
	Square ball = new Square(squarew, squareh);
	Square popup = new Square(1280f, 1280f);
	Square menustart = new Square(250.0f, 250.0f);
	Square menuhelp = new Square(250.0f, 250.0f);
	Square menuabout = new Square(250.0f, 250.0f);
	Square bg = new Square(1280f, 1280f);
	Square aboutbg = new Square(1280f, 1280f);
	Square bg2 = new Square(1280f, 1280f); 
	Square aboutback = new Square (150.0f, 720.0f);
	Square replay = new Square (200.0f, 200.0f);
	Square bghelp = new Square(1280f, 1280f); 
	Square scorebg = new Square (200.0f, 200.0f);
	Square scoreright = new Square(100.0f, 100.0f); 
	Square scoreleft = new Square(100.0f, 100.0f);
	Square[] numbers = new Square[10];
	
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
		menuhelp.setTextureCoords(StextureCoords);
		menuhelp.loadGLTextures(gl, getContext(), R.drawable.help);
		aboutbg.setTextureCoords(StextureCoords);
		aboutbg.loadGLTextures(gl, getContext(), R.drawable.aboutbg);
		bg.setTextureCoords(StextureCoords);
		bg.loadGLTextures(gl, getContext(), R.drawable.bg);
		aboutback.setTextureCoords(StextureCoords);
		aboutback.loadGLTextures(gl, getContext(), R.drawable.back);
		replay.setTextureCoords(StextureCoords);
		replay.loadGLTextures(gl, getContext(), R.drawable.replay);
		bg2.setTextureCoords(StextureCoords);
		bg2.loadGLTextures(gl, getContext(), R.drawable.bg2);
		bghelp.setTextureCoords(StextureCoords);
		bghelp.loadGLTextures(gl, getContext(), R.drawable.bghelp);
		scorebg.setTextureCoords(StextureCoords);
		scorebg.loadGLTextures(gl, getContext(), R.drawable.scorebg);
		scoreright.setTextureCoords(StextureCoords);
		scoreright.loadGLTextures(gl, getContext(), R.drawable.score0);
		scoreleft.setTextureCoords(StextureCoords);
		scoreleft.loadGLTextures(gl, getContext(), R.drawable.score0);
		for ( int i = 0; i < 10; i++ ) {
			numbers[i] = new Square(100, 100);
			numbers[i].setTextureCoords(StextureCoords);
			int rid = getResources().getIdentifier("score"+String.valueOf(i), "drawable", "com.example.opengl10tutorial");
			numbers[i].loadGLTextures(gl, getContext(), rid);
		}
	}
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glEnable(GL10.GL_TEXTURE_2D);  //needed for emulator to draw the game
	
		if(gameState == STATE_MENU) {
			
			rscore = 0;
			lscore = 0;
		//	
			gl.glPushMatrix();
			gl.glTranslatef(scrw1/2, scrh1/2, 0.0f);
			bg.draw(gl);
			gl.glPopMatrix();
			
		
			gl.glPushMatrix();
			gl.glTranslatef(scrw1/2, scrh1/2, 0.0f);
			gl.glRotatef(angle, 0.0f, 0.0f, 0.0f);
			menustart.draw(gl);
			gl.glPopMatrix();
		
			gl.glPushMatrix();
			gl.glTranslatef(scrw1/1.2f, scrh1/2, 0.0f);
			gl.glRotatef(angle, 0.0f, 0.0f, 0.0f);
			menuabout.draw(gl);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslatef(scrw1/6, scrh1/2, 0.0f); 
			gl.glRotatef(angle, 0.0f, 0.0f, 0.0f);
			menuhelp.draw(gl);
			gl.glPopMatrix();
			
			angle++;
			if(angle >= 90) {
				angle = -angle;
			}
		}
		
		if(gameState == STATE_ABOUT) {
			gl.glPushMatrix();
			gl.glTranslatef(scrw1/2, scrh1/2, 0.0f);
			aboutbg.draw(gl);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslatef(scrw1/17.066f, scrh1/2, 0.0f);
			aboutback.draw(gl);
			gl.glPopMatrix();
		}
	
		if(gameState == STATE_STARTED) {
			
				gl.glPushMatrix();
				gl.glTranslatef(scrw1/2, scrh1/2, 0.0f);
				bg2.draw(gl); 
				gl.glPopMatrix();
				
				gl.glPushMatrix();
				gl.glTranslatef(ballx, bally, 0.0f);
				ball.draw(gl);
				gl.glPopMatrix();
			
				if(bally >= scrh1-(squarew/2) || bally <= (squarew/2)) { 
					ballyv = -ballyv*1.02f;
				}
				if((ballx <= (padw/2)+(squarew/2) && bally <= lpady+(padh/2)+(squareh/2) && bally >= lpady-(padh/2)-(squareh/2)) || //check paddle-ball collision  
				   (ballx >= scrw1-(padw/2)-(squarew/2) && bally <= rpady+(padh/2)+(squareh/2) && bally >= rpady-(padh/2)-(squareh/2))) { 
					ballx = (padw/2)+(squarew/2);
					ballxv = -ballxv*1.1f; 
					
					if(ballxv > 0 )	{
						ballx = (padw/2)+(squarew/2);
						}
						else if(ballxv < 0){ 
							ballxv = +ballxv*1.1f; 
							ballx = scrw1-(padw/2)-(squarew/2);
							}
					}
				
				if(ballxv <= -maxxv){  //set max speed for x and y
					ballxv = -30.0f;
				}
				if(ballxv >= maxxv) {
					ballxv = 30.0f;
				}
				if(ballyv <= -maxyv){
					ballyv = -30.0f;
				}
				if(ballyv >= maxyv) {
					ballyv = 30.0f;
				}
				
				gl.glPopMatrix();
				
				gl.glPushMatrix();
				gl.glTranslatef(scrw1-scrw1, padstarty, 0.0f);
				gl.glTranslatef(0, lpady, 0.0f);
				lpad.draw(gl);
				gl.glPopMatrix();
		
				gl.glPushMatrix();
				gl.glTranslatef(0, padstarty, 0.0f);
				gl.glTranslatef(scrw1, rpady, 0.0f);
				rpad.draw(gl);
				gl.glPopMatrix();
				
				ballx+=ballxv;
				bally+=ballyv;
				
		}
	
		if(ballx <= 0) {
				if(rscore <= 8) {
					rscore += 1;
				} 
				gameState = STATE_GAMEOVER;
			}
		if(ballx >= scrw1) {
				if(lscore <= 8){
					lscore += 1;
				}
				gameState = STATE_GAMEOVER;
			}
		
		if(gameState == STATE_GAMEOVER) {
			
				//popup for losing and resetting 
				gl.glPopMatrix();
				gl.glPushMatrix();
				gl.glTranslatef(scrw1/2, scrh1/2, 0.0f);
				popup.draw(gl);
				gl.glPopMatrix();
				
				gl.glPushMatrix();
				gl.glTranslatef(scrw1/17.066f, scrh1/2, 0.0f);
				aboutback.draw(gl);
				gl.glPopMatrix();
				
				gl.glPushMatrix();
				gl.glTranslatef(scrw1/1.2f, scrh1/1.2f, 0.0f);
				replay.draw(gl);
				gl.glPopMatrix();
				
				gl.glPushMatrix();
				gl.glTranslatef(scrw1/1.15f, scrh1/5.2f, 0.0f);
				scorebg.draw(gl);
				gl.glPopMatrix();
				
				// rscore
				gl.glPushMatrix();
				gl.glTranslatef(scrw1/1.1f, scrh1/5.2f, 0.0f);
				numbers[rscore].draw(gl);
				gl.glPopMatrix();
				
				// lscore
				gl.glPushMatrix();
				gl.glTranslatef(scrw1/1.2f, scrh1/5.2f, 0.0f);
				numbers[lscore].draw(gl);
				gl.glPopMatrix();			
				
				gl.glPushMatrix();
				ballx = ballstartx;                                                                                                                                                                 
                bally = ballstarty;
                ballxv = -5.0f;
                ballyv = -5.0f;
                gl.glPopMatrix();
                
				
			}
		
			if(gameState == STATE_HELP) {
				
				gl.glPushMatrix();
				gl.glTranslatef(scrw1/2, scrh1/2, 0.0f);
				bghelp.draw(gl);
				gl.glPopMatrix();
				

				gl.glPushMatrix();
				gl.glTranslatef(scrw1/17.066f, scrh1/2, 0.0f);
				aboutback.draw(gl);
				gl.glPopMatrix();
			}
			//		
		}
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		scrw = width;
		scrh = height;
		scrw1 = 1280f;
		scrh1 = 720f;
		
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl,0,1280, 0, 720);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		ballstartx = scrw1/2;
		ballstarty = scrh1/2;
		ballx = ballstartx;
		bally = ballstarty;
	//
	} 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float y = scrh-event.getY();
		float x = event.getX();
		float lside = scrw / 2; 
		int pointerIndex = 0;
		int pointerId = 0;
		int pointerCount = event.getPointerCount();

		

	      	if(gameState == STATE_HELP) {
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x <= scrw/8.53f) && (y <= scrh) && (y >= 0)) {   	
				gameState = STATE_MENU;
			}
		}
		
		if(gameState == STATE_MENU) {
			
		buttonheightratio = ((scrh/2.88f)/2.0f);
		buttonwidhtratio = ((scrw/2.88f)/2.0f);	
		
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x <= (scrw/2)+buttonwidhtratio) && (x >= (scrw/2)-buttonwidhtratio) && (y <= (scrh/2)+buttonheightratio && (y >= (scrh/2)-buttonheightratio))) {
				gameState = STATE_STARTED;
			}
			
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x <= (scrw/6)+buttonwidhtratio) && (x >= (scrw/6)-buttonwidhtratio) && (y <= (scrh/2)+buttonheightratio) && (y >= (scrh/2)-buttonheightratio)) {
				gameState = STATE_HELP;
			}
	//			
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x <= (scrw/1.2f)+buttonwidhtratio) && (x >= (scrw/1.2f)-buttonwidhtratio) && (y <= (scrh/2)+buttonheightratio) && (y >= (scrh/2)-buttonheightratio)) {
				gameState = STATE_ABOUT;
			}
		}
		
		if(gameState == STATE_ABOUT) { 
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x <= scrw/8.53f) && (y <= scrh) && (y >= 0)) {
				gameState = STATE_MENU;
			}
		}
		
		if(gameState == STATE_GAMEOVER) {
			
			
			                        
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x <= scrw/8.53f) && (y <= scrh) && (y >= 0)) {   
				gameState = STATE_MENU;
			}
			if((event.getAction() == MotionEvent.ACTION_DOWN) && (x >= (scrw/1.2f)-(scrw/7.2f)) && (x <= (scrw/1.2f)+(scrw/7.2f)) && (y <= (scrh/1.2f)+(scrw/3.6f)) && (y >= (scrh/1.2f)-(scrw/3.6f))) {
			
				gameState = STATE_STARTED;
				}
		}
		
		/*if(gameState == STATE_STARTED) {
				if(event.getAction() == MotionEvent.ACTION_MOVE) {
				
		            	if(x < lside) {
		    				lpady = y;
		    			}
			
		    			if(x > lside) {
		    				rpady = y;
		    			}
		        } 
		
		}*/
		
		//
		
		if(gameState == STATE_STARTED) {
	                pointerId = event.getPointerId(pointerIndex);
	                pointerIndex = event.getActionIndex();
				
			switch(event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_MOVE: {
						for (int i = 0; i < pointerCount; i++) {
							int curPointerId = event.getPointerId(i);
							if(event.getX(i) < lside) {
								lpady = scrh-event.getY(curPointerId);
							} else{
								rpady = scrh-event.getY(curPointerId);
							}
						}
						return true;
		        }
				
				case MotionEvent.ACTION_DOWN: {
					if(event.getX(pointerIndex) < lside) {
						lpady = scrh-event.getY(pointerIndex);
					} else {
						rpady = scrh-event.getY(pointerIndex);
					}
					return true;
				}
				case MotionEvent.ACTION_POINTER_DOWN: {
					if(event.getX(pointerIndex) < lside) {
						lpady = scrh-event.getY(pointerIndex);
					} else {
						rpady = scrh-event.getY(pointerIndex);
					}
					return true;
				
				}
		}
	}
		return true;
}
}