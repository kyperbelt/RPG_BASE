package com.rpg.game.nodes.tween;

import com.rpg.game.nodes.GameNode;

public class MoveBy extends NodeTween{
	
	private float xmove;
	private float ymove;
	private float xamount;
	private float yamount;
	
	public MoveBy(float x,float y,float duration) {
		this.xmove = x;
		this.xamount = 0;
		this.yamount = 0;
		this.ymove = y;
		setDuration(duration);
	}
	
	public void setNode(GameNode node) {
		super.setNode(node);
	}
	
	@Override
	public void reset() {
		xamount = 0;
		yamount = 0;
		super.reset();
	}

	@Override
	public void update(float delta) {
		applyDelta(delta);
		GameNode node = getNode();
		
		float percent = getEquation().apply(Math.min(getElapsed()/getDuration(),1f));
		
		node.setPosition(node.getX()+(xmove*percent-xamount), node.getY()+(ymove*percent-yamount));
		
		xamount = xmove*percent;
		yamount = ymove*percent;
		
		if(getElapsed() >=getDuration() && percent == 1f)
			finish();
	}

}
