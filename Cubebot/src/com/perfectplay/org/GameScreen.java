package com.perfectplay.org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScreen implements Screen {
	private Preferences highscores;
	final CubebotGame game;
	private Cubebot bot;
	private CubebotAnimation animations;
	private Stage stage;
	private TextButton fold;
	private TextButton unfold;
	private float time;
	private TextArea timer;
	private boolean done;
	private Table dialog;
	public GameScreen(final CubebotGame game) {
		this.game = game;
		this.bot = new Cubebot(game);
		this.animations = new CubebotAnimation(bot);
		done = false;
		/*
		bot.setColor(Color.PINK);
		bot.setSelectionColor(Color.RED);
		bot.setColorPiece(Cubebot.Head, Color.GREEN);
		bot.setColorPiece(Cubebot.Chest, Color.YELLOW);
		bot.setColorPiece(Cubebot.Pelvis, Color.valueOf("00FFFF"));
		
		bot.setColorPiece(Cubebot.LeftLowerArm, Color.valueOf("31f4e3"));
		bot.setColorPiece(Cubebot.LeftUpperArm, Color.valueOf("f41e98"));
		bot.setColorPiece(Cubebot.LeftHand, Color.valueOf("e4bb9f"));
		
		bot.setColorPiece(Cubebot.LeftLowerLeg, Color.valueOf("FFFF00"));
		bot.setColorPiece(Cubebot.LeftUpperLeg, Color.valueOf("0033FF"));
		bot.setColorPiece(Cubebot.LeftFoot, Color.valueOf("6600FF"));
		
		bot.setColorPiece(Cubebot.RightLowerArm, Color.MAGENTA);
		bot.setColorPiece(Cubebot.RightUpperArm, Color.valueOf("009dde"));
		bot.setColorPiece(Cubebot.RightHand, Color.valueOf("d91548"));
		
		bot.setColorPiece(Cubebot.RightLowerLeg, Color.valueOf("FF6633"));
		bot.setColorPiece(Cubebot.RightUpperLeg, Color.valueOf("663333"));
		bot.setColorPiece(Cubebot.RightFoot, Color.valueOf("9ede00"));
		*/
		
		
		this.stage = new Stage();
		Table table = new Table();
		table.row().fill().expand().colspan(3);
		table.setFillParent(true);
		TextButton resetButton = new TextButton("Reset", game.skin);
		resetButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				game.getScreen().dispose();
				game.setScreen(new GameScreen(game));
			}
		});

		//table.add(resetButton).width(200).height(50).bottom().right()
		//		.padRight(50).padBottom(10);

		
		TextButton menuButton = new TextButton("Menu", game.skin);
		menuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.buttonPress.play();
				game.getScreen().dispose();
				game.setScreen(new MenuScreen(game));
			}
		});
		menuButton.setDisabled(true);
		//table.add(menuButton).width(200).height(50).bottom().right()
		//		.padRight(50).padBottom(10);
		
	
		
		TextButton zeroButton = new TextButton("Front View", game.skin);
		zeroButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				bot.camHandler.setRotation(CameraHandler.FRONT);
			}
		});
		table.add(zeroButton).width(150).height(50).bottom().right()
				.padRight(50).padBottom(10);

		table.row().colspan(3);
		
		TextButton ninetyButton = new TextButton("Left View", game.skin);
		ninetyButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				bot.camHandler.setRotation(CameraHandler.LEFT);
			}
		});

		table.add(ninetyButton).width(150).height(50).bottom().right()
				.padRight(50).padBottom(10);

		table.row().colspan(3);;
		TextButton oneEightyButton = new TextButton("Right View", game.skin);
		oneEightyButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				bot.camHandler.setRotation(CameraHandler.RIGHT);
			}
		});

		table.add(oneEightyButton).width(150).height(50).bottom().right()
				.padRight(50).padBottom(10);

		table.row().colspan(3);
		
		TextButton twoSeventyButton = new TextButton("Back View", game.skin);
		twoSeventyButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				bot.camHandler.setRotation(CameraHandler.BACK);
			}
		});

		table.add(twoSeventyButton).width(150).height(50).bottom().right()
				.padRight(50).padBottom(10);
		table.row().colspan(3);;
		TextButton topButton = new TextButton("Top View", game.skin);
		topButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				bot.camHandler.setRotation(CameraHandler.TOP);
			}
		});
		
		
		
		
		fold = new TextButton("Fold", game.skin);
		fold.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.buttonPress.play();
				if(bot.selectedNode != ""){
					animations.fold(animations.getType(bot.selectedNode));
				}
			}
		});

		
		

		unfold = new TextButton("Unfold", game.skin);
		unfold.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.buttonPress.play();
				if(bot.selectedNode != ""){
					animations.unfold(animations.getType(bot.selectedNode));
				}
			}
		});
		table.row().colspan(3);
		
		table.add(topButton).width(150).height(50).bottom().right()
		.padRight(50).padBottom(25);



		Table tab = new Table();
		tab.setFillParent(true);
		tab.row().expand(0,1);
		tab.add();
		tab.row().width(500).height(56).bottom().padBottom(50);
		//tab.setFillParent(true);
		tab.add(fold).width(200).height(50).bottom().padRight(10).padBottom(25);
		tab.add(unfold).width(200).bottom().height(50).padRight(0).padBottom(25);


		Table tabl = new Table();
		tabl.setFillParent(true);
		tabl.row().expand(1,1);
		timer = new TextArea("", game.skin);
		tabl.add(timer).width(150).height(26).top().right().padRight(50).padTop(25);
		InputMultiplexer inputMux = new InputMultiplexer();
		inputMux.addProcessor(bot);
		inputMux.addProcessor(0, stage);
		Gdx.input.setInputProcessor(inputMux);
		dialog = new Table();
		dialog.setFillParent(true);
		table.row().expand();
		
		stage.addActor(table);
		stage.addActor(tab);
		stage.addActor(tabl);
		stage.addActor(dialog);

	}

	@Override
	public void render(float delta) {
		if(!done)
		time+=delta;
		String temp = (Math.round(time*10))/10f +"";
		String v = "";
		while(v.length() + temp.length() < 30)
			v += " ";
		v+= temp;
		timer.setText(v  + "");
		Gdx.gl.glClearColor(.5f, .8f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		String val = animations.getState(animations.getType(bot.selectedNode));
		if(val != null){
			if(val.equals("F")){
				unfold.setDisabled(false);
				fold.setDisabled(true);
			}else if(val.equals("UF")){
				fold.setDisabled(false);
				unfold.setDisabled(true);
			}else{
				fold.setDisabled(true);
				unfold.setDisabled(true);
			}
		}else{
			fold.setDisabled(true);
			unfold.setDisabled(true);
		}
		stage.act(Gdx.graphics.getDeltaTime());
		bot.render();
		stage.draw();
		animations.update(delta);
		if(animations.isFolded() && !done){
			
			highscores = Gdx.app.getPreferences("Highscores");
			float min  = highscores.getFloat("min");
			
			if(min == 0 || time < min){
				int pos = 1;
				float tempVal = 0;
				for(int i = 1; i <= 10; i++)
				{
					float t = highscores.getFloat(i+"");
					if(time < t || t == 0 ){
						highscores.putFloat(i+"",time);
						tempVal = t;
						pos = i;
						break;
						
					}
				}
				for(int i = pos+1; i <= 10; i++){
					float p = highscores.getFloat(i+"");
					highscores.putFloat(i+"", tempVal);
					tempVal = p;
				}
				highscores.putFloat("min", highscores.getFloat("10"));
			}
			highscores.flush();
			done = true;
			Dialog finished = new Dialog("Cubebot",game.skin,"dialog");
			finished.text("You have folded the Cubebot in: \n" + v + " Seconds!");
			TextButton yes = new TextButton("Ok", game.skin);
			yes.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				game.getScreen().dispose();
				game.setScreen(new HighScoreScreen(game));
				}
			});
			yes.setWidth(100);
			yes.setHeight(20);
			finished.setWidth(300);
			finished.button(yes,true).size(100, 20);;
			finished.center();
			finished.pack();
			finished.align(Align.center);
			dialog.add(finished)
;		}
	}

	@Override
	public void resize(int width, int height) {
		bot.resize(width,height);
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
