package com.rpg.game.nodes.tween;

import com.badlogic.gdx.math.Interpolation;
import com.rpg.game.nodes.GameNode;
import com.rpg.game.utils.RpgUtils;

public class Sequence extends NodeTween {
	
	private NodeTween[] nodeTweens;
	private int current_tween;
	
	public Sequence(NodeTween...nodeTweens) {
		this.nodeTweens = nodeTweens;
		current_tween = 0;
	}
	
	@Override
	public void setNode(GameNode node) {
		for (int i = 0; i < nodeTweens.length; i++) {
			nodeTweens[i].setNode(node);
		}
		super.setNode(node);
	}
	
	@Override
	public void reset() {
		for (int i = 0; i < nodeTweens.length; i++) {
			nodeTweens[i].reset();
		}
		current_tween = 0;
		super.reset();
	}
	
	@Override
	public void setEquation(Interpolation equation) {
		for (int i = 0; i < nodeTweens.length; i++) {
			nodeTweens[i].setEquation(equation);
		}
		super.setEquation(equation);
	}

	@Override
	public void update(float delta) {
		if(nodeTweens.length == 0)
			RpgUtils.logError("Sequence cannot be empty.");
		nodeTweens[current_tween].update(delta);
		
		if(nodeTweens[current_tween].isFinished()){
			current_tween++;
			if(current_tween >= nodeTweens.length)
				finish();
		}
	}

}
