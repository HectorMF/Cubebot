package com.perfectplay.org;

import java.util.ArrayList;

public class AnimationSequence implements BaseAnimation{
	private ArrayList<Animation> animations;
	private int currentAnimation;
	private int size;
	private float delay;
	
	public AnimationSequence(){
		animations = new ArrayList<Animation>();
		currentAnimation = 0;
		size = 0;
		delay = 0;
	}
	
	public AnimationSequence push(Animation animation){
		if(delay > 0){
			animation.delay(delay);
			delay = 0;
		}
		animations.add(animation);
		size++;
		return this;
	}
	
	public void start(){
		if(size > 0){
			//stop current animation
			animations.get(currentAnimation).start();
			animations.get(0).start();
			currentAnimation = 0;
		}
	}
	
	public void update(float time){
		if(currentAnimation >= size)
			return;
		
		animations.get(currentAnimation).update(time);
		if(animations.get(currentAnimation).isFinished()){
			currentAnimation++;
			if(currentAnimation <  size)
				animations.get(currentAnimation).start();
			
		}
	}
	
	public AnimationSequence delay(float delay){
		this.delay = delay;
		return this;
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
