package com.rpg.game.nodes.kfa;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class NodeTransform {
	
	public AtlasRegion region;
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
	public int visible;
	public float alpha;
	
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
		visible = KeyFrame.VOID;
		tint = Color.WHITE;
		alpha = KeyFrame.VOID;
		
	}
	

}
