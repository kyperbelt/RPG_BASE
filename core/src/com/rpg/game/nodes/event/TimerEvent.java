package com.rpg.game.nodes.event;

import com.rpg.game.nodes.GameNode;

public class TimerEvent extends NodeEvent{

	public TimerEvent(GameNode origin, float duration,int repeats ) {
		super(origin, new Object[2]);
		set(duration, repeats);
	}
	
	public void set(float duration,int repeats){
		args[0] = duration;
		args[1] = repeats;
	}
	
	public float getTime(){
		return (Float)getArgs()[0];
	}
	
	public int repeats(){
		return (Integer)getArgs()[1];
	}
	

	
	

}
