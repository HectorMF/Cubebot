package com.perfectplay.org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TutorialScreen implements Screen{
	final CubebotGame game;
	
	public Environment environment;
	public PerspectiveCamera cam;
	public CameraInputController camController;
	public ModelBatch modelBatch;
	public Model model;
	public ModelInstance instance;
	Sound sound;
	Texture speech;
	private Stage stage;
	
	public TutorialScreen(final CubebotGame game){
		this.game = game;
		InputMultiplexer inputMux = new InputMultiplexer();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		speech = new Texture(Gdx.files.internal("GUI/speech.png"));
		modelBatch = new ModelBatch();

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0,0,0);
		cam.near = 0.1f;
		cam.far = 300f;
		cam.update();
		
		this.stage = new Stage();
	   	inputMux.addProcessor(stage);
	        
	    Table table = new Table();
	    table.setFillParent(true);
	    TextButton playButton = new TextButton("Menu", game.skin);
	    
	    playButton.addListener(new ClickListener() {
	    public void clicked(InputEvent event, float x, float y) {
	    		game.setScreen(new MenuScreen(game));
	            }
	    });
	    table.add(playButton).width(100).padLeft(10).padBottom(10);
	    table.setClip(true);
	    table.left().bottom();
	    table.row();
	    TextArea tArea = new TextArea("TEST",game.skin);
	    tArea.setDisabled(true);
	    table.add(tArea);
	    stage.addActor(table);
	        
	        
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f, 5f, 5f, 
            new Material(ColorAttribute.createDiffuse(Color.valueOf("f19d4a"))),
            Usage.Position | Usage.Normal);
        instance = new ModelInstance(model);
        
        camController = new CameraInputController(cam);
        inputMux.addProcessor(camController);
        Gdx.input.setInputProcessor(inputMux);
        sound = Gdx.audio.newSound(Gdx.files.internal("GUI/Sounds/boink.mp3"));
        sound.play(1.0f);

	}
	
	@Override
	public void render(float delta) {
		camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(.5f, .8f, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        Quaternion q = new Quaternion();
        System.out.println(cam.combined.getRotation(q).transform(new Vector3(1,1,1)));
        modelBatch.begin(cam);
        modelBatch.render(instance, environment);
        modelBatch.end();
        game.batch.begin();
        game.batch.draw(speech,Gdx.graphics.getWidth()/2 ,Gdx.graphics.getHeight()/2);
        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
	    stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height,true);
		
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
		model.dispose();
	}

}
