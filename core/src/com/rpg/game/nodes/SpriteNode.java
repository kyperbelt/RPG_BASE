package com.rpg.game.nodes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rpg.game.utils.RpgUtils;

public class SpriteNode extends BasicNode{
	
	private Sprite region;
	
	public SpriteNode(AtlasSprite r){
		super();
		if(r!=null){
			setTextureRegion(r);
		}
		
		//this.region = region;
		this.setName("sprite_node");
	}
	
	public void setTextureRegion(Sprite region,float w,float h){
		this.region = region;
		setSize(w,h);
		setScale(getXScale(), getYScale());
		setOrigin(originX(), originY());
		setPosition(getX(), getY());
		setAngle(getAngle());
		setAlpha(getAlpha());
		setTint(getTint());
	}
	
	public void setTextureRegion(Sprite region){
		setTextureRegion(region,region.getWidth(),region.getHeight());
	}
	
	public TextureRegion getRegion(){
		return region;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
	@Override
	public void setOrigin(float originx, float originy) {
		if(region!=null)
			region.setOrigin(originx, originy);
		super.setOrigin(originx, originy);
	}
	
	@Override
	public void setScale(float xscale, float yscale) {
		if(region!=null)
			region.setScale(getParent().getXScale()*xscale, getParent().getYScale()*yscale);
		super.setScale(xscale, yscale);
	}
	
	@Override
	public void setAlpha(float alpha) {
		float newalpha = RpgUtils.clampNum(getParent().getAlpha()*alpha, 0, 1f);
		super.setAlpha(alpha);
		if(region!=null)
		region.setAlpha(newalpha);
	}
	
	@Override
	public float getAlpha() {
		return super.getAlpha()/getParent().getAlpha();
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		if(region!=null)
		region.setPosition(getParent().getX()+x, getParent().getY()+y);
	}
	
	@Override
	public void setSize(float width, float height) {
		if(region!=null)
		region.setSize(width, height);
		super.setSize(width, height);
	}
	
	@Override
	public void setTint(Color tint) {
		if(region!=null)
		region.setColor(tint);
		super.setTint(tint);
	}
	
	@Override
	public void setAngle(float degrees) {
		super.setAngle(degrees);
		if(region!=null)
		region.setRotation(getParent().getAngle()+degrees);
	}
	
	
	@Override
	public void draw(SpriteBatch batch) {
		if(!isVisible() || region == null)
			return;
		region.draw(batch);
		super.draw(batch);
	}

}
