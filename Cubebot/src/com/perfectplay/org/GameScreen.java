package com.perfectplay.org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScreen implements Screen {
	final CubebotGame game;
	private Cubebot bot;
	private AnimationManager animationManager;
	private Stage stage;

	public GameScreen(final CubebotGame game) {
		this.game = game;
		this.bot = new Cubebot();
		this.stage = new Stage();
		Table table = new Table();
		table.row().fill().expand();
		table.setFillParent(true);
		TextButton resetButton = new TextButton("Reset", game.skin);
		resetButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				game.setScreen(new GameScreen(game));
			}
		});

		table.add(resetButton).width(200).height(50).bottom().right()
				.padRight(50).padBottom(20);

		table.row();

		TextButton menuButton = new TextButton("Menu", game.skin);
		menuButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				game.setScreen(new MenuScreen(game));
			}
		});

		table.add(menuButton).width(200).height(50).bottom().right()
				.padRight(50).padBottom(50);

		InputMultiplexer inputMux = new InputMultiplexer();
		inputMux.addProcessor(bot.getCamController());
		inputMux.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMux);
		stage.addActor(table);

		animationManager = new AnimationManager();
<<<<<<< HEAD


		// animationManager.addAnimation("RotateBody",
		// bot.getNode(Cubebot.Chest),
		// "Animations/CubebotTest.txt");
		// animationManager.startAnimation("RotateBody", false);

		// animationManager.addAnimation("Rotate",
		// bot.getNode(Cubebot.LeftLowerArm), "Animations/Wave.txt");
		// animationManager.startAnimation("Rotate", false);

		animationManager.addAnimation("Animation",
				new Animation(bot.getNode(Cubebot.RightHand),
						"Animations/RightHandTurnForward.txt").delay(0));

	
		animationManager.addAnimation("RotateBody", new Animation(bot.getNode(Cubebot.Chest),
				"Animations/CubebotTest.txt"));
		animationManager.startAnimation("RotateBody", false);
=======
		
		//animationManager.addAnimation("RotateBody", bot.getNode(Cubebot.Chest),
		//		"Animations/CubebotTest.txt");
		//animationManager.startAnimation("RotateBody", false);
>>>>>>> 4e4ba3288d345614907107018e4e5fa4d5d321ca

		//animationManager.addAnimation("Rotate",
		//		bot.getNode(Cubebot.LeftLowerArm), "Animations/Wave.txt");
		//animationManager.startAnimation("Rotate", false);
		
		/*//Right Limb Rotation
		animationManager.addAnimation("Animation", 
				bot.getNode(Cubebot.RightHand), "Animations/RightHandTurnForward.txt");
		animationManager.startAnimation("Animation", false);
		
		animationManager.addAnimation("Animation1", 
				bot.getNode(Cubebot.RightUpperArm), "Animations/RightInnerArmTurnForward.txt");
		animationManager.startAnimation("Animation1", false);
		
		animationManager.addAnimation("Animation2", 
				bot.getNode(Cubebot.RightLowerArm), "Animations/RightOuterArmTurnForward.txt");
		animationManager.startAnimation("Animation2", false);
		*/
		
		//animationManager.addAnimation("AnimationHead", 
		//		bot.getNode(Cubebot.Head), "Animations/HeadTurnForward.txt");
		//animationManager.startAnimation("AnimationHead", false);
		
		/*//Go To Cube Scripts*/
		
		animationManager.addAnimation("HeadCube", 
				new Animation(bot.getNode(Cubebot.Head), "Animations/HeadGoToCube.txt"));
		animationManager.startAnimation("HeadCube", false);
		
		animationManager.addAnimation("RightUpperArmCube", 
				new Animation(bot.getNode(Cubebot.RightUpperArm), "Animations/RightInnerArmGoToCube.txt"));
		animationManager.startAnimation("RightUpperArmCube", false);
		
		animationManager.addAnimation("LeftUpperArmCube", 
				new Animation(bot.getNode(Cubebot.LeftUpperArm), "Animations/LeftInnerArmGoToCube.txt"));
		animationManager.startAnimation("LeftUpperArmCube", false);
		
		animationManager.addAnimation("RightLowerArmCube", 
				new Animation(bot.getNode(Cubebot.RightLowerArm), "Animations/RightOuterArmGoToCube.txt"));
		animationManager.startAnimation("RightLowerArmCube", false);
		
		animationManager.addAnimation("LeftLowerArmCube", 
				new Animation(bot.getNode(Cubebot.LeftLowerArm), "Animations/LeftOuterArmGoToCube.txt"));
		animationManager.startAnimation("LeftLowerArmCube", false);
		
		animationManager.addAnimation("RightHandCube", 
				new Animation(bot.getNode(Cubebot.RightHand), "Animations/RightHandToCube.txt"));
		animationManager.startAnimation("RightHandCube", false);
		
<<<<<<< HEAD
		
=======
		animationManager.addAnimation("LeftHandCube", 
				new Animation(bot.getNode(Cubebot.LeftHand), "Animations/LeftHandToCube.txt"));
		animationManager.startAnimation("LeftHandCube", false);
>>>>>>> 4e4ba3288d345614907107018e4e5fa4d5d321ca
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.5f, .8f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		bot.render();
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
		animationManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height);
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		bot.dispose();
		stage.dispose();
	}
}
