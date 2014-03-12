package com.perfectplay.org;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.perfectplay.org.bullet.BulletConstructor;
import com.perfectplay.org.bullet.BulletEntity;
import com.perfectplay.org.bullet.BulletWorld;

@SuppressWarnings("deprecation")
public class Cubebot implements InputProcessor {

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
	
	/*
	 * Camera
	 */
	public CameraHandler camHandler;
	private PerspectiveCamera cam;
	
	/*
	 * Rendering
	 */
	private ModelBatch modelBatch;
	private AssetManager assets;
	private ModelBatch shadowBatch;
	
	/*
	 * Environment
	 */
	private HashMap<String, ModelInstance> instances;
	private ModelInstance pedestal;
	private SkyBox skyBox;
	private Environment environment;
	private DirectionalShadowLight shadowLight;
	private Attribute defaultMaterial;
	private Attribute selectedMaterial;
	
	/*
	 * Bullet Physics
	 */
	private BulletWorld world;
	private HashMap<String, BulletEntity> collisionBoxes;
	private ClosestRayResultCallback rayTestCB;
	private Vector3 rayFrom = new Vector3();
	private Vector3 rayTo = new Vector3();
	
	private String selectedNode;
	
	public Cubebot() {
		instances = new HashMap<String, ModelInstance>();

		// set up camera, environment, and input
		modelBatch = new ModelBatch();
		shadowBatch = new ModelBatch(new DepthShaderProvider());
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f,
				0.4f, 0.4f, .1f));
		environment.add((shadowLight = new DirectionalShadowLight(1024, 1024, 30f, 30f, 1f, 100f)).set(0.8f, 0.8f, 0.8f, .4f, -.8f, 2f));
			environment.shadowMap = shadowLight;

		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		camHandler = new CameraHandler(cam);
		
		/*
		 * Physics simulation stuff
		 */

		Bullet.init();		
		world = new BulletWorld();
		collisionBoxes = new HashMap<String, BulletEntity>();
		rayTestCB = new ClosestRayResultCallback(Vector3.Zero, Vector3.Z);
		
		// load in the files and store it in the asset manager
		assets = new AssetManager();

		// load in the cubebot pieces
		/*
		 * Body
		 */
		assets.load("Cubebot/Chest.g3dj", Model.class);
		assets.load("Cubebot/Head.g3dj", Model.class);
		assets.load("Cubebot/Pelvis.g3dj", Model.class);
		/*
		 * Legs
		 */
		assets.load("Cubebot/RightUpperLeg.g3dj", Model.class);
		assets.load("Cubebot/LeftUpperLeg.g3dj", Model.class);
		assets.load("Cubebot/LowerLeg.g3dj", Model.class);
		assets.load("Cubebot/Foot.g3dj", Model.class);

		/*
		 * Arms
		 */
		assets.load("Cubebot/LowerArm.g3dj", Model.class);
		assets.load("Cubebot/UpperArm.g3dj", Model.class);
		assets.load("Cubebot/LeftHand.g3dj", Model.class);
		assets.load("Cubebot/RightHand.g3dj", Model.class);

		/*
		 * Environment
		 */
		assets.load("Cubebot/Cylinder.g3dj", Model.class);
		
		/*
		 * Skybox
		 */
		String faces[] = { "Sky/skyX55+x.png",
				"Sky/skyX55-x.png", "Sky/skyX55+y.png",
				"Sky/skyX55-y.png", "Sky/skyX55+z.png",
				"Sky/skyX55-z.png" };
		
		for (int i = 0; i < 6; i++) {
			assets.load(faces[i], Pixmap.class);
		}
		

		while (!assets.update());
		skyBox = new SkyBox(assets,faces);
		
		/*
		 * Build the cubebot
		 */
		createBot();
	}

	private void createBot() {
		ModelInstance instance;
		Node node;
		HashMap<String, BoundingBox> boundingBoxes = new HashMap<String, BoundingBox>();

		/*
		 * CHEST PIECE
		 */
		instance = new ModelInstance(assets.get("Cubebot/Chest.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.rotation.set(new Vector3(1, 0, 0), -90);
		node.rotation.mul(new Quaternion(new Vector3(0,0,1), 90));
		node.translation.set(0, -.2f, 0);
		node.scale.set(1, 1, 1);
		instance.calculateTransforms();
		instances.put("Chest", instance);
		instance = new ModelInstance(assets.get("Cubebot/Chest.g3dj",
				Model.class));

		boundingBoxes
				.put("Chest", node.calculateBoundingBox(new BoundingBox()));
		/*
		 * HEAD PIECE
		 */
		instance = new ModelInstance(assets.get("Cubebot/Head.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0, 0, 3.57f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("Chest").nodes.get(0);
		instance.calculateTransforms();
		instances.put("Head", instance);
		boundingBoxes.put("Head", node.calculateBoundingBox(new BoundingBox()));

		/*
		 * PELVIS PIECE
		 */
		instance = new ModelInstance(assets.get("Cubebot/Pelvis.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0, 0, -3.2f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("Chest").nodes.get(0);
		instance.calculateTransforms();
		instances.put("Pelvis", instance);
		boundingBoxes.put("Pelvis",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * RIGHT UPPER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/RightUpperLeg.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0, 1.5f, -3.2f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("Pelvis").nodes.get(0);
		instance.calculateTransforms();
		instances.put("RightUpperLeg", instance);
		boundingBoxes.put("RightUpperLeg",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * LEFT UPPER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/LeftUpperLeg.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0, -1.5f, -3.2f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("Pelvis").nodes.get(0);
		instance.calculateTransforms();
		instances.put("LeftUpperLeg", instance);
		boundingBoxes.put("LeftUpperLeg",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * LEFT LOWER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/LowerLeg.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0, -.475f, -3.7f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("LeftUpperLeg").nodes.get(0);
		instance.calculateTransforms();
		instances.put("LeftLowerLeg", instance);
		boundingBoxes.put("LeftLowerLeg",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * Right LOWER LEG
		 */
		instance = new ModelInstance(assets.get("Cubebot/LowerLeg.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0, .48f, -3.7f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("RightUpperLeg").nodes.get(0);
		instance.calculateTransforms();
		instances.put("RightLowerLeg", instance);
		boundingBoxes.put("RightLowerLeg",
				node.calculateBoundingBox(new BoundingBox()));

		/*
		 * RIGHT FOOT
		 */
		instance = new ModelInstance(assets.get("Cubebot/Foot.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(.95f, 0f, -1.75f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("RightLowerLeg").nodes.get(0);
		instance.calculateTransforms();
		instances.put("RightFoot", instance);
		boundingBoxes.put("RightFoot",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * LEFT FOOT
		 */
		instance = new ModelInstance(assets.get("Cubebot/Foot.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(.95f, 0f, -1.75f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("LeftLowerLeg").nodes.get(0);
		instance.calculateTransforms();
		instances.put("LeftFoot", instance);
		boundingBoxes.put("LeftFoot",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * LEFT UPPER ARM
		 */
		instance = new ModelInstance(assets.get("Cubebot/UpperArm.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0, -5.25f, 1.15f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("Chest").nodes.get(0);
		instance.calculateTransforms();
		instances.put("LeftUpperArm", instance);
		boundingBoxes.put("LeftUpperArm",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * LEFT LOWER ARM
		 */
		instance = new ModelInstance(assets.get("Cubebot/LowerArm.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0, -3.75f, 0f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("LeftUpperArm").nodes.get(0);
		instance.calculateTransforms();
		instances.put("LeftLowerArm", instance);
		boundingBoxes.put("LeftLowerArm",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * LEFT Hand
		 */
		instance = new ModelInstance(assets.get("Cubebot/LeftHand.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0.01f, -2.3f, -0.0f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("LeftLowerArm").nodes.get(0);
		instance.calculateTransforms();
		instances.put("LeftHand", instance);
		boundingBoxes.put("LeftHand",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * RIGHT UPPER ARM
		 */
		instance = new ModelInstance(assets.get("Cubebot/UpperArm.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0, 5.3f, 1.2f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("Chest").nodes.get(0);
		instance.calculateTransforms();
		instances.put("RightUpperArm", instance);
		boundingBoxes.put("RightUpperArm",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * RIGHT LOWER ARM
		 */
		instance = new ModelInstance(assets.get("Cubebot/LowerArm.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0, 3.75f, 0);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("RightUpperArm").nodes.get(0);
		instance.calculateTransforms();
		instances.put("RightLowerArm", instance);
		boundingBoxes.put("RightLowerArm",
				node.calculateBoundingBox(new BoundingBox()));
		/*
		 * RIGHT HAND
		 */
		instance = new ModelInstance(assets.get("Cubebot/RightHand.g3dj",
				Model.class));
		node = instance.nodes.get(0);
		node.translation.set(0.01f, 2.3f, -0.0f);
		node.scale.set(1, 1, 1);
		node.parent = instances.get("RightLowerArm").nodes.get(0);
		instance.calculateTransforms();
		instances.put("RightHand", instance);
		boundingBoxes.put("RightHand",
				node.calculateBoundingBox(new BoundingBox()));

		/*
		 * Add children to each parent
		 */
		getNode(Cubebot.Chest).children.add(getNode(Cubebot.Head));
		getNode(Cubebot.Chest).children.add(getNode(Cubebot.Pelvis));

		getNode(Cubebot.RightLowerLeg).children.add(getNode(Cubebot.RightFoot));
		getNode(Cubebot.RightUpperLeg).children
				.add(getNode(Cubebot.RightLowerLeg));
		getNode(Cubebot.Pelvis).children.add(getNode(Cubebot.RightUpperLeg));

		getNode(Cubebot.LeftLowerLeg).children.add(getNode(Cubebot.LeftFoot));
		getNode(Cubebot.LeftUpperLeg).children
				.add(getNode(Cubebot.LeftLowerLeg));
		getNode(Cubebot.Pelvis).children.add(getNode(Cubebot.LeftUpperLeg));

		getNode(Cubebot.LeftLowerArm).children.add(getNode(Cubebot.LeftHand));
		getNode(Cubebot.LeftUpperArm).children
				.add(getNode(Cubebot.LeftLowerArm));
		getNode(Cubebot.Chest).children.add(getNode(Cubebot.LeftUpperArm));

		getNode(Cubebot.RightLowerArm).children.add(getNode(Cubebot.RightHand));
		getNode(Cubebot.RightUpperArm).children
				.add(getNode(Cubebot.RightLowerArm));
		getNode(Cubebot.Chest).children.add(getNode(Cubebot.RightUpperArm));

		/*
		 * Environment
		 */
		pedestal = new ModelInstance(assets.get("Cubebot/Cylinder.g3dj",
				Model.class));
		pedestal.transform.rotate(1, 0, 0, 90);
		pedestal.transform.translate(0, 0, 15);
		pedestal.transform.scale(2, 2, 1);
		defaultMaterial = instances.get("Chest").materials.get(0).get(ColorAttribute.Diffuse).copy();
		selectedMaterial = ColorAttribute.createDiffuse(Color.GREEN);
		
		/*
		 * Physics world generation
		 */
		BulletEntity e;
		for (String name : boundingBoxes.keySet()) {
			BoundingBox bb = boundingBoxes.get(name);
			Vector3 dim = bb.getDimensions();

			Model boxModel = new ModelBuilder().createBox(dim.x, dim.y, dim.z,
					new Material(ColorAttribute.createDiffuse(Color.WHITE),
							ColorAttribute.createSpecular(Color.WHITE),
							FloatAttribute.createShininess(64f)),
					Usage.Position | Usage.Normal);
			world.addConstructor(name, new BulletConstructor(boxModel, 0));
			e = world.add(name, 0, 0, 0);
			e.body.setCollisionFlags(e.body.getCollisionFlags()
					| btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT);
			e.body.userData = name;
			collisionBoxes.put(name, e);
		}
	}

	public void render() {
		//clear screen
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(.5f, .8f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		updateCollisionBoxes();
		world.update();
		camHandler.update();
		
		//render the world
		skyBox.render(cam);
		modelBatch.begin(cam);
		modelBatch.render(pedestal, environment);
		modelBatch.render(instances.get("Chest"), environment);
		modelBatch.end();
		
		//render shadows
		shadowLight.begin(Vector3.Zero, cam.direction);
		shadowBatch.begin(shadowLight.getCamera());
		shadowBatch.render(instances.get("Chest"));
		shadowBatch.end();
		shadowLight.end();
		
		//bullet debug mode
		/*modelBatch.begin(cam);
		for(BulletEntity e : collisionBoxes.values())
		modelBatch.render(e.modelInstance);
		modelBatch.end();*/
	}

	public Node getNode(String id) {
		if (instances.containsKey(id))
			return instances.get(id).nodes.get(0);
		return null;
	}

	public void updateCollisionBoxes() {
		BulletEntity e;
		for (String name : collisionBoxes.keySet()) {
			e = collisionBoxes.get(name);
			Matrix4 transform = instances.get(name).nodes.get(0).calculateWorldTransform().cpy().rotate(new Vector3(0,1,0),90).rotate(new Vector3(0,0,1),90);
			//Unknown problem with left and right foot translations
			if(name == "RightFoot" || name == "LeftFoot")
				transform.translate(new Vector3(0,-.4f,-.75f));
			e.body.setWorldTransform(transform);
			e.modelInstance.transform.set(transform);
		}
	}

	public void dispose() {
		modelBatch.dispose();
		instances.clear();
		assets.dispose();
		for (String name : collisionBoxes.keySet()) {
			collisionBoxes.get(name).dispose();
		}
		world.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		camHandler.touchDown(x, y, pointer, button);
		Ray ray = cam.getPickRay(x, y);
		rayFrom.set(ray.origin);
		rayTo.set(ray.direction).scl(100f).add(rayFrom); // 50 meters max from the origin
		// Because we reuse the ClosestRayResultCallback, we need reset it's values
		rayTestCB.setCollisionObject(null);
		rayTestCB.setClosestHitFraction(1f);
		rayTestCB.getRayFromWorld().setValue(rayFrom.x, rayFrom.y, rayFrom.z);
		rayTestCB.getRayToWorld().setValue(rayTo.x, rayTo.y, rayTo.z);

		world.collisionWorld.rayTest(rayFrom, rayTo, rayTestCB);
		if (rayTestCB.hasHit()) {
			if(instances.containsKey(selectedNode))
				instances.get(selectedNode).materials.get(0).set(defaultMaterial);
			final btCollisionObject obj = rayTestCB.getCollisionObject();
			final btRigidBody body = (btRigidBody)(obj);
			selectedNode = (String) body.userData;

			instances.get(selectedNode).materials.get(0).set(selectedMaterial);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		camHandler.touchUp(screenX,screenY,pointer,button);
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		camHandler.touchDragged(x, y, pointer);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
