package com.perfectplay.org;

import org.lwjgl.opengl.DisplayMode;

import bullet.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		/* how to set desktop config??
		 * google it.
		 */
		//System.out.println(Gdx.files.getLocalStoragePath());
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		//boolean isLocAvailable = Gdx.files.isLocalStorageAvailable();
	//	System.out.println(isLocAvailable);
		/*Preferences config =  Gdx.app.getPreferences("config");
		config.putInteger("width", 1920);
		config.putInteger("height", 1080);
		config.putBoolean("fullscreen", false);
		config.putBoolean("gl20", true);
		config.putBoolean("vsync", false);
		cfg.title = "Cubebot";
		cfg.useGL20 = config.getBoolean("gl20");
		cfg.fullscreen = config.getBoolean("fullscreen");
		cfg.width = config.getInteger("width");
		cfg.height = config.getInteger("height");
		cfg.vSyncEnabled = config.getBoolean("vsync");*/
		cfg.useGL20 = true;
		//cfg.resizable = false;
		cfg.width = 1080;
		cfg.height = 760;
	//	new LwjglApplication(new RayCastTest(), cfg);
		new LwjglApplication(new CubebotGame(), cfg);
	}
}
