package com.rpg.game.nodes.kfa;

import com.badlogic.gdx.utils.Array;
import com.rpg.game.nodes.BasicNode;
import com.rpg.game.nodes.SpriteNode;

public class KeyFrameNode extends BasicNode{
	
	//holds a set of keyframes and "limb" spritenodes
	
	private Array<KeyFrame> frames;
	private float total_duration;
	private float elapsed;
	private int current_frame;
	
	public KeyFrameNode(Array<SpriteNode> limbs,Array<KeyFrame> f){
		super();
		setName("keyframe_node");
		this.frames = f;
		total_duration = 0;
		elapsed = 0;
		current_frame = 0;
		for (int i = 0; i < frames.size; i++) {
			total_duration+=frames.get(i).getLastFor();
			frames.get(i).setKFN(this);
		}
		for (int i = 0; i < limbs.size; i++) {
			addChild(limbs.get(i));
		}
	}
	
	public Array<KeyFrame> getFrames(){
		return frames;
	}
	
	public void setFrames(Array<KeyFrame> frames){
		this.frames = frames;
	}
	
	public float totalDuration(){
		return total_duration;
	}
	
	@Override
	public float getX() {
		return getParent().getX()+getX();
	}
	
	public float getY(){
		return getParent().getY()+getY();
	}
	
	@Override
	public void update(float delta) {
		elapsed+=delta;
		if(elapsed >= frames.get(current_frame).getLastFor()){
			elapsed=elapsed-frames.get(current_frame).getLastFor();
			current_frame++;
			if(current_frame >= frames.size)
				current_frame=0;
		}
		frames.get(current_frame).applyTransforms();
		super.update(delta);
	}

}
