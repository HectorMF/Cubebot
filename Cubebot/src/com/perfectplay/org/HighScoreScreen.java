package com.perfectplay.org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HighScoreScreen implements Screen{
	/*
	 * Highscores are stored by their position in the list
	 * i.e. Position 1, is stored with the key "1"
	 * The key "min" will return the smallest value in the 
	 * high score list. "size" will return the number of elements in 
	 * the list.
	 * 
	 */
	private Preferences highscores;
	private int size;
	private float min;
	private String scores = "";
	private Stage stage;
	private CubebotGame game;

	public HighScoreScreen(final CubebotGame game){
		this.game = game;
		
		highscores = Gdx.app.getPreferences("Highscores");
		
		size = highscores.getInteger("size");
		min = highscores.getFloat("min");
		for(int i = 1; i <= 10; i++)
			scores += i  + " : " + highscores.getFloat(i + "") + "\n";
		this.stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		
		table.row().expandX().fillX();
		
		Image logo = new Image(new Texture(Gdx.files.internal("GUI/highscore.png")));
        table.add(logo).top().width(1000).height(200);
        table.row().expand().fill();
		TextArea tArea = new TextArea(scores, 	game.skin);
	    tArea.setDisabled(true);
	
	    table.add(tArea).top().minWidth(400).maxWidth(1000).fillY();
	    
	    table.row();
	    TextButton menuButton = new TextButton("Menu", game.skin);
        menuButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            	game.buttonPress.play();
                game.setScreen(new MenuScreen(game));
            }
        });
        
        table.add(menuButton).width(200).height(50).bottom().right().padRight(50).padBottom(50);
        table.row();
        table.setFillParent(true);
	    table.debugTable();
	    table.debug();
	    stage.addActor(table);
	    table.validate();

	    System.out.println(stage.getWidth());
		System.out.println(table.getWidth());
		System.out.println(stage.getHeight());
		System.out.println(table.getHeight());
		
		System.out.println(table.getParent());
	   
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.5f, .8f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    stage.act(Gdx.graphics.getDeltaTime());
	    Table.drawDebug(stage);
	    stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height,true);
	}

	@Override
	public void show() { }

	@Override
	public void hide() { }

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void dispose() {
		stage.dispose();
	}

}
