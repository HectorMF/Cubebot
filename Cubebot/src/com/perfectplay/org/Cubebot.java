package com.perfectplay.org;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Cubebot extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;
    public Sound buttonPress;
    
    public void create() {

        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("buttons.json"));
        buttonPress = Gdx.audio.newSound(Gdx.files.internal("button-press.mp3"));
       
        this.setScreen(new MenuScreen(this));
    
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
