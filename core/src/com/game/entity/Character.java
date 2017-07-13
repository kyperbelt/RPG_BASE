package com.game.entity;

import com.rpg.game.nodes.BasicNode;

/**
 * character node with several sync aniamtions
 * @author kyperbelt
 *
 */
public class Character extends BasicNode{
	
	
	public Character() {
		super();
		setName("character");
	}
	
	@Override
	public void setName(String name) {
		super.setName("character@"+name);
	}

}
