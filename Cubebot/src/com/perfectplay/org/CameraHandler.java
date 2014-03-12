package com.perfectplay.org;

import com.badlogic.gdx.graphics.PerspectiveCamera;

public class CameraHandler {
	private PerspectiveCamera cam;
	private int destinationRotation;
	private float exactRotation;
	private int rotation;
	boolean isActive;
	
	public CameraHandler(PerspectiveCamera camera, int origRotation)
	{
		destinationRotation = origRotation;
		rotation = origRotation;
		cam = camera;
		
		exactRotation = rotation * (float)Math.PI / 180f;
		
		float x = 21 * (float) Math.cos((double) exactRotation);
		float y = -3f;
		float z = 21 * (float) Math.sin((double) exactRotation);
		
		cam.position.set(x, y, z);
		cam.lookAt(0, -3, 0);
		cam.near = 0.1f;
		cam.far = 300f;
		
		cam.update();
	}
	
	public void setRotation(int newRotation)
	{
		destinationRotation = newRotation;
		isActive = true;
	}
	
	public void update()
	{
		if (!isActive) return;
		
		rotation ++;
		
		if(rotation == 360) rotation = 0;
			
		exactRotation = rotation * (float)Math.PI / 180f;
		
		float x = 21 * (float) Math.cos((double) exactRotation);
		float y = -3f;
		float z = 21 * (float) Math.sin((double) exactRotation);
		
		cam.position.set(x, y, z);
		cam.lookAt(0, -3f, 0);
		cam.near = 0.1f;
		cam.far = 300f;
		
		cam.update();
		
		System.out.println(rotation + ", " + destinationRotation);
		
		if(rotation == destinationRotation)
			isActive = false;
	}
	
}
