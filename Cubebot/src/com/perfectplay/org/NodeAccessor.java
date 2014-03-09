package com.perfectplay.org;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class NodeAccessor implements TweenAccessor<Node> {

	public static final int POSITION_X = 0;
	public static final int POSITION_Y = 1;
	public static final int POSITION_Z = 2;
	public static final int POSITION = 3;
	public static final int ROTATION = 4;

	@Override
	public int getValues(Node target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case POSITION_X:
			returnValues[0] = target.translation.x;
			return 1;
		case POSITION_Y:
			returnValues[0] = target.translation.y;
			return 1;
		case POSITION_Z:
			returnValues[0] = target.translation.z;
			return 1;
		case POSITION:
			returnValues[0] = target.translation.x;
			returnValues[1] = target.translation.y;
			returnValues[2] = target.translation.z;
			return 3;
		case ROTATION:
			Vector3 axis = new Vector3();
			float angle = target.rotation.getAxisAngle(axis);
			returnValues[0] = angle;
			returnValues[1] = axis.x;
			returnValues[2] = axis.y;
			returnValues[3] = axis.z;
			return 4;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Node target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case POSITION_X:
			target.translation.x = newValues[0];
			break;
		case POSITION_Y:
			target.translation.y = newValues[0];
			break;
		case POSITION_Z:
			target.translation.z = newValues[0];
			break;
		case POSITION:
			target.translation.set(newValues[0], newValues[1], newValues[2]);
			break;
		case ROTATION:
			Vector3 axis = (new Vector3(newValues[1], newValues[2], newValues[3]));
			float angle = newValues[0];
			angle = angle%360;
			System.out.println(axis);
			target.rotation.set(new Quaternion(axis, angle));
			break;
		default:
			assert false;
			break;
		}
		target.calculateTransforms(true);
	}

}
