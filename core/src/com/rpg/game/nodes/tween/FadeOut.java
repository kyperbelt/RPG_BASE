package com.rpg.game.nodes.tween;

import com.rpg.game.nodes.GameNode;

public class FadeOut extends NodeTween{
	
	private float startalpha;
	public FadeOut(float duration){
		setDuration(duration);
		startalpha = 0;
	}
	
	@Override
	public void setNode(GameNode node) {
		if(node!=null){
			startalpha = node.getAlpha();
		}
		super.setNode(node);
	}
	
	@Override
	public void reset() {
		if(getNode()!=null){
			getNode().setAlpha(startalpha);
		}
		super.reset();
	}
	
	@Override
	public void update(float delta) {
		applyDelta(delta);
		float percent = getEquation().apply(Math.min(getElapsed()/getDuration(),1f));
		getNode().setAlpha(1f-percent);
		if(getElapsed() >=getDuration() && percent == 1f)
			finish();
	}

}
