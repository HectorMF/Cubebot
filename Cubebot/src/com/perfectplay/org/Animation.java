package com.perfectplay.org;

import java.util.ArrayList;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Animation implements BaseAnimation {

	private ArrayList<Timeline> forwardTimeline;
	private ArrayList<Timeline> reverseTimeline;

	private boolean isReverse;
	private float delay;

	private Node node;
	private Quaternion rotation;

	private Quaternion nodeRotation;
	private Quaternion forwardRotation;
	private Quaternion reverseRotation;
	private ArrayList<AnimationFrame> Frames;
	private boolean hasStarted;
	private int currentStep = 0;
	private float totalTime;
	private int size;
	private boolean isFinished;
	private float delayTimer;

	public Animation(Node node, String filepath) {
		this.node = node;
		Frames = new ArrayList<AnimationFrame>();
		
		forwardRotation = new Quaternion();
		reverseRotation = new Quaternion();
		hasStarted = false;
		currentStep = 0;
		isReverse = false;
		isFinished = false;
		FileHandle file = Gdx.files.internal(filepath);
		String text = file.readString();

		while (text.length() > 0) {
			float time = Float
					.parseFloat(text.substring(0, text.indexOf("::")));
			text = text.substring(text.indexOf("::") + 2);
			Vector3 position = new Vector3();
			position.x = Float.parseFloat(text.substring(0, text.indexOf(',')));
			text = text.substring(text.indexOf(',') + 1);
			position.y = Float.parseFloat(text.substring(0, text.indexOf(',')));
			text = text.substring(text.indexOf(',') + 1);
			position.z = Float
					.parseFloat(text.substring(0, text.indexOf("::")));
			text = text.substring(text.indexOf("::") + 2);
			rotation = new Quaternion();
			rotation.x = Float.parseFloat(text.substring(0, text.indexOf(',')));
			text = text.substring(text.indexOf(',') + 1);
			rotation.y = Float.parseFloat(text.substring(0, text.indexOf(',')));
			text = text.substring(text.indexOf(',') + 1);
			rotation.z = Float.parseFloat(text.substring(0, text.indexOf(',')));

			text = text.substring(text.indexOf(',') + 1);
			if (text.indexOf('\n') != -1) {
				rotation.w = Float.parseFloat(text.substring(0,
						text.indexOf('\n')));
				text = text.substring(text.indexOf('\n') + 1);
			} else {
				rotation.w = Float.parseFloat(text);
				text = "";
			}

			AnimationFrame frame = new AnimationFrame(time, position, rotation);

			Frames.add(frame);
		}

		forwardTimeline = new ArrayList<Timeline>();
		reverseTimeline = new ArrayList<Timeline>();

		for (int i = 0; i < Frames.size(); i++) {
			float time = i - 1 < 0 ? Frames.get(i).time : Frames.get(i).time
					- Frames.get(i - 1).time;
			Frames.get(i).time = time;
			System.out.println(time);
			reverseTimeline
					.add(0,
							Timeline.createSequence().push(
									Tween.to(node, NodeAccessor.POSITION, time)
											.targetRelative(
													-Frames.get(i).position.x,
													-Frames.get(i).position.y,
													-Frames.get(i).position.z)
											.delay(0)));
			/*
			 * .push(Tween.to(node, NodeAccessor.ROTATION, time)
			 * .targetRelative(-Frames.get(i).rotation.x,
			 * -Frames.get(i).rotation.y, -Frames.get(i).rotation.z)).delay(0));
			 */
			forwardTimeline.add(Timeline.createSequence().push(
					Tween.to(node, NodeAccessor.POSITION, time)
							.targetRelative(Frames.get(i).position.x,
									Frames.get(i).position.y,
									Frames.get(i).position.z).delay(0)));

		}
		size = forwardTimeline.size();
	}

	public void start() {
		hasStarted = true;
		isFinished = false;
		nodeRotation = node.rotation.cpy();
		if (isReverse) {

			reverseTimeline.get(0).start();
			reverseRotation = node.rotation.cpy().mul(
					Frames.get(0).rotation.cpy().conjugate());
		} else {
			forwardTimeline.get(0).start();
			forwardRotation = node.rotation.cpy().mul(Frames.get(0).rotation.cpy());
		}
		totalTime = 0;
		delayTimer = 0;
		currentStep = 0;
		// reverseRotation = node.rotation.cpy().mul();
	}

	public Animation setReverse(boolean bool) {
		this.isReverse = bool;
		return this;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public Animation delay(float time) {
		forwardTimeline.get(0).delay(time);
		reverseTimeline.get(0).delay(time);
		delay = time;
		return this;
	}

	public void pause() {
		if (!isReverse)
			forwardTimeline.get(currentStep).pause();
		else
			reverseTimeline.get(currentStep).pause();
	}

	public void resume() {
		if (!isReverse)
			forwardTimeline.get(currentStep).resume();
		else
			reverseTimeline.get(currentStep).resume();
	}

	public float getDelay() {
		return delay;
	}
	public Quaternion validate(Quaternion q){
		if(q.w > .99)
			q.w = 1;
		if(q.w < .01)
			q.w = 0;
		if(q.x > .99)
			q.x = 1;
		if(q.x < .01)
			q.x = 0;
		if(q.y > .99)
			q.y = 1;
		if(q.y < .01)
			q.y = 0;
		if(q.z > .99)
			q.z = 1;
		if(q.z < .01)
			q.z = 0;
		
		Matrix4 temp = new Matrix4();
		q.toMatrix(temp.val);
		return temp.translate(0, 0, 0).getRotation(new Quaternion());
	}
	public void update(float delta) {
		if (!hasStarted || isFinished)
			return;

		delayTimer += delta;
		

		if (delayTimer > delay) {
			totalTime += delta;

			if (isReverse) {
				if (reverseTimeline.get(currentStep).isFinished()) {
					node.rotation.set(validate(reverseRotation.cpy()));
					node.calculateLocalTransform();
					currentStep++;
					if (currentStep < size) {
						reverseTimeline.get(currentStep).start();
						nodeRotation = node.rotation.cpy();
						reverseRotation = node.rotation.cpy().mul(
								Frames.get(currentStep).rotation.cpy().conjugate());
						totalTime = 0;
					} else {
						isFinished = true;
						return;
					}
				}
				reverseTimeline.get(currentStep).update(delta);
				node.rotation.set(validate(nodeRotation.cpy().slerp(
						reverseRotation.cpy(),
						totalTime / (float) Frames.get(currentStep).time)));
			} else {
				if (forwardTimeline.get(currentStep).isFinished()) {
					node.rotation.set(validate(forwardRotation.cpy()));
					node.calculateLocalTransform();
					currentStep++;
					if (currentStep < size) {
						forwardTimeline.get(currentStep).start();
						nodeRotation = node.rotation.cpy();
						forwardRotation = node.rotation.cpy().mul(
								Frames.get(currentStep).rotation.cpy());
						totalTime = 0;
					} else {
						isFinished = true;
						return;
					}
				}
				
				forwardTimeline.get(currentStep).update(delta);
				node.rotation.set(validate(nodeRotation.cpy().slerp(
						forwardRotation.cpy(),
						totalTime / (float) Frames.get(currentStep).time)));
			}
		} else {
			forwardTimeline.get(currentStep).update(delta);
			reverseTimeline.get(currentStep).update(delta);
		}
	}

	
	private class AnimationFrame {
		public float time;
		public Vector3 position;
		public Quaternion rotation;

		public AnimationFrame(float time, Vector3 pos, Quaternion rot) {
			this.time = time;
			this.position = pos;
			this.rotation = rot;
		}
	}

}
