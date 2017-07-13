package com.rpg.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class AnimationAtlas {
	
	//the texture atlas that will be creating the animations for this whole thing
	private TextureAtlas atlas;
	//the pooled animations 
	private ObjectMap<String, Array<PooledAnimation<Sprite>>> animations;
	public AnimationAtlas(TextureAtlas atlas) {
		this.atlas = atlas;
		animations = new ObjectMap<String, Array<PooledAnimation<Sprite>>>();
	}
	/**
	 * get an animation, use a pooled one if it exists
	 * @param animation
	 * @return
	 */
	public Animation<Sprite> getAnimation(String animation){
		if(!animations.containsKey(animation)){
			animations.put(animation, new Array<PooledAnimation<Sprite>>());
		}
		if(animations.get(animation).size == 0){
			PooledAnimation<Sprite> anim = new PooledAnimation<Sprite>(.25f, atlas.createSprites(animation));
			anim.name = animation;
			return anim;
		}else{
			return animations.get(animation).pop();
		}
	}
	
	/**
	 * return an animation to the pool
	 * @param anim
	 */
	public void depositAnimation(PooledAnimation<Sprite> anim){
		if(!animations.containsKey(anim.name)){
			animations.put(anim.name, new Array<PooledAnimation<Sprite>>());
		}
		animations.get(anim.name).add(anim);
	}

}
