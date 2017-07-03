package com.rpg.game.nodes.tween;

public class WaitFor extends NodeTween{

	public WaitFor(float duration){
		setDuration(duration);
	}
	
	@Override
	public void update(float delta) {
		applyDelta(delta);
		if(getElapsed() >= getDuration()){
			finish();
		}
	}

}
