package com.rpg.game.nodes;

import com.badlogic.gdx.math.Polygon;

public class RectangleNode extends BasicNode{
	
	private Polygon poly;
	
	public RectangleNode(float width,float height){
		this.setSize(width, height);
		setName("rectangle");
		poly = new Polygon();
	}
	
	public Polygon getRectangle(){
		GameNode p = getParent();
		if(p == null)
			p = BasicNode.NULL;
		poly.setVertices(new float[]{0,0,
				getWidth(),0,
				getWidth(),getHeight(),
				0,getHeight()});
		poly.setRotation(p.getAngle()+getAngle());
		poly.setOrigin(originX(), originY());
		poly.setScale(p.getXScale()*getXScale(), p.getYScale()*getYScale());
		poly.setPosition(getX(),getY());
		return poly;
	}
	
	@Override
	public void onAdded() {
		super.onAdded();
	}

}
