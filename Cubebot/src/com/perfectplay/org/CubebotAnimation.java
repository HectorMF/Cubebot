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
		state.put("LeftUpperArm", "UF");
		state.put("LeftLowerArm", "UF");
		state.put("LeftHand", "UF");
		state.put("RightUpperArm", "UF");
		state.put("RightLowerArm", "UF");
		state.put("RightHand", "UF");
		state.put("Head", "UF");
		state.put("LeftUpperLeg", "UF");
		state.put("LeftLowerLeg", "UF");
		state.put("LeftFoot", "UF");
		state.put("RightUpperLeg", "UF");
		state.put("RightLowerLeg", "UF");
		state.put("RightFoot", "UF");
		
		/*
		 * Left Upper Arm
		 */
		AnimationSequence fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.LeftUpperArm), "Animations/LeftUpperArm/Fold1.txt"));
	    reverseAnimations.put("LeftUpperArm", fold.reverse());
		forwardAnimations.put("LeftUpperArm", fold);
		
		/*
		 * Left Lower Arm
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.LeftLowerArm), "Animations/LeftLowerArm/Fold1.txt"));
	    reverseAnimations.put("LeftLowerArm", fold.reverse());
		forwardAnimations.put("LeftLowerArm", fold);
		
		/*
		 * Left Hand
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.LeftHand), "Animations/LeftHand/Fold1.txt"));
	    reverseAnimations.put("LeftHand", fold.reverse());
		forwardAnimations.put("LeftHand", fold);
		
		/*
		 * Right Upper Arm
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.RightUpperArm), "Animations/RightUpperArm/Fold1.txt"));
	    reverseAnimations.put("RightUpperArm", fold.reverse());
		forwardAnimations.put("RightUpperArm", fold);
		
		/*
		 * Right Lower Arm
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.RightLowerArm), "Animations/RightLowerArm/Fold1.txt"));
	    reverseAnimations.put("RightLowerArm", fold.reverse());
		forwardAnimations.put("RightLowerArm", fold);
		
		/*
		 * Right Hand
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.RightHand), "Animations/RightHand/Fold1.txt"));
	    reverseAnimations.put("RightHand", fold.reverse());
		forwardAnimations.put("RightHand", fold);
		
		/*
		 * Left Upper Leg
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.LeftUpperLeg), "Animations/LeftUpperLeg/Fold1.txt"));
	
	    reverseAnimations.put("LeftUpperLeg", fold.reverse());
		forwardAnimations.put("LeftUpperLeg", fold);
		
		/*
		 * Left Lower Leg
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.LeftLowerLeg), "Animations/LeftLowerLeg/Fold1.txt"));
	
	    reverseAnimations.put("LeftLowerLeg", fold.reverse());
		forwardAnimations.put("LeftLowerLeg", fold);
		
		/*
		 * Left Foot
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.LeftFoot), "Animations/LeftFoot/Fold1.txt"));
	
	    reverseAnimations.put("LeftFoot", fold.reverse());
		forwardAnimations.put("LeftFoot", fold);
		
		/*
		 * Right Upper Leg
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.RightUpperLeg), "Animations/RightUpperLeg/Fold1.txt"));
	
	    reverseAnimations.put("RightUpperLeg", fold.reverse());
		forwardAnimations.put("RightUpperLeg", fold);
		
		/*
		 * Right Lower Leg
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.RightLowerLeg), "Animations/RightLowerLeg/Fold1.txt"));
	
	    reverseAnimations.put("RightLowerLeg", fold.reverse());
		forwardAnimations.put("RightLowerLeg", fold);
		
		/*
		 * Right Foot
		 */
		fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.RightFoot), "Animations/RightFoot/Fold1.txt"));
	
	    reverseAnimations.put("RightFoot", fold.reverse());
		forwardAnimations.put("RightFoot", fold);
		
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
		if(name == "LeftUpperLeg")
			return "LeftUpperLeg";
		if(name == "LeftLowerLeg")
			return "LeftLowerLeg";
		if(name == "LeftFoot")
			return "LeftFoot";
		if(name == "RightUpperLeg")
			return "RightUpperLeg";
		if(name == "RightLowerLeg")
			return "RightLowerLeg";
		if(name == "RightFoot")
			return "RightFoot";
		if(name == "RightUpperArm")
			return "RightUpperArm";
		if(name == "RightLowerArm")
			return "RightLowerArm";
		if(name == "RightHand")
			return "RightHand";
		if(name == "LeftUpperArm")
			return "LeftUpperArm";
		if(name == "LeftLowerArm")
			return "LeftLowerArm";
		if(name == "LeftHand")
			return "LeftHand";
		if(name == "Head")
			return "Head";
		return "";
		
	}
	
	public boolean isFolded(){
		if(state.get("LeftUpperArm").equals("F"))
			if(state.get("LeftLowerArm").equals("F"))
				if(state.get("LeftHand").equals("F"))
					if(state.get("RightUpperArm").equals("F"))
						if(state.get("RightLowerArm").equals("F"))
							if(state.get("RightHand").equals("F"))
								if(state.get("Head").equals("F"))
									if(state.get("LeftLowerLeg").equals("F"))
										if(state.get("LeftUpperLeg").equals("F"))
											if(state.get("LeftFoot").equals("F"))
												if(state.get("RightUpperLeg").equals("F"))
													if(state.get("RightLowerLeg").equals("F"))
														if(state.get("RightFoot").equals("F"))
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
