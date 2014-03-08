package com.perfectplay.org;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen{
	final CubebotGame game;
	private Cubebot bot;
    
	public GameScreen(final CubebotGame game){
		this.game = game;
		this.bot = new Cubebot();
	}

	@Override
	public void render(float delta){
		bot.render();
	}

	@Override
	public void resize(int width, int height) { }

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
		bot.dispose();
	}
}
