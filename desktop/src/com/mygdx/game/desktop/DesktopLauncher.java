package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.PhysicsApp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1620;
		config.height = 900;
		config.fullscreen = false;
		config.resizable = true;
		config.title = "Collision simulation";
		new LwjglApplication(new PhysicsApp(), config);
	}
}
