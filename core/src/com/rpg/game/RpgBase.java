package com.rpg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rpg.game.assets.GameAssets;
import com.rpg.game.messages.Message;
import com.rpg.game.messages.MessageManager;
import com.rpg.game.mode.BaseMode;
import com.rpg.game.mode.GameMode;
import com.rpg.game.mode.LoadMode;
import com.rpg.game.nodes.BasicNode;
import com.rpg.game.utils.Keybinds;
import com.rpg.game.utils.PhysWorld;
import com.rpg.game.utils.RpgUtils;
import com.rpg.game.utils.SoundManager;
import com.rpgbase.tests.AnimationEditor;

public class RpgBase extends Game {

	// clear color
	private Color background_color;
	//current GameMode
	private String current;
	//the load mode
	private LoadMode load;
	//the other modes in a map for access
	private ObjectMap<String, BaseMode> modes;

	// ----PUBLIC
	// ----------

	// default spritebatch
	public SpriteBatch batch;
	//game assets
	public GameAssets assets;
	//the root node of the game.
	public BasicNode root;
	//the stage where we form our ui
	public Stage ui;
	//the sound of the game used to load sounds and play them at varying volumes
	public SoundManager sound;
	
	

	@Override
	public void create() {
		//initialize
		background_color = Configuration.DEFAULT_BG_COLOR;
		batch = new SpriteBatch();
		modes = new ObjectMap<String, BaseMode>();
		root = new BasicNode();
		root.setApp(this);
		ui = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(ui);
		assets = new GameAssets();
		load = new LoadMode();
		load.setApp(this);
		assets.setCurrentMode("load");
		load.load(assets);
		assets.finishLoading();

		
		addMode("gamemode", new AnimationEditor());
		setMode("gamemode");
	}
	
	public void addMode(String name,BaseMode mode){
		if(modes.containsKey(name)){
			RpgUtils.logError("could not add mode with duplicate name ["+name+"].");
			return;
		}
		mode.setApp(this);
		modes.put(name, mode);
	}
	
	public void setMode(String name){
		if(!modes.containsKey(name)){
			RpgUtils.logError("Failed to find mode ["+name+"]. Add to game or check your spelling.");
			return;
		}
		current = name;
		setScreen(load);
	}
	
	public BaseMode getMode(String name){
		if(!modes.containsKey(name)){
			RpgUtils.logError("Failed to find mode ["+name+"]. Add to game or check your spelling.");
			return null;
		}
		return modes.get(name);
	}
	
	public String currentModeName(){
		return current;
	}
	
	public BaseMode getCurrentMode(){
		return modes.get(current);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(background_color.r, background_color.g, background_color.b, background_color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float delta = Gdx.graphics.getDeltaTime();
		PhysWorld.update(delta);
		super.render();
		Keybinds.update();
	}

	public void setBackgroundColor(Color color) {
		this.background_color = color;
	}

	@Override
	public void resize(int width, int height) {
		MessageManager.sendMessage(Message.SCREEN_RESIZE, width, height);
		ui.getViewport().update(width, height);
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
