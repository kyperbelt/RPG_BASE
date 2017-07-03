package com.rpg.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AtlasWrapper {
	
	private TextureAtlas atlas;
	
	public AtlasWrapper(TextureAtlas atlas){
		setAtlas(atlas);
	}
	
	public void setAtlas(TextureAtlas atlas){
		this.atlas = atlas;
	}

	public TextureRegion findRegion(String name){
		TextureRegion region = atlas.findRegion(name);
		if(region == null){
			return atlas.findRegion("noregion");
		}
		return region;
	}
	
	public Array<AtlasRegion> findRegions(String name){
		if(findRegion(name)==null){
			return new Array<AtlasRegion>(new AtlasRegion[]{atlas.findRegion("noregion")});
		}
		return atlas.findRegions(name);
	}
	
	public TextureAtlas getAtlas(){
		return atlas;
	}
}
