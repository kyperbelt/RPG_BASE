package com.rpg.game.messages;

public abstract class MessageListener {
	
	protected int id;
	private MessageManager manager;
	
	protected void setManager(MessageManager m){
		this.manager = m;
	}
	
	public void remove(){
		manager.removeListener(this);
	}
	
	public abstract void handle(Message message);
	
	
}
