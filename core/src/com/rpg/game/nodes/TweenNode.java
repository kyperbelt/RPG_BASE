package com.rpg.game.nodes;

import com.rpg.game.nodes.tween.NodeTween;

/**
 * basic tweening node too add tween functionality 
 * to the given node
 * @author kyperbelt
 *
 */
public class TweenNode extends BasicNode{
	
	private NodeTween tween;
	private boolean paused;
	private boolean started;
	private GameNode tween_target;
	
	public TweenNode(NodeTween tween){
		setTween(tween);
		paused = false;
		started = false;
		setName("tween_node");
	}
//	public void setTweenTarget(GameNode target){
//		this.tween_target = target;
//		if(tween_target == null)
//			tween_target = getParent();
//	}
	/**
	 * set the tween to use for this node
	 * try not to use this too much on the same tween node since
	 * the tweens are not pooled
	 * TODO:add pooling to tweens
	 * @param tween
	 */
	public void setTween(NodeTween tween){
		this.tween = tween;
	}
	/**
	 * get the tween for this node
	 * @return
	 */
	public NodeTween getTween(){
		return tween;
	}
	/**
	 * start the tween
	 */
	public void start(){
		paused = false;
		tween.reset();
		tween.setNode(tween_target);
		started = true;
	}
	/**
	 * pause the tween
	 */
	public void pause(){
		paused = true;
	}
	/**
	 * resume the tween
	 */
	public void resume(){
		paused = false;
	}
	/**
	 * stop the tween
	 */
	public void stop(){
		started = false;
	}
	/**
	 * check if tween has started
	 */
	public boolean isRunning(){
		return(started&&!paused);
	}
	
	public boolean isPaused(){
		return paused;
	}
	@Override
	public void update(float delta) {
		if(started&&!paused){
			tween.update(delta);
			if(tween.isFinished()){
				started = false;
			}
		}
		super.update(delta);
	}
	
	@Override
	public void onAdded() {
		tween_target = getParent();
		super.onAdded();
	}
}
