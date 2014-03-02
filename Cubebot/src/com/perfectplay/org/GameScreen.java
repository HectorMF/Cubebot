package com.perfectplay.org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
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

	   public Shader shader;
	    
	public GameScreen(final Cubebot game){
		this.game = game;
		
		//set up camera, environment, and input
	    //modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, .1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
         
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();
 
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
         
        //load in the file and store it in the asset manager
        assets = new AssetManager();
        assets.load("BreastPiece.g3dj", Model.class);
        assets.load("Wood/Diffuse.jpg",Texture.class);
        assets.load("Wood/Specular.jpg",Texture.class);
        assets.load("Wood/Normal.jpg",Texture.class);
        assets.load("Wood/Bump.jpg",Texture.class);
        assets.update(1000);
        
		 Model cube = assets.get("BreastPiece.g3dj", Model.class);

		 ModelInstance instance = new ModelInstance(cube, 0, 0, 0);
		
	        String vert = Gdx.files.internal("wood.vertex.glsl").readString();
	        String frag = Gdx.files.internal("wood.fragment.glsl").readString();
	    
	        shader = new DefaultShader(instance.getRenderable(new Renderable()), new Config(vert, frag));
	        shader.init();
	       

	        modelBatch = new ModelBatch(new DefaultShaderProvider() {
	            @Override
	            public Shader getShader(Renderable renderable) {
	                return shader;
	            }

	            @Override
	            public void dispose() {
	                shader.dispose();
	            }
	        });
        loading = true;
	}

	private void doneLoading() {
		 Model cube = assets.get("BreastPiece.g3dj", Model.class);
		 assets.get("Wood/Diffuse.jpg",Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
		 assets.get("Wood/Diffuse.jpg",Texture.class).setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		 ModelBuilder modelBuilder = new ModelBuilder();
		 ModelInstance instance = new ModelInstance(cube, 0, 0, 0);
	 
		 
		 //create a model instance
		for (int x = -5; x <= 5; x+=2) {
		      for (int z = -5; z<=5; z+=2) {
		    	// cube = modelBuilder.createBox(2f, 2f, 1f,
		    	//	      new Material(),
		    	//	      Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		    	
		          instance = new ModelInstance(cube, x*4, 0, z*4);
		          TextureAttribute diff = TextureAttribute.createDiffuse(assets.get("Wood/Diffuse.jpg",Texture.class));
		          TextureAttribute specular = TextureAttribute.createSpecular(assets.get("Wood/Specular.jpg",Texture.class));
		          TextureAttribute normal = new TextureAttribute(TextureAttribute.Normal, assets.get("Wood/Normal.jpg",Texture.class));
		          TextureAttribute bump = new TextureAttribute(TextureAttribute.Bump, assets.get("Wood/Bump.jpg",Texture.class));
		          ColorAttribute attr = ColorAttribute.createDiffuse((x+5f)/10f, (z+5f)/10f, 0, 1);
		          
		          //instance.materials.get(0).set(attr);
		       //   instance.materials.get(0).set(diff);
		        //  instance.materials.get(0).set(specular);
		        //  instance.materials.get(0).set(normal);
		          //instance.materials.get(0).set(bump);
		          instances.add(instance);
		      }
		    }
	   
	
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
	        
	        for (ModelInstance instance : instances)
	            modelBatch.render(instance, environment,shader);
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
