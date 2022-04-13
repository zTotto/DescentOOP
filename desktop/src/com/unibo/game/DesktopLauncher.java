package com.unibo.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Descent");
	    config.setWindowedMode(Descent.GAME_WIDTH, Descent.GAME_HEIGHT);
	    config.useVsync(true);
	    config.setForegroundFPS(165);
		new Lwjgl3Application(new Descent(), config);
	}
}
