package com.perfectplay.org;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CubebotGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;
    public Sound buttonPress;
    
    public void create() {

        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("GUI/buttons.json"));
        buttonPress = Gdx.audio.newSound(Gdx.files.internal("GUI/Sounds/button-press.mp3"));
       
        this.setScreen(new MenuScreen(this));
        Tween.registerAccessor(Node.class, new NodeAccessor());
        Tween.setCombinedAttributesLimit(4);
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
