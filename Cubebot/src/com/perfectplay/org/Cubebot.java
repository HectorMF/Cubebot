package com.perfectplay.org;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Cubebot {
	public static final String Chest = "Chest";
	public static final String Pelvis = "Pelvis";
	public static final String Head = "Head";
	
	public static final String LeftUpperLeg = "LeftUpperLeg";
	public static final String LeftLowerLeg = "LeftLowerLeg";
	public static final String LeftFoot = "LeftFoot";
	
	public static final String LeftUpperArm = "LeftUpperArm";
	public static final String LeftLowerArm = "LeftLowerArm";
	public static final String LeftHand = "LeftHand";
	
	public static final String RightUpperLeg = "RightUpperLeg";
	public static final String RightLowerLeg = "RightLowerLeg";
	public static final String RightFoot = "RightFoot";
	
	public static final String RightUpperArm = "RightUpperArm";
	public static final String RightLowerArm = "RightLowerArm";
	public static final String RightHand = "RightHand";
	
	private ModelBatch modelBatch;
	private AssetManager assets;
	private HashMap<String, ModelInstance> instances;
	private Environment environment;
	private PerspectiveCamera cam;
	private CameraInputController camController;

	public Cubebot() {
		instances = new HashMap<String, ModelInstance>();
		// set up camera, environment, and input
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f,
				0.4f, 0.4f, .1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f,
				-0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(30f, 20f, 10f);
		cam.lookAt(0, 10, 0);
		cam.near = 0.1f;
		cam.far = 300f;
		cam.update();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		// load in the file and store it in the asset manager
		assets = new AssetManager();

		// load in the cubebot pieces
		assets.load("Cubebot/Chest.g3dj", Model.class);
		assets.load("Cubebot/Foot.g3dj", Model.class);
		assets.load("Cubebot/Hand.g3dj", Model.class);
		assets.load("Cubebot/Head.g3dj", Model.class);
		assets.load("Cubebot/UpperLeg.g3dj", Model.class);
		assets.load("Cubebot/LowerArm.g3dj", Model.class);
		
		
		assets.load("Cubebot/LowerArmAndLeg.g3dj", Model.class);
		assets.load("Cubebot/Pelvis.g3dj", Model.class);
		assets.load("Cubebot/UpperArm.g3dj", Model.class);

		while(!assets.update());
		createBot();
	}

	private void createBot() {
		ModelInstance instance;
		Node node;

		/*
		 * CHEST PIECE
		 */
		instance = new ModelInstance(assets.get("Cubebot/Chest.g3dj", Model.class));
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		//node.rotation.mul(new Quaternion(0,0,0,1));
		node.translation.set(0,0,12.85f);
		node.scale.set(1,1,1);
		instance.calculateTransforms();
		instances.put("Chest", instance);
		
		/*
		 * HEAD PIECE
		 */
		instance = new ModelInstance(assets.get("Cubebot/Head.g3dj", Model.class));
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(1,0,0,1));
		node.translation.set(0,0, 3.57f);
		node.scale.set(1,1,1);
		//update the transforms
		node.parent = instances.get("Chest").nodes.get(0);
		instance.calculateTransforms();
		instances.put("Head", instance);
		
		/*
		 * PELVIS PIECE
		 */
		instance = new ModelInstance(assets.get("Cubebot/Pelvis.g3dj", Model.class));
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(1,0,0,1));
		node.translation.set(0,0,-3.2f);
		node.scale.set(1,1,1);
		//update the transforms
		node.parent = instances.get("Chest").nodes.get(0);
		instance.calculateTransforms();
		instances.put("Pelvis", instance);
		
		/*
		 * RIGHT UPPER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/UpperLeg.g3dj", Model.class));
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(1,0,0,1));
		node.translation.set(0,1.5f,-3.2f);
		node.scale.set(1,1,1);
		//update the transforms
		node.parent = instances.get("Pelvis").nodes.get(0);
		instance.calculateTransforms();
		instances.put("RightUpperLeg", instance);
		
		/*
		 * LEFT UPPER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/UpperLeg.g3dj", Model.class));
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(new Vector3(0,-1,1),180));
		node.translation.set(0,-1.5f,-3.2f);
		node.scale.set(1,1,1);
		//update the transforms
		node.parent = instances.get("Pelvis").nodes.get(0);
		instance.calculateTransforms();
		instances.put("LeftUpperLeg", instance);
		
		/*
		 * LEFT LOWER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/LowerArmAndLeg.g3dj", Model.class));
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(new Vector3(.707f,0f,.707f),180));
		node.translation.set(0,.48f,-3.7f);
		node.scale.set(1,1,1);
		//update the transforms
		node.parent = instances.get("LeftUpperLeg").nodes.get(0);
		instance.calculateTransforms();
		instances.put("LeftLowerLeg", instance);
		
		/*
		 * Right LOWER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/LowerArmAndLeg.g3dj", Model.class));
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(new Vector3(.707f,0f,.707f),180));
		node.translation.set(0,.48f,-3.7f);
		node.scale.set(1,1,1);
		//update the transforms
		node.parent = instances.get("RightUpperLeg").nodes.get(0);
		instance.calculateTransforms();
		instances.put("RightLowerLeg", instance);
		
		/*
		 * RIGHT FOOT
		 */
		instance = new ModelInstance(assets.get("Cubebot/Foot.g3dj", Model.class));
		//get the actual foot from the entire model
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(0,0,-.707f,.707f));
		node.translation.set(0,-1.15f,-1.02f);
		node.scale.set(1,1,1);
	
		node.parent = instances.get("RightLowerLeg").nodes.get(0);
		//update the transforms
		instance.calculateTransforms();
		instances.put("RightFoot", instance);
		
		/*
		 * LEFT FOOT
		 */
		instance = new ModelInstance(assets.get("Cubebot/Foot.g3dj", Model.class));
		//get the actual foot from the entire model
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(0f,-.707f,0,.707f));
		node.translation.set(0,-1.15f,-1.02f);
		node.scale.set(1,1,1);
		
		node.parent = instances.get("LeftLowerLeg").nodes.get(0);
		//update the transforms
		instance.calculateTransforms();
		instances.put("LeftFoot", instance);

		/*
		 * LEFT UPPER ARM
		 */
		instance = new ModelInstance(assets.get("Cubebot/UpperArm.g3dj", Model.class));
		//get the actual foot from the entire model
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		//node.rotation.mul(new Quaternion(1,0, 0,0f));
		node.translation.set(0,-5.25f,1.15f);
		node.scale.set(1,1,1);
		
		node.parent = instances.get("Chest").nodes.get(0);
		//update the transforms
		instance.calculateTransforms();
		instances.put("LeftUpperArm", instance);
		
		/*
		 * LEFT LOWER ARM
		 */
		instance = new ModelInstance(assets.get("Cubebot/LowerArm.g3dj", Model.class));
		//get the actual foot from the entire model
		node  = instance.nodes.get(0);
		//set the rotation
		
		node.translation.set(0,-.02f,6.78f);
		//node.rotation.set(new Quaternion(0,0,0,1));
		node.scale.set(1,1,1);
		
		node.parent = instances.get("LeftUpperArm").nodes.get(0);
		//update the transforms
		instance.calculateTransforms();
		instances.put("LeftLowerArm", instance);
		
		/*
		 * LEFT Hand
		 */
		instance = new ModelInstance(assets.get("Cubebot/Hand.g3dj", Model.class));
		//get the actual foot from the entire model
		node  = instance.nodes.get(0);
		//set the rotation
		node.rotation.mul(new Quaternion(new Vector3(0,0,0),0));
		node.translation.set(0.01f,-1.556f,-0.0f);
		node.scale.set(1,1,1);
		
		node.parent = instances.get("LeftLowerArm").nodes.get(0);
		//update the transforms
		instance.calculateTransforms();
		instances.put("LeftHand", instance);
		
		/*
		 * RIGHT UPPER ARM
		 */
		instance = new ModelInstance(assets.get("Cubebot/UpperArm.g3dj", Model.class));
		//get the actual foot from the entire model
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(1,0, 0,0f));
		node.translation.set(0,1.75f,.57f);
		node.scale.set(1,1,1);
	
		node.parent = instances.get("Chest").nodes.get(0);
		//update the transforms
		instance.calculateTransforms();
		instances.put("RightUpperArm", instance);
		
		/*
		 * RIGHT LOWER ARM
		 */
		instance = new ModelInstance(assets.get("Cubebot/LowerArmAndLeg.g3dj", Model.class));
		//get the actual foot from the entire model
		node  = instance.nodes.get(0);
		//set the rotation
		
		node.translation.set(0,-.02f,-1.78f);
		node.rotation.mul(new Quaternion(0f,-.707f,0,.707f));
		node.scale.set(1,1,1);
		
		node.parent = instances.get("RightUpperArm").nodes.get(0);
		//update the transforms
		instance.calculateTransforms();
		instances.put("RightLowerArm", instance);
		
		/*
		 * RIGHT HAND
		 */
		instance = new ModelInstance(assets.get("Cubebot/Hand.g3dj", Model.class));
		//get the actual foot from the entire model
		node  = instance.nodes.get(0);
		//set the rotation
		node.rotation.mul(new Quaternion(new Vector3(-1,0,0),180));
		node.translation.set(0.01f,1.556f,-0.0f);
		node.scale.set(1,1,1);
		
		node.parent = instances.get("RightLowerArm").nodes.get(0);
		//update the transforms
		instance.calculateTransforms();
		instances.put("RightHand", instance);
		
		
		getNode(Cubebot.Chest).children.add(getNode(Cubebot.Head));
		getNode(Cubebot.Chest).children.add(getNode(Cubebot.Pelvis));
		
		getNode(Cubebot.RightLowerLeg).children.add(getNode(Cubebot.RightFoot));
		getNode(Cubebot.RightUpperLeg).children.add(getNode(Cubebot.RightLowerLeg));
		getNode(Cubebot.Pelvis).children.add(getNode(Cubebot.RightUpperLeg));
		
		getNode(Cubebot.LeftLowerLeg).children.add(getNode(Cubebot.LeftFoot));
		getNode(Cubebot.LeftUpperLeg).children.add(getNode(Cubebot.LeftLowerLeg));
		getNode(Cubebot.Pelvis).children.add(getNode(Cubebot.LeftUpperLeg));
		
	    getNode(Cubebot.LeftLowerArm).children.add(getNode(Cubebot.LeftHand));
		getNode(Cubebot.LeftUpperArm).children.add(getNode(Cubebot.LeftLowerArm));
		getNode(Cubebot.Chest).children.add(getNode(Cubebot.LeftUpperArm));
		
		getNode(Cubebot.RightLowerArm).children.add(getNode(Cubebot.RightHand));
		getNode(Cubebot.RightUpperArm).children.add(getNode(Cubebot.RightLowerArm));
		getNode(Cubebot.Chest).children.add(getNode(Cubebot.RightUpperArm));
	}

	public void render() {
		camController.update();
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(.5f, .8f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		modelBatch.begin(cam);
		
		modelBatch.render(instances.get("Chest"), environment);
		
		modelBatch.end();
	
	}

	public Node getNode(String id){
		if(instances.containsKey(id))
			return instances.get(id).nodes.get(0);
		return null;
	}
	
	public void dispose() {
		modelBatch.dispose();
		instances.clear();
		assets.dispose();
	}
}
