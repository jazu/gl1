package com.example.opengl10tutorial;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;

public class Tutorial extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 this.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ); //pakota landscape orientaatio
		GLSurfaceView view = new MyRenderer(this);
		setContentView(view);
//		this.startActivity( new Intent(this,Obj3DView.class));
	}
}
