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
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;

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
		assets.load("Cubebot/Leg.g3dj", Model.class);
		assets.load("Cubebot/LowerArm.g3dj", Model.class);
		assets.load("Cubebot/Pelvis.g3dj", Model.class);
		assets.load("Cubebot/UpperArm.g3dj", Model.class);

		loading = true;
	}

	public void createBot() {
		ModelInstance instance;
		
		//body 
		/*
		instance = new ModelInstance(assets.get("Cubebot/Chest.g3dj", Model.class), 0, 12.85f, 0);
		instances.put("Chest", instance);
		instance = new ModelInstance(assets.get("Cubebot/Head.g3dj", Model.class), 0, 16.55f, 0);
		instances.put("Head", instance);
		instance = new ModelInstance(assets.get("Cubebot/Pelvis.g3dj", Model.class), 0, 9.654f, 0);
		instances.put("Pelvis", instance);
		
		*/
		instance = new ModelInstance(assets.get("Cubebot/Foot.g3dj", Model.class), 1, 1, 2);
		instance.transform.set(new Quaternion(-.5f,-.5f,-.5f,.5f));
		
		instances.put("LeftFoot", instance);
		
		instance = new ModelInstance(assets.get("Cubebot/Foot.g3dj", Model.class), 1, 1, -2);
		instance.transform.set(new Quaternion(-.5f,-.5f,-.5f,.5f));
		instance.calculateTransforms();
		
		System.out.println(instances.values().size());
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
