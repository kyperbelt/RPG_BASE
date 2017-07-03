package com.rpg.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rpg.game.Configuration;
import com.rpg.game.RpgBase;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Configuration.WIDTH;
		config.height = Configuration.HEIGHT;
		config.title = Configuration.TITLE+Configuration.VERSION;
		config.fullscreen = Configuration.FULLSCREEN;
		new LwjglApplication(new RpgBase(), config);
	}
}
