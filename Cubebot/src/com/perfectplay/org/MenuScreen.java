
package com.perfectplay.org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen
{
	final CubebotGame game;

    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    
    public MenuScreen(final CubebotGame game) {
        this.game = game;
        this.stage = new Stage();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        int width = 300;
        int height = 50;
        
        Image logo = new Image(new Texture(Gdx.files.internal("GUI/Logo1.png")));
        Image cb = new Image(new Texture(Gdx.files.internal("Textures/Cubebot.png")));
        background = new Texture(Gdx.files.internal("Textures/Background.png"));
        
        Table tableLogo = new Table();

        tableLogo.setFillParent(true);
        tableLogo.row().fill().expand();
        
      //  table.add(cb).width(444).height(431);
        
        Table table = new Table();

        table.setFillParent(true);
        table.row().fill();        
       // table.add(cb);
        
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
        
        table.add(playButton).width(width).height(height).padBottom(10).padTop(410);
        table.row();
        
        /*
         * Tutorial Button
         * 
         *
         *       
        TextButton tutorialButton = new TextButton("Tutorial", game.skin);
        tutorialButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            	game.buttonPress.play();
                game.setScreen(new TutorialScreen(game));
            }
        });
        table.add(tutorialButton).width(width).height(height).padBottom(5);
        table.row();
        */
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
        table.add(highScores).width(width).height(height).padBottom(10);
        table.row();
        table.debugTable();
	    table.debug();
        
        /*
         * Setting Button
         */
         
        TextButton settings = new TextButton("Settings", game.skin);
        settings.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingScreen(game));
            }
        });
       // table.add(settings).width(width).height(height).padBottom(10);
      //  table.row();
        
        /*
         * Buy One Button
         */
         
        TextButton buyOne = new TextButton("Click Here to Buy Cubebot®!", game.skin);
        buyOne.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            	Gdx.net.openURI("http://www.areaware.com/pages/cubebot");
            }
        });
        //table.add(buyOne).width(width).height(height).padBottom(10);
      //  table.row();
        
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
        table.add(quit).width(width).height(height).padBottom(150);
       
        TextArea tArea = new TextArea(" \n                                                                                           Click Here to Buy Cubebot®!",game.skin);
	    tArea.setDisabled(true);
	    tArea.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            	Gdx.net.openURI("http://www.areaware.com/pages/cubebot");
            }
        });
	    tableLogo.add(logo).width(1000).height(600).padTop(60);
	    tableLogo.row();
        tableLogo.add(tArea).width(900).height(70).padBottom(30);
        stage.addActor(tableLogo);
        stage.addActor(table);

    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, stage.getWidth(), stage.getHeight(), 0, 0,background.getWidth(),background.getHeight(), false,false);
		batch.end();
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
		stage.dispose();
		
	}
}