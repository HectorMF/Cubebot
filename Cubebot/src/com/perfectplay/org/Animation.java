package com.perfectplay.org;

import java.util.ArrayList;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Animation implements BaseAnimation {

	private Timeline forwardTimeline;
	private Timeline reverseTimeline;
	
	private boolean isReverse;
	private float delay;
	private Node node;
	private String path;

	public Animation(Node node, String filepath) {
		this.node = node;
		this.path = filepath;
		ArrayList<AnimationFrame> Frames = new ArrayList<AnimationFrame>();

		isReverse = false;
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
			Quaternion rotation = new Quaternion();

			rotation.x = Float.parseFloat(text.substring(0, text.indexOf(',')));
			text = text.substring(text.indexOf(',') + 1);
			rotation.y = Float.parseFloat(text.substring(0, text.indexOf(',')));

			text = text.substring(text.indexOf(',') + 1);
			if (text.indexOf('\n') != -1) {
				rotation.z = Float.parseFloat(text.substring(0,
						text.indexOf('\n')));
				text = text.substring(text.indexOf('\n') + 1);
			} else {
				rotation.z = Float.parseFloat(text);
				text = "";
			}

			AnimationFrame frame = new AnimationFrame(time, position, rotation);

			Frames.add(frame);
		}

		forwardTimeline = Timeline.createSequence();
		reverseTimeline = Timeline.createSequence();
		ArrayList<Timeline> tweens = new ArrayList<Timeline>();
		for (int i = 0; i < Frames.size(); i++) {
			float time = i - 1 < 0 ? Frames.get(i).time : Frames.get(i).time
					- Frames.get(i - 1).time;

			tweens.add(
					0,
					Timeline.createParallel()
							.push(Tween.to(node, NodeAccessor.POSITION, time)
									.targetRelative(-Frames.get(i).position.x,
											-Frames.get(i).position.y,
											-Frames.get(i).position.z).delay(0))
							.push(Tween.to(node, NodeAccessor.ROTATION, time)
									.targetRelative(-Frames.get(i).rotation.x,
											-Frames.get(i).rotation.y,
											-Frames.get(i).rotation.z)).delay(0));

			Timeline temp = Timeline
					.createParallel()
					.push(Tween.to(node, NodeAccessor.POSITION, time)
							.targetRelative(Frames.get(i).position.x,
									Frames.get(i).position.y,
									Frames.get(i).position.z).delay(0))
					.push(Tween.to(node, NodeAccessor.ROTATION, time)
							.targetRelative(Frames.get(i).rotation.x,
									Frames.get(i).rotation.y,
									Frames.get(i).rotation.z).delay(0));

			forwardTimeline.push(temp);
		}

		for (int i = 0; i < tweens.size(); i++) {
			reverseTimeline.push(tweens.get(i));
		}

		forwardTimeline.build();
		reverseTimeline.build();

	}

	public void start() {
		if (!isReverse)
			forwardTimeline.start();
		else
			reverseTimeline.start();
	}

	public Animation setReverse(boolean bool) {
		this.isReverse = bool;
		return this;
	}

	public boolean isFinished() {
		if (!isReverse)
			return forwardTimeline.isFinished();
		else
			return reverseTimeline.isFinished();
	}

	public Animation delay(float time) {
		forwardTimeline.delay(time);
		reverseTimeline.delay(time);
		return this;
	}

	public Animation repeat(int times, float delay) {
		forwardTimeline.repeat(times, delay);
		reverseTimeline.repeat(times, delay);
		return this;
	}

	public void pause() {
		if (!isReverse)
			forwardTimeline.pause();
		else
			reverseTimeline.pause();
	}

	public void resume() {
		if (!isReverse)
			forwardTimeline.resume();
		else
			reverseTimeline.resume();
	}

	public float getDelay() {
		return delay;
	}

	public void update(float delta) {
		forwardTimeline.update(delta);
		reverseTimeline.update(delta);
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
	
	public Animation cpy(){
		return new Animation(node, path).setReverse(isReverse);
	}

}