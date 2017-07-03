package com.rpg.game.nodes.kfa;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NodeTransform {
	
	public TextureRegion region;
	public float x;
	public float y;
	public float originx;
	public float originy;
	public float scalex;
	public float scaley;
	public float angle;
	public float width;
	public float height;
	public Color tint;
	
	public NodeTransform(){
		region = null;
		x = KeyFrame.VOID;
		y = KeyFrame.VOID;
		originx = KeyFrame.VOID;
		originy = KeyFrame.VOID;
		scalex = KeyFrame.VOID;
		scaley = KeyFrame.VOID;
		angle = KeyFrame.VOID;
		width = KeyFrame.VOID;
		height = KeyFrame.VOID;
		
	}
	

}
