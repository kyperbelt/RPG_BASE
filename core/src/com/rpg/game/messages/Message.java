package com.rpg.game.messages;

import com.badlogic.gdx.utils.Array;

public class Message{
	
	public static final Message SCREEN_RESIZE = new Message("SCREEN_RESIZED");
	
	public String name;
	public Array<Object>args;
	public boolean handled;
	
	public Message(String name){
		this.name = name;
		args = new Array<Object>();
		handled = false;
	}
	
	public void reset(){
		args.clear();
		handled = false;
	}
	
	@Override
	public String toString() {
		return name+" ["+args.toString("|")+"]";
	}
	
	public void handle(){
		handled = true;
	}

}
