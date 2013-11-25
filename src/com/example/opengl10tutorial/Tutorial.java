package com.example.opengl10tutorial;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;

public class Tutorial extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		GLSurfaceView view = new GLSurfaceView(this);
		view.setRenderer(new MyRenderer());
		setContentView(view);
	}
}
