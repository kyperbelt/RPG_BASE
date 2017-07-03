package com.rpg.game.nodes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * node used to cull child nodes inside parent node
 * @author kyperbelt
 *
 */
public class CullNode extends BasicNode{
	
	private CameraNode cam;
	private BoundingBox box;
	private Vector3 min,max;
	
	public CullNode(CameraNode cam){
		super();
		this.setName("cull_node");
		this.cam = cam;
		box = new BoundingBox();
		min = new Vector3();
		max = new Vector3();
	}
	
	@Override
	public void update(float delta) {
		for (int i = 0; i < getParent().getChildren().size; i++) {
			GameNode node = getParent().getChildren().get(i);
			boolean in = isInFrustum(node);

			node.setVisible(in);
		}
	}
	
	public boolean isInFrustum(GameNode node){
		OrthographicCamera cam = this.cam.getCam();
		min.set(node.worldX(),node.worldY(),0);
		max.set(node.worldX()+node.getWidth(),node.worldY()+node.getHeight(),0);
		box.set(min, max);
		return cam.frustum.boundsInFrustum(box);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
	
	@Override
	public void onAdded() {
		
		super.onAdded();
	}

}
