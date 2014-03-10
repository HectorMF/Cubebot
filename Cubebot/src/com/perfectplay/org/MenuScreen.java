
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
	final CubebotGame game;

    private Stage stage;
    
    public MenuScreen(final CubebotGame game) {
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        int width = 200;
        int height = 50;
        
        Image logo = new Image(new Texture(Gdx.files.internal("GUI/Logo.png")));
        
        Table table = new Table();

        table.setFillParent(true);
        table.add(logo);
        table.row();
        
        /*
         * Play Button
         * 
         */
        TextButton playButton = new TextButton("Play", game.skin);
        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            	game.buttonPress.play();
                game.setScreen(new GameScreen(game));
            }
        });
        
        table.add(playButton).width(width).height(height).padBottom(5);
        table.row();
        
        /*
         * Tutorial Button
         * 
         */
        TextButton tutorialButton = new TextButton("Tutorial", game.skin);
        tutorialButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            	game.buttonPress.play();
                game.setScreen(new TutorialScreen(game));
            }
        });
        table.add(tutorialButton).width(width).height(height).padBottom(5);
        table.row();
        
        /*
         * High score Button
         * 
         */
        TextButton highScores = new TextButton("High Scores", game.skin);
        highScores.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HighScoreScreen(game));
            }
        });
        table.add(highScores).width(width).height(height).padBottom(5);
        table.row();
        
        /*
         * Setting Button
         * 
         */
        TextButton settings = new TextButton("Settings", game.skin);
        settings.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingScreen(game));
            }
        });
        table.add(settings).width(width).height(height).padBottom(5);
        table.row();
        
        /*
         * Quit Button
         * 
         */
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