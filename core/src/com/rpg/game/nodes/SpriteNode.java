package com.rpg.game.nodes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteNode extends BasicNode{
	
	private TextureRegion region;
	
	public SpriteNode(TextureRegion region){
		super();
		if(region!=null){
			setTextureRegion(region);
			setSize(region.getRegionWidth(), region.getRegionHeight());
		}
		//this.region = region;
		this.setName("sprite_node");
	}
	
	public void setTextureRegion(TextureRegion region,float w,float h){
		setSize(w,h);
		this.region = region;
	}
	
	public void setTextureRegion(TextureRegion region){
		float nw = getWidth();
		float nh = getHeight();
		if(getWidth() <=2&&region!=null){
			nw = region.getRegionWidth();
		}
		if(getHeight()<=2&&region!=null){
			nh = region.getRegionHeight();
		}
			
		setTextureRegion(region,nw,nh);
	}
	
	public TextureRegion getRegion(){
		return region;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if(!isVisible() || region == null)
			return;
		batch.setColor(getTint().r, getTint().g, getTint().b, getAlpha());
		batch.draw(region, getParent().getX()+getX(), getParent().getY()+getY(),
				originX(), originY(),
				getWidth(), getHeight(), 
		getParent().getXScale()*getXScale(),getParent().getYScale()*getYScale(), 
		getParent().getAngle()+getAngle());
		super.draw(batch);
	}

}
