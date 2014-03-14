package com.perfectplay.org;

import java.util.ArrayList;

public class AnimationSequence implements BaseAnimation{
	private ArrayList<Animation> animations;
	private int currentAnimation;
	private int size;
	private float delay;
	private Boolean isFinished;
	
	public AnimationSequence(){
		animations = new ArrayList<Animation>();
		currentAnimation = 0;
		size = 0;
		delay = 0;
		isFinished = true;
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
	
	public Boolean isFinished(){
		return isFinished;
	}
	
	public AnimationSequence reverse(){
		AnimationSequence reverse = new AnimationSequence();
		for(int i = animations.size() - 1; i >= 0; i--)
			reverse.push(animations.get(i).cpy().setReverse(true));
		return reverse;
	}
	
	public void start(){
		if(size > 0){
			for(int i = 0; i < size; i ++){
				animations.set(i,animations.get(i).cpy());
				animations.get(i).start();
			}
			animations.get(0).resume();
			currentAnimation = 0;
			isFinished = false;
		}
	}
	
	public void update(float time){
		if(currentAnimation >= size) {
			isFinished = true;
			return;
		}
		
		animations.get(currentAnimation).update(time);
		if(animations.get(currentAnimation).isFinished()){
			currentAnimation++;
			if(currentAnimation <  size){
				animations.get(currentAnimation).start();
			}
			
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
