package com.rpg.game.nodes.event;

import com.rpg.game.nodes.GameNode;

public class AnimationEvent extends NodeEvent{
	
	public static final String FINISHED = "finished";
	
	public AnimationEvent(GameNode origin, String state) {
		super(origin, new Object[1]);
		setState(state);
	}
	
	public void setState(String state){
		this.args[0] = state;
	}

	public AnimationEvent getAnimation(){
		return (AnimationEvent) getOrigin();
	}
	
	public String getState(){
		return (String) getArgs()[0];
	}
}
