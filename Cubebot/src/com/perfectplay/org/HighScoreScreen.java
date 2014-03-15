package com.perfectplay.org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HighScoreScreen implements Screen {
	/*
	 * Highscores are stored by their position in the list i.e. Position 1, is
	 * stored with the key "1" The key "min" will return the smallest value in
	 * the high score list. "size" will return the number of elements in the
	 * list.
	 */
	private Preferences highscores;
	private String scores = "";
	private Stage stage;
	private Texture background;
	private SpriteBatch batch;

	public HighScoreScreen(final CubebotGame game) {
		this.batch = new SpriteBatch();

		this.stage = new Stage();

		highscores = Gdx.app.getPreferences("Highscores");

		highscores.getInteger("size");
		highscores.getFloat("min");
		for (int i = 1; i < 10; i++)
			scores += "                              " + i + " :  " +  Math.round(highscores.getFloat(i + "")*100)/100f + " Seconds\n";
		scores += "                            " + 10 + " :  " + Math.round(highscores.getFloat(10 + "")*100)/100f + " Seconds\n";
		this.stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		table.setFillParent(true);
	
		table.row();
		TextArea tArea = new TextArea(scores, game.skin);
		tArea.setDisabled(true);

		table.add(tArea).padTop(450).padBottom(10).height(220).width(350);

		table.row();
		TextButton menuButton = new TextButton("Menu", game.skin);
		menuButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.buttonPress.play();
				game.getScreen().dispose();
				game.setScreen(new MenuScreen(game));
			}
		});

		table.add(menuButton).width(200).height(50).bottom().right()
				.padRight(0).padBottom(50);
		table.row();
		table.debugTable();
		table.debug();

		Image logo = new Image(new Texture(Gdx.files.internal("GUI/Logo1.png")));
		background = new Texture(Gdx.files.internal("Textures/Background.png"));

		Table tableLogo = new Table();

		tableLogo.setFillParent(true);
		tableLogo.row().fill().expand();

		tableLogo.setFillParent(true);
		tableLogo.row().fill().expand();
		tableLogo.add(logo).width(1000).height(600).padTop(60);
		tableLogo.row();
		tableLogo.add().width(900).height(70).padBottom(30);
		
		stage.addActor(tableLogo);
		stage.addActor(table);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.5f, .8f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, stage.getWidth(), stage.getHeight(), 0, 0,
				background.getWidth(), background.getHeight(), false, false);
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
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
		stage.dispose();
	}

}
