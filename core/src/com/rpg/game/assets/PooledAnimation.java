package com.rpg.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

public class PooledAnimation<T> extends Animation<T>{
	
	public String name;
	
	public PooledAnimation(float frameDuration, Array<? extends T> keyFrames) {
		super(frameDuration, keyFrames);
	}

}
