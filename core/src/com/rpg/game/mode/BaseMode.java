package com.rpg.game.mode;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpg.game.RpgBase;
import com.rpg.game.assets.GameAssets;
import com.rpg.game.nodes.GameNode;
import com.rpg.game.utils.SoundManager;

public abstract class BaseMode implements Screen{
	
	RpgBase app;
	
	private boolean created;
	
	public BaseMode(){
		created = false;
	}
	
	public boolean isCreated(){
		return created;
	}
	
	public void setApp(RpgBase app){
		this.app = app;
	}
	
	public RpgBase app(){
		return this.app;
	}
	
	public void show() {
		create();
	}
	
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void dispose() {
		remove();
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		draw(app.batch);
	}
	
	public GameAssets assets(){
		return app.assets;
	}
	
	public GameNode root(){
		return app.root;
	}
	
	public SoundManager sound(){
		return app.sound;
	}
	
	public Stage ui(){
		return app.ui;
	}
	
	public abstract void load(GameAssets loader);
	
	public abstract void create();
	
	public abstract void update(float delta);
	
	public abstract void draw(SpriteBatch batch);
	
	public abstract void remove();
	
	

}
