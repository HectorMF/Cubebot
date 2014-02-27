package com.perfectplay.org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen{
	final Cubebot game;
	
	//http://blog.xoppa.com/loading-models-using-libgdx/
	   public PerspectiveCamera cam;
	   public CameraInputController camController;
	   public ModelBatch modelBatch;
	   public AssetManager assets;
	   public Array<ModelInstance> instances = new Array<ModelInstance>();
	   public Environment environment;
	   public boolean loading;
	    
	public GameScreen(final Cubebot game){
		this.game = game;
		
		//set up camera, environment, and input
	    modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
         
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(3f, 3f, 3f);
        cam.lookAt(0,0,0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();
 
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
         
        //load in the file and store it in the asset manager
        assets = new AssetManager();
        assets.load("test.g3db", Model.class);
        loading = true;
	}

	private void doneLoading() {
		//create a model instance
	    Model ship = assets.get("test.g3db", Model.class);
	    ModelInstance shipInstance = new ModelInstance(ship); 
	    instances.add(shipInstance);
	    loading = false;
	}
	 
	@Override
	public void render(float delta) {
		   if (loading && assets.update())
	            doneLoading();
	        camController.update();
	         
	        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        Gdx.gl.glClearColor(.5f, .8f, 1, 1);
	        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	 
	        modelBatch.begin(cam);
	        modelBatch.render(instances, environment);
	        modelBatch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
        modelBatch.dispose();
        instances.clear();
        assets.dispose();
	}
}
