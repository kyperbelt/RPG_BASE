package com.rpg.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.rpg.game.Configuration;
import com.rpg.game.utils.RpgUtils;

public class GameAssets {

	private AssetManager manager;
	private ObjectMap<String,Array<String>> assets;
	
	private String current_mode;
	
	public GameAssets(){
		current_mode = "";
		manager = new AssetManager(Configuration.DEFAULT_RESOLVER);
		assets = new ObjectMap<String, Array<String>>();
	}
	
	public void setCurrentMode(String mode){
		if(!assets.containsKey(mode)){
			assets.put(mode, new Array<String>());
		}
		current_mode  = mode;
	}
	
	public void finishLoading(){
		manager.finishLoading();
	}
	
	public boolean loadNext(){
		return manager.update();
	}
	
	public void unloadMode(String mode){
		
		if(assets.containsKey(mode)){
			Array<String> unload_ = assets.get(mode);
			for (int i = 0; i < unload_.size; i++) {
				manager.unload(unload_.get(i));
			}
		}else{
			RpgUtils.logError("mode set in assets does not exist ["+current_mode+"]");
		}
		
	}
	
	public void loadTexture(String name){
		load(name,Texture.class);
	}
	
	public void loadAtlas(String name){
		load(name, TextureAtlas.class);
	}
	
	public void loadSound(String name){
		load(name,Sound.class);
	}
	
	public void loadMusic(String name){
		load(name,Music.class);
	}
	
	private void load(String name,Class<?> type){
		if(current_mode.isEmpty()){
			RpgUtils.logError("current mode not set in GameAssets. use setCurrentMode() before loading mode.");
			return;
		}
		if(!assets.containsKey(current_mode)){
			RpgUtils.logError("mode set in assets does not exist ["+current_mode+"]");
			return;
		}
		Array<String> mode_assets = assets.get(current_mode);
		if(!mode_assets.contains(name, false)){
			mode_assets.add(name);
		}
		manager.load(name, type);
	}
	
	public Texture getTexture(String name){
		return manager.get(name,Texture.class);
	}
	
	public TextureAtlas getAtlas(String name){
		return manager.get(name,TextureAtlas.class);
	}
	
	public Sound getSound(String name){
		return manager.get(name,Sound.class);
	}
	
	public Music getMusic(String name){
		return manager.get(name,Music.class);
	}
	
	public void clear(){
		manager.clear();
	}
	
	public void dispose(){
		manager.dispose();
	}
	
	public boolean itemLoaded(String name){
		return manager.getAssetNames().contains(name, false);
	}
}
