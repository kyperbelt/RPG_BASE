package com.rpg.game.nodes.tween;

import com.rpg.game.nodes.GameNode;

public class ScaleBy extends NodeTween{

	private float amount;//amount to scale by
	private float count; //amount already added
	
	public ScaleBy(float amount,float duration){
		this.amount = amount;
		setDuration(duration);
	}
	
	@Override
	public void reset() {
		count = 0;
		super.reset();
	}
	
	@Override
	public void update(float delta) {
		applyDelta(delta);
		GameNode node = getNode();
		
		float percent = getEquation().apply(Math.min(getElapsed()/getDuration(), 1f));
		
		float m = amount*percent;
		
		node.scale(m-count);
		
		count = m;
		
		if(getElapsed() >= getDuration()&& percent == 1f)
			finish();
	}

}
