package com.rpg.game.nodes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.rpg.game.nodes.event.AnimationEvent;
import com.rpg.game.utils.RpgUtils;

public class AnimationNode extends BasicNode{
	
	private Animation<TextureRegion> animation;
	private boolean finish_sent;
	private float elapsed;
	private AnimationEvent event;
	
	public AnimationNode(Animation<TextureRegion> animation){
		this.animation = animation;
		elapsed = 0;
		event = new AnimationEvent(this, AnimationEvent.FINISHED);
		setLooping(true);
	}
	
	public void setLooping(boolean loop){
		if(loop)
			animation.setPlayMode(PlayMode.LOOP);
		else
			animation.setPlayMode(PlayMode.NORMAL);
	}
	
	public Animation<TextureRegion> getAnim(){
		return animation;
	}
	
	public TextureRegion getFirst(){
		return animation.getKeyFrames()[0];
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
		}else if(elapsed > animation.getAnimationDuration()*2f && animation.getPlayMode()==PlayMode.LOOP)
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
