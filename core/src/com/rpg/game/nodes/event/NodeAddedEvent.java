package com.rpg.game.nodes.event;

import com.rpg.game.nodes.GameNode;

public class NodeAddedEvent extends NodeEvent{

	public NodeAddedEvent(GameNode origin, Object[] args) {
		super(origin, args);
	}

}
