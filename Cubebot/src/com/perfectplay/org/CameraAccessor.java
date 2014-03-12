package com.perfectplay.org;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.PerspectiveCamera;

public class CameraAccessor implements TweenAccessor<PerspectiveCamera> {

	public static final int POSITION = 0;
	public static final int ROTATION = 1;
	@Override
	public int getValues(PerspectiveCamera target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case POSITION:
			returnValues[0] = target.position.x;
			returnValues[1] = target.position.y;
			returnValues[2] = target.position.z;
			return 3;
		case ROTATION:
			returnValues[0] = target.up.x;
			returnValues[1] = target.up.y;
			returnValues[2] = target.up.z;
			return 3;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(PerspectiveCamera target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case POSITION:
			target.position.set(newValues[0], newValues[1], newValues[2]);
			break;
		case ROTATION:
			target.up.set(newValues[0], newValues[1], newValues[2]);
			break;
		default:
			assert false;
			break;
		}
	}
}
