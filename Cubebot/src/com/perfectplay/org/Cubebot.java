package com.perfectplay.org;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Cubebot {
	private ModelBatch modelBatch;
	private AssetManager assets;
	private HashMap<String, ModelInstance> instances;
	private Environment environment;
	private PerspectiveCamera cam;
	private CameraInputController camController;
	private boolean loading;

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
		assets.load("Cubebot/LowerArmAndLeg.g3dj", Model.class);
		assets.load("Cubebot/Pelvis.g3dj", Model.class);
		assets.load("Cubebot/UpperArm.g3dj", Model.class);

		loading = true;
	}

	public void createBot() {
		ModelInstance instance;
		Node node;
		//body 
		
		/*
		 * CHEST PIECE
		 */
		instance = new ModelInstance(assets.get("Cubebot/Chest.g3dj", Model.class), 0, 12.85f, 0);
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(0,0,1,0));
		node.translation.set(0,0,12.85f);
		node.scale.set(1,3,2);
		//update the transforms
		instance.calculateTransforms();
		instances.put("Chest", instance);
		
		
		/*
		 * HEAD PIECE
		 */
		instance = new ModelInstance(assets.get("Cubebot/Head.g3dj", Model.class), 0, 16.55f, 0);
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(1,0,0,1));
		node.translation.set(0,0, -.25f);
		node.scale.set(1,.333f,.5f);
		//update the transforms
		node.parent = instances.get("Chest").nodes.get(0);
		instance.calculateTransforms();
		instances.put("Head", instance);
		
		
		/*
		 * PELVIS PIECE
		 */
		instance = new ModelInstance(assets.get("Cubebot/Pelvis.g3dj", Model.class), 0, 9.654f, 0);
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(1,0,0,1));
		node.translation.set(0,0,0f);
		node.scale.set(1,1,.5f);
		//update the transforms
		node.parent = instances.get("Chest").nodes.get(0);
		instance.calculateTransforms();
		instances.put("Pelvis", instance);
		
		
		/*
		 * RIGHT UPPER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/UpperLeg.g3dj", Model.class), 0, 9.654f, 0);
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(1,0,0,1));
		node.translation.set(0,.5f,-3.2f);
		node.scale.set(1,.5f,2.04f);
		//update the transforms
		node.parent = instances.get("Pelvis").nodes.get(0);
		instance.calculateTransforms();
		instances.put("RightUpperLeg", instance);
		
		/*
		 * LEFT UPPER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/UpperLeg.g3dj", Model.class), 0, 9.654f, 0);
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(new Vector3(0,-1,1),180));
		node.translation.set(0,-.5f,-3.2f);
		node.scale.set(1,.5f,2.04f);
		//update the transforms
		node.parent = instances.get("Pelvis").nodes.get(0);
		instance.calculateTransforms();
		instances.put("LeftUpperLeg", instance);
		
		
		/*
		 * LEFT LOWER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/LowerArmAndLeg.g3dj", Model.class), 0, 9.654f, 0);
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(new Vector3(.707f,0f,.707f),180));
		node.translation.set(0,.33f,-1.81f);
		node.scale.set(.67f,.74f,1.02f);
		//update the transforms
		node.parent = instances.get("LeftUpperLeg").nodes.get(0);
		instance.calculateTransforms();
		instances.put("LeftLowerLeg", instance);
		
		/*
		 * Right LOWER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/LowerArmAndLeg.g3dj", Model.class), 0, 9.654f, 0);
		node  = instance.nodes.get(0);
		//set the rotation of the foot
		node.rotation.mul(new Quaternion(new Vector3(.707f,0f,.707f),180));
		node.translation.set(0,.33f,-1.81f);
		node.scale.set(.67f,.74f,1.02f);
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
		node.rotation.mul(new Quaternion(-.5f,-.5f,.5f, .5f));
		node.translation.set(0,-1.14f,1.02f);
		node.scale.set(1,.6f,1.96f);
		
		node.parent = instances.get("RightLowerLeg").nodes.get(0);
		//update the transforms
		instance.calculateTransforms();
		instances.put("RightFoot", instance);

<<<<<<< HEAD

		
=======
>>>>>>> 12607820501e44683f7fc027702c427468f6a17a
		/*
		instance = new ModelInstance(assets.get("Cubebot/Hand.g3dj", Model.class), 0, 0, 0);
		instances.put("Hand", instance);
		
		instance = new ModelInstance(assets.get("Cubebot/Leg.g3dj", Model.class), 0, 0, 0);
		instances.put("Leg", instance);
		instance = new ModelInstance(assets.get("Cubebot/LowerArm.g3dj", Model.class), 0, 0, 0);
		instances.put("LowerArm", instance);

		instance = new ModelInstance(assets.get("Cubebot/UpperArm.g3dj", Model.class), 0, 0, 0);
		instances.put("UpperArm", instance);
		
*/
		

		loading = false;
	}

	public void render() {
		if (loading && assets.update())
			createBot();
		camController.update();
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(.5f, .8f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		for (ModelInstance instance : instances.values()){
			//System.out.println(instance);

			modelBatch.render(instance, environment);
		}
		modelBatch.end();

	}

	public void dispose() {
		modelBatch.dispose();
		instances.clear();
		assets.dispose();
	}
}
