package com.rpg.game.nodes.kfa;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.rpg.game.assets.AtlasWrapper;
import com.rpg.game.nodes.GameNode;
import com.rpg.game.nodes.SpriteNode;
import com.rpg.game.utils.RpgUtils;

public class KeyFrame {
	
	protected static final int VOID = -999;
	
	private KeyFrameNode kfn;
	
	private ObjectMap<String, NodeTransform> transforms;
	
	private AtlasWrapper atlas;
	
	private float last_for;
	
	public KeyFrame(){
		last_for = .25f;
		transforms = new ObjectMap<String, NodeTransform>();
	}
	
	public void setKFN(KeyFrameNode kfn){
		this.kfn = kfn;
	}
	
	public void setLastFor(float last_for){
		this.last_for = last_for;
	}
	
	public float getLastFor(){
		return last_for;
	}
	
	public void addTransform(String node_name,NodeTransform transform){
		transforms.put(node_name, transform);
	}
	
	public void applyTransforms(){
		Array<GameNode> children = kfn.getChildren();
		for (int i = 0; i < children.size; i++) {
			SpriteNode node = (SpriteNode) children.get(i);
			if(transforms.containsKey(node.getName())){
				NodeTransform nt = transforms.get(node.getName());
				if(nt.region != null){
					node.setTextureRegion(nt.region);
					
				}
				if(nt.x!=VOID){
					node.setPosition(nt.x, node.getY());
				}
				if(nt.y!=VOID){
					node.setPosition(node.getX(),nt.y);
				}
				if(nt.scalex!=VOID){
					node.setScale(nt.scalex, node.getYScale());
				}
				if(nt.scaley!=VOID){
					node.setScale(node.getXScale(),nt.scaley);
				}
				if(nt.originx!=VOID){
					node.setOrigin(nt.originx, node.originY());
				}
				if(nt.originy!=VOID){
					node.setOrigin(node.originX(), nt.originy);
				}
			}
			transforms.get(node.getName());
		}
	}
	//holds state of frame objects
	

}
