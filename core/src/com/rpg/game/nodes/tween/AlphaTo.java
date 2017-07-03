package com.rpg.game.nodes.tween;

import com.rpg.game.nodes.GameNode;

public class AlphaTo extends NodeTween{

	private float target_alpha;
	private float start_alpha;
	
	public AlphaTo(float alpha,float duration){
		this.target_alpha = alpha;
		this.start_alpha = 0;
		setDuration(duration);
	}
	
	@Override
	public void setNode(GameNode node) {
		if(node!=null){
			start_alpha = node.getAlpha();
		}
		super.setNode(node);
	}
	
	@Override
	public void reset() {
		if(getNode()!=null)
			getNode().setAlpha(start_alpha);
		super.reset();
	}

	@Override
	public void update(float delta) {
		applyDelta(delta);
		GameNode node = getNode();
		float percent = getEquation().apply(Math.min(getElapsed()/getDuration(),1f));
		
		node.setAlpha(start_alpha+(target_alpha-start_alpha)*percent);
		
		if(getElapsed() >=getDuration() && percent == 1f)
			finish();
	}

}
