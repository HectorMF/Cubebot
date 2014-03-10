package com.perfectplay.org;

import java.util.HashMap;

public class AnimationManager {
	private HashMap<String, Animation> Animations;

	public AnimationManager() {
		Animations = new HashMap<String, Animation>();
	}

	public void addAnimation(String name, Animation animation) {
		Animations.put(name, animation);
	}

	public void removeAnimation(String name) {
		Animations.remove(name);
	}

	public void startAnimation(String name, Boolean reverse) {
		Animations.get(name).setReverse(reverse);
		Animations.get(name).start();
	}

	public void pauseAnimation(String name) {
		Animations.get(name).pause();
	}
	
	public void resumeAnimation(String name) {
		Animations.get(name).resume();
	}

	public void restartAnimation(String name) {
		Animations.get(name).restart();
	}


	public void update(float delta) {
		for (Animation animation : Animations.values()) {
			animation.update(delta);
		}
	}
}
