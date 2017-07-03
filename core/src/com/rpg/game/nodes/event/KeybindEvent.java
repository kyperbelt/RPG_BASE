package com.rpg.game.nodes.event;

import com.rpg.game.nodes.GameNode;

public class KeybindEvent extends NodeEvent{

	public KeybindEvent(GameNode origin, Object[] args) {
		super(origin, args);
	}
	
	public String getBind(){
		return (String) args[0];
	}
	
	public boolean justPressed(){
		return (Boolean) args[1];
	}
	
	public void setEvent(String bind,boolean once){
		args[0] = bind;
		args[1] = once;
	}

}
