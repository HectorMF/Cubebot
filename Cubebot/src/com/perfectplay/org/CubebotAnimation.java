package com.perfectplay.org;

import java.util.ArrayList;
import java.util.HashMap;

public class CubebotAnimation {
	private HashMap<String, AnimationSequence> forwardAnimations;
	private HashMap<String, AnimationSequence> reverseAnimations;
	private HashMap<String, String> state;
	private ArrayList<String> unfinished;
	
	private AnimationManager manager;
	public CubebotAnimation(Cubebot bot){
		this.manager = new AnimationManager();
		unfinished= new ArrayList<String>();
		forwardAnimations = new HashMap<String, AnimationSequence>();
		reverseAnimations = new HashMap<String, AnimationSequence>();
		state = new HashMap<String, String>();
		addAnimations(bot);
	}
	
	private void addAnimations(Cubebot bot){
		state.put("LeftArm", "UF");
		state.put("RightArm", "UF");
		state.put("Head", "UF");
		state.put("LeftLeg", "UF");
		state.put("RightLeg", "UF");
		
		/*
		 * LEFT ARM
		 */
		AnimationSequence fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.LeftHand), "Animations/LeftHand/Fold1.txt"))
		.push(new Animation(bot.getNode(Cubebot.LeftLowerArm), "Animations/LeftLowerArm/Fold1.txt"))
		.push(new Animation(bot.getNode(Cubebot.LeftUpperArm), "Animations/LeftUpperArm/Fold1.txt"));
	    reverseAnimations.put("LeftArm", fold.reverse());
		forwardAnimations.put("LeftArm", fold);
		
		/*
		 * RightArm
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.RightHand), "Animations/RightHand/Fold1.txt"))
		.push(new Animation(bot.getNode(Cubebot.RightLowerArm), "Animations/RightLowerArm/Fold1.txt"))
		.push(new Animation(bot.getNode(Cubebot.RightUpperArm), "Animations/RightUpperArm/Fold1.txt"));
	    reverseAnimations.put("RightArm", fold.reverse());
		forwardAnimations.put("RightArm", fold);
		
		/*
		 * Left Leg
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.LeftFoot), "Animations/LeftFoot/Fold1.txt"))
		.push(new Animation(bot.getNode(Cubebot.LeftLowerLeg), "Animations/LeftLowerLeg/Fold1.txt"))
		.push(new Animation(bot.getNode(Cubebot.LeftUpperLeg), "Animations/LeftUpperLeg/Fold1.txt"));
	
	    reverseAnimations.put("LeftLeg", fold.reverse());
		forwardAnimations.put("LeftLeg", fold);
		
		/*
		 * Right Leg
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.RightFoot), "Animations/RightFoot/Fold1.txt"))
		.push(new Animation(bot.getNode(Cubebot.RightLowerLeg), "Animations/RightLowerLeg/Fold1.txt"))
		.push(new Animation(bot.getNode(Cubebot.RightUpperLeg), "Animations/RightUpperLeg/Fold1.txt"));
	
	    reverseAnimations.put("RightLeg", fold.reverse());
		forwardAnimations.put("RightLeg", fold);
		
		/*
		 * Head
		 */
		fold = new AnimationSequence();
		fold		.push(new Animation(bot.getNode(Cubebot.Head), "Animations/Head/Fold1.txt"))
		.push(new Animation(bot.getNode(Cubebot.Head), "Animations/Head/Fold2.txt"));
	
	    reverseAnimations.put("Head", fold.reverse());
		forwardAnimations.put("Head", fold);
	}
	
	public void fold(String name){
		if(name == "") return;
		if(state.get(name).equals("UF")){
			forwardAnimations.get(name).start();
			manager.addAnimation(name, forwardAnimations.get(name));
			manager.startAnimation(name);
			state.put(name, "NF");
			unfinished.add(name);
		}
	}
	
	public void unfold(String name){
		if(name == "") return;
		if(state.get(name).equals("F")){
			reverseAnimations.get(name).start();
			manager.addAnimation(name + "r", reverseAnimations.get(name));
			manager.startAnimation(name + "r");
			state.put(name, "NUF");
			unfinished.add(name);
		}
	}
	
	public String getType(String name){
		if(name == "LeftUpperLeg" || name == "LeftLowerLeg" || name == "LeftFoot")
			return "LeftLeg";
		if(name == "RightUpperLeg" || name == "RightLowerLeg" || name == "RightFoot")
			return "RightLeg";
		if(name == "RightUpperArm" || name == "RightLowerArm" || name == "RightHand")
			return "RightArm";
		if(name == "LeftUpperArm" || name == "LeftLowerArm" || name == "LeftHand")
			return "LeftArm";
		if(name == "Head")
			return "Head";
		return "";
		
	}
	
	public boolean isFolded(){
		if(state.get("LeftArm").equals("F"))
			if(state.get("RightArm").equals("F"))
				if(state.get("Head").equals("F"))
					if(state.get("LeftLeg").equals("F"))
						if(state.get("RightLeg").equals("F"))
							return true;
		return false;							
	}
	
	public String getState(String name){
		return state.get(name);
	}
	
	public boolean isTransitioning(String name){
		return state.get(name) == "NF" || state.get(name) == "NUF";
	}
	
	public void update(float delta){
		for(int i = unfinished.size() - 1; i >= 0 ; i--){
			String name = unfinished.get(i);
			if(forwardAnimations.get(name).isFinished() && reverseAnimations.get(name).isFinished()){
				unfinished.remove(i);
				String s = state.get(name);
				s = s.substring(1);
				state.put(name, s);
			}
		}
		manager.update(delta*2);
	}
}
