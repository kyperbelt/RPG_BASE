package com.rpg.game.nodes.tween;

import com.badlogic.gdx.math.Interpolation;
import com.rpg.game.nodes.GameNode;

public class Parallel extends NodeTween{
	
	private NodeTween[] nodeTweens;
	
	public Parallel(NodeTween...nodeTweens){
		this.nodeTweens = nodeTweens;
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
		boolean finished = true;
		for (int i = 0; i < nodeTweens.length; i++) {
			nodeTweens[i].update(delta);
			finished = nodeTweens[i].isFinished();
		}
		
		if(finished)
			finish();
	}

}
