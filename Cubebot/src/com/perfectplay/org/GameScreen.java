package com.perfectplay.org;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen{
	final CubebotGame game;
	private Cubebot bot;
	private AnimationManager animationManager;
	float time;
    
	public GameScreen(final CubebotGame game){
		this.game = game;
		this.bot = new Cubebot();
		animationManager = new AnimationManager();
		System.out.println(bot.getNode(Cubebot.Chest));
		
		animationManager.addAnimation("RotateBody", bot.getNode(Cubebot.Pelvis), "CubebotTest.txt");
		animationManager.startAnimation("RotateBody", false);

		time = 0;
	}

	@Override
	public void render(float delta){
		bot.render();
		time+= delta;
		animationManager.update(delta);
		//if(time > 5) animationManager.ReverseAnimation("RotateBody");
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
