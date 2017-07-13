package com.rpg.game.nodes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.rpg.game.nodes.event.AnimationEvent;
import com.rpg.game.utils.RpgUtils;

public class AnimationNode extends BasicNode{
	
	private Animation<Sprite> animation;
	private boolean finish_sent;
	private float elapsed;
	private AnimationEvent event;
	
	public AnimationNode(Animation<Sprite> animation){
		this.animation = animation;
		elapsed = 0;
		event = new AnimationEvent(this, AnimationEvent.FINISHED);
	}
	
	public void setPlayMode(PlayMode playmode){
		this.animation.setPlayMode(playmode);
	}
	
	public Animation<Sprite> getAnim(){
		return animation;
	}
	
	public TextureRegion getFirst(){
		return animation.getKeyFrames()[0];
	}
	
	public void setFrameDuration(float duration){
		animation.setFrameDuration(duration);
	}
	
	public float getElapsed(){
		return elapsed;
	}
	
	public void setElapsed(float elapsed){
		this.elapsed = elapsed;
	}
	
	@Override
	public void update(float delta) {
		if(getParent()==NULL||!(getParent() instanceof SpriteNode)){
			return;
		}
		elapsed+=delta;
		SpriteNode p = (SpriteNode) getParent();
		if(elapsed>=animation.getAnimationDuration()&&!finish_sent){
			getParent().alertAll(event);
			finish_sent = true;
		}else if(elapsed > animation.getAnimationDuration()*2f && (animation.getPlayMode()==PlayMode.LOOP||animation.getPlayMode() == PlayMode.LOOP_PINGPONG))
			elapsed = elapsed - animation.getAnimationDuration()*2f;
		
		p.setTextureRegion(animation.getKeyFrame(elapsed));
		super.update(delta);
	}
	
	@Override
	public void onAdded() {
		finish_sent = false;
		if(!(getParent() instanceof SpriteNode)){
			Array<GameNode> children = getParent().getChildren();
			for (int i = 0; i < children.size; i++) {
				//TODO: attach to first sprite node it finds?
			}
			RpgUtils.logError("Animation nodes must be added to SpriteNodes in order to function properly.");
		}
		elapsed = 0;
		super.onAdded();
	}

}
