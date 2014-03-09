package com.perfectplay.org;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g3d.model.Node;

public class AnimationManager {
	private ArrayList<AnimationField> Animations;
	
	public AnimationManager()
	{
		Animations = new ArrayList<AnimationField>();
	}
	
	public void AddAnimation(String name, Animation animation)
	{
		Animations.add(new AnimationField(name,animation));
	}
	
	public void AddAnimation(String name, Node node, String filepath)
	{
		Animations.add(new AnimationField(name, new Animation(node, filepath)));
	}
	
	public void RemoveAnimation(String name)
	{
		int i;
		
		for(i=0; i<Animations.size(); i++)
			if(Animations.get(i).name.toLowerCase().equals(name.toLowerCase()))
				{
					Animations.remove(i);
					i--;
				}
	}
	
	public void update(float delta)
	{
		int i;
		
		for(i=0; i<Animations.size(); i++)
			Animations.get(i).animation.update(delta);
	}
	
	private class AnimationField{
		public String name;
		public Animation animation;
		
		public AnimationField(String name, Animation animation)
		{
			this.name = name;
			this.animation = animation;
		}
	}
}
