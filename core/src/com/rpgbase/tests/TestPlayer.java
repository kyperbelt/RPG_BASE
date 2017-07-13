package com.rpgbase.tests;

import com.badlogic.gdx.Gdx;
import com.rpg.game.assets.AnimationAtlas;
import com.rpg.game.nodes.BasicNode;
import com.rpg.game.nodes.KeybindNode;
import com.rpg.game.nodes.SyncAnimationNode;
import com.rpg.game.nodes.event.KeybindEvent;
import com.rpg.game.nodes.event.NodeEvent;
import com.rpg.game.utils.Keybinds;

public class TestPlayer extends BasicNode{
	
	SyncAnimationNode down,up,left,right;
	AnimationAtlas atlas;
	KeybindNode keybinds;
	
	SyncAnimationNode current;
	
	public TestPlayer(AnimationAtlas atlas) {
		super();
		setName("test_player");
		this.atlas = atlas;
		down = SyncAnimationNode.loadAnimation(atlas, Gdx.files.internal("animations/male_walk_down.anim"));
		up = SyncAnimationNode.loadAnimation(atlas, Gdx.files.internal("animations/male_walk_up.anim"));
		left = SyncAnimationNode.loadAnimation(atlas, Gdx.files.internal("animations/male_walk_left.anim"));
		right = SyncAnimationNode.loadAnimation(atlas, Gdx.files.internal("animations/male_walk_right.anim"));
		setSize(48, 48);
		//setScale(2f);
		//setAlpha(.5f);
		current = down;
		keybinds = new KeybindNode();
		addChild(down);
		addChild(keybinds);

	}
	
	@Override
	public boolean onAlert(NodeEvent e) {
		if(e instanceof KeybindEvent){
			KeybindEvent key = (KeybindEvent) e;
			if(key.getBind().equals(Keybinds.DOWN)){
				current.remove();
				current = down;
				addChild(current);
				return true;
			}
			if(key.getBind().equals(Keybinds.UP)){
				current.remove();
				current = up;
				addChild(current);
				return true;
			}
			if(key.getBind().equals(Keybinds.LEFT)){
				current.remove();
				current = left;
				addChild(current);
				return true;
			}
			if(key.getBind().equals(Keybinds.RIGHT)){
				current.remove();
				current = right;
				addChild(current);
				return true;
			}

		}
		return super.onAlert(e);
	}

}
