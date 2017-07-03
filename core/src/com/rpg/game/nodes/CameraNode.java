package com.rpg.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rpg.game.messages.Message;
import com.rpg.game.messages.MessageListener;
import com.rpg.game.messages.MessageManager;

public class CameraNode extends BasicNode{
	
	private OrthographicCamera cam;
	private Viewport viewport;
	private MessageListener resize_listener;
	private GameNode follow;
	
	public CameraNode(Viewport v){
		this.setName("camera_node");
		this.viewport = v;
		this.cam = (OrthographicCamera) viewport.getCamera();
		resize_listener = new MessageListener() {
			public void handle(Message message) {
				if(message == Message.SCREEN_RESIZE){
					int width = (Integer) message.args.get(0);
					int height = (Integer) message.args.get(1);
					viewport.update(width,height);
				}

			}
		};
		MessageManager.addListener(resize_listener);
	}
	
	@Override
	public void setPosition(float x, float y) {
		cam.position.set(x, y, 0);
	}
	
	public Vector3 getPosition(){
		return cam.position;
	}
	
	@Override
	public void update(float delta) {
		if(follow != null){
			cam.position.set(follow.worldX()+follow.getWidth()/2,follow.worldY()+follow.getHeight()*.5f,0);
		}
		cam.update();
		super.update(delta);
	}
	
	public void setFollow(GameNode node){
		this.follow = node;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.end();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		super.draw(batch);
	}
	
	public void setZoom(float zoom){
		cam.zoom = zoom;
	}
	
	public float getZoom(){
		return cam.zoom;
	}
	
	@Override
	public void onAdded() {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		viewport.update(width, height);
	}
	
	public OrthographicCamera getCam(){
		return cam;
	}

}
