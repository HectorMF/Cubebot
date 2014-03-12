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
				game.getScreen().dispose();
				game.setScreen(new GameScreen(game));
			}
		});

		table.add(resetButton).width(200).height(50).bottom().right()
				.padRight(50).padBottom(10);

		table.row();

		TextButton menuButton = new TextButton("Menu", game.skin);
		menuButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				game.setScreen(new MenuScreen(game));
			}
		});

		table.add(menuButton).width(200).height(50).bottom().right()
				.padRight(50).padBottom(10);
		
		table.row();
		
		TextButton zeroButton = new TextButton("Front View", game.skin);
		zeroButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				bot.camHandler.setRotation(CameraHandler.FRONT);
			}
		});

		table.add(zeroButton).width(200).height(50).bottom().right()
				.padRight(50).padBottom(10);

		table.row();
		
		TextButton ninetyButton = new TextButton("Left View", game.skin);
		ninetyButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				bot.camHandler.setRotation(CameraHandler.LEFT);
			}
		});

		table.add(ninetyButton).width(200).height(50).bottom().right()
				.padRight(50).padBottom(10);

		table.row();
		
		TextButton oneEightyButton = new TextButton("Right View", game.skin);
		oneEightyButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				bot.camHandler.setRotation(CameraHandler.RIGHT);
			}
		});

		table.add(oneEightyButton).width(200).height(50).bottom().right()
				.padRight(50).padBottom(10);

		table.row();
		
		TextButton twoSeventyButton = new TextButton("Back View", game.skin);
		twoSeventyButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				bot.camHandler.setRotation(CameraHandler.BACK);
			}
		});

		table.add(twoSeventyButton).width(200).height(50).bottom().right()
				.padRight(50).padBottom(50);
		
		TextButton topButton = new TextButton("Top View", game.skin);
		topButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				bot.camHandler.setRotation(CameraHandler.TOP);
			}
		});
		table.row();
		table.add(topButton).width(200).height(50).bottom().right()
				.padRight(50).padBottom(50);

		

		InputMultiplexer inputMux = new InputMultiplexer();
		inputMux.addProcessor(bot);
		inputMux.addProcessor(0, stage);
		//inputMux.addProcessor(bot.getCamController());
		//inputMux.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMux);
		stage.addActor(table);

		animationManager = new AnimationManager();


		/*//Go To Cube Scripts*/
		AnimationSequence fold = new AnimationSequence();
		fold.push(new Animation(bot.getNode(Cubebot.Head), "Animations/HeadGoToCube.txt"))
		.push(new Animation(bot.getNode(Cubebot.RightFoot), "Animations/CubebotTest.txt"))
			.push(new Animation(bot.getNode(Cubebot.RightUpperArm), "Animations/RightInnerArmGoToCube.txt"))
			.delay(2.5f)
			.push(new Animation(bot.getNode(Cubebot.LeftUpperArm), "Animations/LeftInnerArmGoToCube.txt"))
			.push(new Animation(bot.getNode(Cubebot.RightLowerArm), "Animations/RightOuterArmGoToCube.txt"))
			.delay(1.5f)
			.push(new Animation(bot.getNode(Cubebot.LeftLowerArm), "Animations/LeftOuterArmGoToCube.txt"))
			.delay(1.2f)
					.push(new Animation(bot.getNode(Cubebot.RightFoot), "Animations/RightHandToCube.txt"))

			.push(new Animation(bot.getNode(Cubebot.RightHand), "Animations/RightHandToCube.txt"))
			.push(new Animation(bot.getNode(Cubebot.LeftHand), "Animations/LeftHandToCube.txt"));
		
		animationManager.addAnimation("Fold", fold);
		animationManager.startAnimation("Fold");

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
