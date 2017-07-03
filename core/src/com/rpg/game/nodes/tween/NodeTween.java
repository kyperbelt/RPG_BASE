package com.rpg.game.nodes.tween;

import com.badlogic.gdx.math.Interpolation;
import com.rpg.game.nodes.GameNode;

public abstract class NodeTween {
	
	//if the tween is finished
	private boolean finished;
	//node to apply tween to
	private GameNode node;
	//time to track tween 
	private float elapsed;
	//how to tween
	private Interpolation equation;
	//duration of tween
	private float duration;
	
	public NodeTween(){
		finished = false;
		elapsed = 0;
		equation = Interpolation.linear;
		duration = 1f;
	}
	/**
	 * set the equation to use for this tween
	 */
	public void setEquation(Interpolation equation){
		this.equation = equation;
	}
	/**
	 * get the equation being used by this tween
	 */
	public Interpolation getEquation(){
		return equation;
	}
	/**
	 * reset the tween for pooling purposes or repeat
	 */
	public void reset(){
		finished = false;
		setNode(null);
		setElapsed(0);
	}
	/**
	 * set the duration of this tween
	 * @param duration
	 */
	public void setDuration(float duration){
		this.duration = duration;
	}
	public float getDuration(){
		return duration;
	}
	/**
	 * set the node to apply this tween to
	 * @param node
	 */
	public void setNode(GameNode node){
		this.node = node;
	}
	/**
	 * get the node this tween applies to
	 * @return
	 */
	public GameNode getNode(){
		return node;
	}
	/**
	 * return true if this tween is finished
	 * @return
	 */
	public boolean isFinished(){
		return finished;
	}
	/**
	 * finish the tween
	 */
	public void finish(){
		finished = true;
	}
	/**
	 * set the elapsed time to update the tween
	 * @param elapsed
	 */
	public void setElapsed(float elapsed){
		this.elapsed = elapsed;
	}
	/**
	 * apply the delta to the current elapsed time
	 * @param delta
	 */
	public void applyDelta(float delta){
		elapsed+=delta;
	}
	/**
	 * get the current elapsed time for this node
	 * @return
	 */
	public float getElapsed(){
		return elapsed;
	}
	/**
	 * update this tween
	 * @param delta
	 */
	public abstract void update(float delta);
	
}
