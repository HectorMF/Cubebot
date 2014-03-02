package com.perfectplay.org;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Cubebot";
		cfg.useGL20 = true;
		//cfg.fullscreen = true;
		cfg.width = 1920;
		cfg.height = 1080;
		
		new LwjglApplication(new Cubebot(), cfg);
	}
}
