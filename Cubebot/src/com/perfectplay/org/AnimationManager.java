package com.perfectplay.org;

import java.util.HashMap;

public class AnimationManager {
	private HashMap<String, BaseAnimation> Animations;

	public AnimationManager() {
		Animations = new HashMap<String, BaseAnimation>();
	}

	public void addAnimation(String name, BaseAnimation animation) {
		Animations.put(name, animation);
	}

	public void removeAnimation(String name) {
		Animations.remove(name);
	}

	public void startAnimation(String name) {
		Animations.get(name).start();
	}

	public void pauseAnimation(String name) {
		Animations.get(name).pause();
	}
	
	public void resumeAnimation(String name) {
		Animations.get(name).resume();
	}

	public void update(float delta) {
		for (BaseAnimation animation : Animations.values()) {
			animation.update(delta);
		}
	}
}
