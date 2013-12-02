package com.example.opengl10tutorial;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Mesh {

	private FloatBuffer verticesBuffer = null;
	private ShortBuffer indicesBuffer = null;
	private int numOfIndices = -1;
	
	private float[] rgba = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
	private FloatBuffer colorBuffer = null;
	private FloatBuffer texBuffer = null;
	
	public float x = 0;
	public float y = 0;
	public float z = 0;
	
	public float rx = 0;
	public float ry = 0;
	public float rz = 0;
	
	public void draw(GL10 gl) {
		
	
		
		gl.glFrontFace(GL10.GL_CCW);
	//	gl.glEnable(GL10.GL_CULL_FACE); //enable only if there is no rotation
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
		gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
			if ( colorBuffer != null ) {
				gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
			}
		gl.glTranslatef(x, y, z);
		gl.glRotatef(rx, 1, 0, 0);
		gl.glRotatef(ry, 0, 1, 0);
		gl.glRotatef(rz, 0, 0, 1);
		
		
		
		gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices, GL10.GL_UNSIGNED_SHORT, indicesBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	//	gl.glDisable(GL10.GL_CULL_FACE); //enable only if there is no rotation
	}
	
	protected void setVertices(float[] vertices) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		verticesBuffer = vbb.asFloatBuffer();
		verticesBuffer.put(vertices);
		verticesBuffer.position(0);
	}

	protected void setIndices(short[] indices) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indicesBuffer = ibb.asShortBuffer();
		indicesBuffer.put(indices);
		indicesBuffer.position(0);
		numOfIndices = indices.length;
	}

	protected void setColor(float red, float green, float blue, float alpha) {
		rgba[0] = red;
		rgba[1] = green;
		rgba[2] = blue;
		rgba[3] = alpha;
	}
	
	protected void setColors(float[] colors) {
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}
}
