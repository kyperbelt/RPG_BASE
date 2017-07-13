package com.rpg.game;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;

public class Configuration {
	
	public static final boolean DEBUG = true;
	public static final boolean FULLSCREEN = false;
	
	//defaults
	public static final String TITLE = "RPG_BASE";
	public static final String VERSION = "v0.0.0";
	public static final int WIDTH = 854;
	public static final int HEIGHT = 600;
	
	//app
	public static final Color DEFAULT_BG_COLOR = Color.BLACK;
	public static final FileHandleResolver DEFAULT_RESOLVER = new InternalFileHandleResolver();
	public static final String ASSET_PATH = "";
	
	//binds
	public static final int MAX_KEY_ACTIONS = 4;
	
	//world
	public static final float PIXELS_PER_METER = 32;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 2;
	public static final float GRAVITY_X = 0;
	public static final float GRAVITY_Y = -2f;
	public static final float WORLD_STEP = 1/60f;

}
