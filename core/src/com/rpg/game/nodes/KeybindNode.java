package com.rpg.game.nodes;

import com.rpg.game.nodes.event.KeybindEvent;
import com.rpg.game.utils.Keybinds;

public class KeybindNode extends BasicNode{
	
	public KeybindNode(){
		super();
		setName("keybind_node");
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
	public void castKeyEvent(KeybindEvent event){
		getParent().alertAll(event);
	}
	
	@Override
	public void onAdded() {
		Keybinds.addKeyNode(this);
		super.onAdded();
	}
	
	@Override
	public void onRemove() {
		Keybinds.removeKeyNode(this);
		super.onRemove();
	}

}
