package com.rpg.game.nodes.tween;

import com.rpg.game.nodes.GameNode;

public class MoveTo extends NodeTween{
	private float destx;
	private float desty;
	private float startx;
	private float starty;
	
	public MoveTo(float x,float y,float duration) {
		this.startx = 0;
		this.starty = 0;
		this.destx = x;
		this.desty = y;
		setDuration(duration);
	}
	
	public void setNode(GameNode node) {
		if(node!=null){
			startx = node.getX();
			starty = node.getY();
		}
		super.setNode(node);
	}
	
	public void setDestination(float x,float y){
		if(getNode()!=null){
			startx = getNode().getX();
			starty = getNode().getY();
		}
		destx = x;
		desty = y;
	}
	
	@Override
	public void reset() {
		if(getNode()!=null)
			getNode().setPosition(startx, starty);
		super.reset();
	}

	@Override
	public void update(float delta) {
		applyDelta(delta);
		GameNode node = getNode();
		
		float percent = getEquation().apply(Math.min(getElapsed()/getDuration(),1f));
		
		node.setPosition(startx+(destx-startx)*percent,starty+(desty-starty)*percent);
		if(getElapsed() >=getDuration() && percent == 1f)
			finish();
	}
}
