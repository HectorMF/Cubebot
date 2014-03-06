
package com.perfectplay.org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen
{
	final Cubebot game;

    private Stage stage;
    
    public MenuScreen(final Cubebot game) {
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        int width = 200;
        int height = 50;
        
        Image logo = new Image(new Texture(Gdx.files.internal("Logo.png")));
        
        Table table = new Table();

        table.setFillParent(true);
        table.add(logo);
        table.row();
        TextButton playButton = new TextButton("Play", game.skin);
        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        
        table.add(playButton).width(width).height(height).padBottom(5);
        table.row();
        TextButton tutorialButton = new TextButton("Tutorial", game.skin);
        tutorialButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TutorialScreen(game));
            }
        });
        table.add(tutorialButton).width(width).height(height).padBottom(5);
        table.row();
        
        TextButton highScores = new TextButton("High Scores", game.skin);
        highScores.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingScreen(game));
            }
        });
        table.add(highScores).width(width).height(height).padBottom(5);
        
        table.row();
        TextButton settings = new TextButton("Settings", game.skin);
        settings.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingScreen(game));
            }
        });
        table.add(settings).width(width).height(height).padBottom(5);
        table.row();
        TextButton quit = new TextButton("Quit", game.skin);
        quit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(quit).width(width).height(height).padBottom(5);
        stage.addActor(table);

    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.5f, .8f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    stage.act(Gdx.graphics.getDeltaTime());
	    stage.draw();

	    //Table.drawDebug(stage);
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
		stage.dispose();
		
	}
}