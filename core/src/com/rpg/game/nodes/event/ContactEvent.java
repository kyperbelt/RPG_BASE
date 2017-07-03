package com.rpg.game.nodes.event;

import com.rpg.game.nodes.GameNode;

public class ContactEvent extends NodeEvent{
	
	public static final int BEGIN = 0;
	public static final int END = 1;
	

	public ContactEvent(GameNode origin, Object[] args) {
		super(origin, args);
	}
	
	public void setType(int type){
		args[0] = type;
	}
	
	public int getType(){
		return (Integer) args[0];
	}

}
