package com.perfectplay.org;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g3d.model.Node;

public class AnimationManager {
	private HashMap<String, Animation> Animations;
	
	public AnimationManager()
	{
		Animations = new HashMap<String,Animation>();
	}
	
	public void AddAnimation(String name, Animation animation)
	{
		Animations.put(name, animation);
	}
	
	public void AddAnimation(String name, Node node, String filepath)
	{
		Animations.put(name, new Animation(node, filepath));
	}
	
	public void RemoveAnimation(String name)
	{
		Animations.remove(name);
	}
	
	public void ReverseAnimation(String name)
	{
		Animations.get(name).isReverse = true;
	}
	
	public void StartAnimation(String name)
	{
		Animations.get(name).start();
	}
	
	public void StopAnimation(String name)
	{
		Animations.get(name).stop();
	}
	
	public void RestartAnimation(String name)
	{
		Animations.get(name).restart();
	}
	
	public void ReverseAnimationTimeFlow(String name)
	{
		Animations.get(name).reverseTimeFlow();
	}
	
	public void update(float delta)
	{
		for(Animation animation : Animations.values()){
			animation.update(delta);
		}
	}
}
