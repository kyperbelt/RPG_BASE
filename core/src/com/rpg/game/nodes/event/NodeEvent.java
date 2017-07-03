package com.rpg.game.nodes.event;

import com.rpg.game.nodes.GameNode;

public abstract class NodeEvent {
	
	private GameNode origin;
	protected Object[] args;
	
	public NodeEvent(GameNode origin,Object...args){
		this.origin = origin;
		this.args = args;
	}
	
	public GameNode getOrigin(){
		return origin;
	}
	
	public void setOrigin(GameNode node){
		this.origin = node;
	}
	
	public Object[] getArgs(){
		return args;
	}
	
	public void setArgs(Object...args){
		this.args = args;
	}
}
