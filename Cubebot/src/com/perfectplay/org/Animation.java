package com.perfectplay.org;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Animation {
	private int totalTime;
	private boolean isAnimating;
	private int frameIndex;
	private Node node;
	ArrayList<AnimationFrame> Frames;
	
	public Animation(Node node, String filepath)
	{
		totalTime = 0;
		isAnimating = false;
		frameIndex = 0;
		this.node = node;
		
		//FileHandle file = Gdx.files.internal("C:/Users/Chase Plante/Desktop/CubebotTest.txt");
		FileHandle file = Gdx.files.internal(filepath);
		String text = file.readString();
		
		while(text.length() > 0)
		{
			int time = Integer.parseInt(text.substring(0, text.indexOf("::")));
			text = text.substring(text.indexOf("::") + 2);
			Vector3 position = new Vector3();
			position.x = Float.parseFloat(text.substring(0, text.indexOf(',')));
			text = text.substring(text.indexOf(',') + 1);
			position.y = Float.parseFloat(text.substring(0, text.indexOf(',')));
			text = text.substring(text.indexOf(',') + 1);
			position.z = Float.parseFloat(text.substring(0, text.indexOf("::")));
			text = text.substring(text.indexOf("::") + 2);
			Quaternion rotation = new Quaternion();
			rotation.w = Float.parseFloat(text.substring(0, text.indexOf(',')));
			text = text.substring(text.indexOf(',') + 1);
			rotation.x = Float.parseFloat(text.substring(0, text.indexOf(',')));
			text = text.substring(text.indexOf(',') + 1);
			rotation.y = Float.parseFloat(text.substring(0, text.indexOf(',')));
			text = text.substring(text.indexOf(',') + 1);
			if(text.indexOf('\n') != -1)
			{
				rotation.z = Float.parseFloat(text.substring(0, text.indexOf('\n')));
				text = text.substring(text.indexOf('\n') + 1);
			}
			else
			{
				rotation.z = Float.parseFloat(text);
				text = "";
			}
							
			AnimationFrame frame = new AnimationFrame(time, position, rotation);
			Frames.add(frame);
		}
	}

	public void start()
	{
		isAnimating = true;
	}
	
	public void stop()
	{
		isAnimating = false;
	}
	
	public void update()
	{
		if(!isAnimating) return;
		
		if(frameIndex < Frames.size() && Frames.get(frameIndex).time <= totalTime)
		{
			//node.move(Frames.get(frameIndex).position;
			//node.rotate(Frames.get(frameIndex).rotation;
			frameIndex ++;
		}
		else if(frameIndex >= Frames.size()) stop();
		
	}
	
	private class AnimationFrame{
		public int time;
		public Vector3 position;
		public Quaternion rotation;
		
		public AnimationFrame(int time, Vector3 pos, Quaternion rot)
		{
			this.time = time;
			position = pos;
			rotation = rot;
		}
	}
}
