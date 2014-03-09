package com.perfectplay.org;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
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
			Vector3 euler = convertToEuler(target.rotation);
			returnValues[0] = euler.x;
			returnValues[1] = euler.y;
			returnValues[2] = euler.z;
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
			System.out.println(newValues[0] + " : " + newValues[1] + " : " + newValues[2]);
			target.rotation.set(convertFromEuler(new Vector3(newValues[0], newValues[1],
					newValues[2])));
			break;
		default:
			assert false;
			break;
		}
		target.calculateTransforms(true);
	}

	public Quaternion convertFromEuler(Vector3 euler) {
		float heading = euler.x;
		float attitude = euler.y;
		float bank = euler.z;
		// Assuming the angles are in radians.
		double c1 = Math.cos(heading / 2);
		double s1 = Math.sin(heading / 2);
		double c2 = Math.cos(attitude / 2);
		double s2 = Math.sin(attitude / 2);
		double c3 = Math.cos(bank / 2);
		double s3 = Math.sin(bank / 2);
		double c1c2 = c1 * c2;
		double s1s2 = s1 * s2;
		double w = c1c2 * c3 - s1s2 * s3;
		double x = c1c2 * s3 + s1s2 * c3;
		double y = s1 * c2 * c3 + c1 * s2 * s3;
		double z = c1 * s2 * c3 - s1 * c2 * s3;
		return new Quaternion((float)x, (float)y, (float)z, (float)w);
		
	}

	public Vector3 convertToEuler(Quaternion q1) {

		double heading;
		double attitude;
		double bank;

		double sqw = q1.w * q1.w;
		double sqx = q1.x * q1.x;
		double sqy = q1.y * q1.y;
		double sqz = q1.z * q1.z;
		double unit = sqx + sqy + sqz + sqw; // if normalised is one, otherwise
												// is correction factor
		double test = q1.x * q1.y + q1.z * q1.w;
		if (test > 0.499 * unit) { // singularity at north pole
			heading = 2 * Math.atan2(q1.x, q1.w);
			attitude = Math.PI / 2;
			bank = 0;
			return new Vector3((float) heading, (float) attitude, (float) bank);
		}
		if (test < -0.499 * unit) { // singularity at south pole
			heading = -2 * Math.atan2(q1.x, q1.w);
			attitude = -Math.PI / 2;
			bank = 0;
			return new Vector3((float) heading, (float) attitude, (float) bank);
		}
		heading = Math.atan2(2 * q1.y * q1.w - 2 * q1.x * q1.z, sqx - sqy - sqz
				+ sqw);
		attitude = Math.asin(2 * test / unit);
		bank = Math.atan2(2 * q1.x * q1.w - 2 * q1.y * q1.z, -sqx + sqy - sqz
				+ sqw);

		return new Vector3((float) heading, (float) attitude, (float) bank);
	}
}
