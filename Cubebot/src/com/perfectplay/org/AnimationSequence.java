package com.perfectplay.org;

import java.util.ArrayList;

public class AnimationSequence implements BaseAnimation{
	private ArrayList<Animation> animations;
	private int currentAnimation;
	private int size;
	public AnimationSequence(){
		animations = new ArrayList<Animation>();
		currentAnimation = 0;
		size = 0;
	}
	
	public AnimationSequence push(Animation animation){
		animations.add(animation);
		size++;
		return this;
	}
	
	public void start(){
		if(size > 0){
			//stop current animation
			animations.get(currentAnimation).pause();
			animations.get(0).start();
			currentAnimation = 0;
		}
		
	}
	
	public void update(float time){
		if(currentAnimation >= size){
			return;
		}
		animations.get(currentAnimation).update(time);
		if(animations.get(currentAnimation).isFinished()){
			currentAnimation++;
		}
	}

	@Override
	public void pause() {
		if(size > 0){
			animations.get(currentAnimation).pause();
		}
	}

	@Override
	public void resume() {
		if(size > 0){
			animations.get(currentAnimation).resume();
		}
	}
	
}
