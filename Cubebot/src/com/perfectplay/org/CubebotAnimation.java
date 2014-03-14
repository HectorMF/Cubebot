package com.perfectplay.org;

import java.util.HashMap;

public class CubebotAnimation {
	private HashMap<String, AnimationSequence> forwardAnimations;
	private HashMap<String, AnimationSequence> reverseAnimations;
	
	public CubebotAnimation(Cubebot bot){
		forwardAnimations = new HashMap<String, AnimationSequence>();
		reverseAnimations = new HashMap<String, AnimationSequence>();
		addAnimations(bot);
	}
	
	public void addAnimations(Cubebot bot){
		
	}
}
