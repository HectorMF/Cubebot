package com.perfectplay.org;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class CameraHandler extends CameraInputController{
	private PerspectiveCamera cam;
	boolean isActive;
	Tween tween;
	Tween rotation;
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int FRONT = 2;
	public static final int BACK = 3;
	public static final int TOP = 4;
	
	public CameraHandler(PerspectiveCamera camera)
	{
		super(camera);
		cam = camera;
		
		tween = Tween.set(cam, CameraAccessor.POSITION).target(0,-3,-21).start();
		rotation = Tween.set(cam, CameraAccessor.ROTATION).target(0,1,0).start();
	
		tween.update(1);
		rotation.update(1);
		
		cam.lookAt(0, -3, 0);
		cam.near = 0.1f;
		cam.far = 300f;
		cam.update();
	}
	
	@Override
	public boolean keyDown(int keycode){
		return false;
	}
	@Override
	public boolean keyUp(int keycode){
		return false;
	}
	
	@Override
	public boolean keyTyped(char keycode){
		return false;
	}

	public void setRotation(int newRotation)
	{
		isActive = true;
		switch(newRotation){
		case RIGHT:
			tween = Tween.to(cam, CameraAccessor.POSITION, 1).target(-21,-3,0).start();
			rotation = Tween.to(cam, CameraAccessor.ROTATION, 1).target(0,1,0).start();
			break;
		case LEFT:
			tween = Tween.to(cam, CameraAccessor.POSITION, 1).target(21,-3,0).start();
			rotation = Tween.to(cam, CameraAccessor.ROTATION, 1).target(0,1,0).start();
			break;
		case FRONT:
			tween = Tween.to(cam, CameraAccessor.POSITION, 1).target(0,-3,-21).start();
			rotation = Tween.to(cam, CameraAccessor.ROTATION, 1).target(0,1,0).start();
			break;
		case BACK:
			tween = Tween.to(cam, CameraAccessor.POSITION, 1).target(0,-3, 21).start();
			rotation = Tween.to(cam, CameraAccessor.ROTATION, 1).target(0,1,0).start();
			break;
		case TOP:
			tween = Tween.to(cam, CameraAccessor.POSITION, 1).target(0,21, 0).start();
			rotation = Tween.to(cam, CameraAccessor.ROTATION, 1).target(0,0,1).start();
			break;
		default:
			break;
		}
	}
	
	public void update()
	{
		if (!isActive) return;
		tween.update(Gdx.graphics.getDeltaTime());
		rotation.update(Gdx.graphics.getDeltaTime());
		cam.lookAt(0, -3f, 0);
		cam.update();
		if(tween.isFinished())
			isActive = false;
	}
	
}
