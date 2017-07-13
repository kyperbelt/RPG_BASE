package com.rpg.game.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.rpg.game.assets.AnimationAtlas;
import com.rpg.game.assets.PooledAnimation;
import com.rpg.game.utils.RpgUtils;

/**
 * allows you to make paper doll animations that are synced with eachother
 * @author kyperbelt
 *
 */
public class SyncAnimationNode extends BasicNode{
	
	private float elapsed;
	private float frameDuration;
	private PlayMode playmode;
	private AnimationAtlas atlas;
	
	ObjectMap<String, SpriteNode> limbs;

	public SyncAnimationNode() {
		super();
		setName("sync_animation");
		elapsed = 0;
		frameDuration = .25f;
		limbs = new ObjectMap<String, SpriteNode>();
		playmode = PlayMode.LOOP_PINGPONG;
	}
	
	public void setPlayMode(PlayMode mode){
		this.playmode = mode;
	}
	
	public PlayMode getPlayMode(){
		return playmode;
	}
	
	public void setFrameDuration(float duration){
		this.frameDuration = duration;
	}
	
	public void setAnimationAtlas(AnimationAtlas atlas){
		this.atlas = atlas;
	}
	
	public float getFrameDuration(){
		return frameDuration;
	}
	/**
	 * add a limb with the given name to this node in order
	 * to animate it
	 * @param limb
	 */
	public void addLimb(String limb){
		if(limbs.containsKey(limb)){
			RpgUtils.logError("cannot have two of the same limbs in a single SyncAnimationNode.");
			return;
		}
		SpriteNode n = new SpriteNode(null);
		n.setName(limb);
		limbs.put(limb, n);
		addChild(n);
		
	}
	public Array<SpriteNode> getLimbs(){
		Array<SpriteNode> ls = new Array<SpriteNode>();;
		for (int i = 0; i < getChildren().size; i++) {
			GameNode n = getChildren().get(i);
			if(n instanceof SpriteNode){
				ls.add((SpriteNode) n);
			}
		}
		return ls;
	}
	/**
	 * set the index of an already added limb to change the update/draw order
	 * @param limb
	 * @param index
	 */
	public void setIndex(String limb,int index){
		SpriteNode n = getLimb(limb);
		getChildren().set(index, n);
	}
	/**
	 *  get the limb with the given name
	 * @param limb
	 * @return
	 */
	public SpriteNode getLimb(String limb){
		return limbs.get(limb);
	}
	/**
	 * set the animation for the given limb
	 * if the previous animation was pooled we return it to 
	 * the given animation atlas for later use.
	 * @param limb
	 * @param node
	 */
	public void setAnimation(String limb,AnimationNode node){
		SpriteNode n = getLimb(limb);
		if(n==null)
			return;
		node.setPlayMode(playmode);
		if(n.getChildren().size > 0){
			AnimationNode an = (AnimationNode) n.getChildren().first();
			if(an.getAnim() instanceof PooledAnimation){
				if(atlas!=null)
					atlas.depositAnimation((PooledAnimation<Sprite>) an.getAnim());
			}
		}
		n.clearChildren();
		n.addChild(node);
	}
	
	public AnimationNode getAnimation(String limb){
		AnimationNode a = (AnimationNode) getLimb(limb).getChildren().first();
		return a;
	}
	
	@Override
	public void update(float delta) {
		//update all limbs animation deltas
		for (int i = 0; i < getChildren().size; i++) {
			AnimationNode a = (AnimationNode) getChildren().get(i).getChildren().first();
			if(a  != null){
				a.setFrameDuration(frameDuration);
				a.setElapsed(elapsed);
			}
		}
		elapsed+=delta;
		if(elapsed>=10000f){
			elapsed = 0;
		}
		super.update(delta);
	}
	
	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return getParent().getX()+super.getX();
	}
	
	@Override
	public float getY() {
		return getParent().getY()+super.getY();
	}
	
	@Override
	public float getXScale() {
		return getParent().getXScale()*super.getXScale();
	}
	
	@Override
	public float getYScale() {
		return getParent().getYScale()*super.getYScale();
	}
	
	@Override
	public void addChild(GameNode node) {
		if(!(node instanceof SpriteNode))
			RpgUtils.logError("You should not add non-SpriteNode to SyncAnimationNode. Use addLimb() to add new limbs to the node");
		super.addChild(node);
	}
	
	public static SyncAnimationNode loadAnimation(AnimationAtlas atlas,FileHandle handle){
		SyncAnimationNode sanim = new SyncAnimationNode();
		JsonReader reader = new JsonReader();
		JsonValue root = reader.parse(handle);
		
		sanim.setName(root.get("name").asString());
		sanim.setFrameDuration(root.getFloat("frameDuration"));
		
		JsonValue limbs = root.get("limbs");
		for (int i = 0; i < limbs.size; i++) {
			JsonValue limb = limbs.get(i);
			SpriteNode n = new SpriteNode(null);
			n.setName(limb.name);
			String res = limb.getString("regions", "noregion");
			AnimationNode an = new AnimationNode(atlas.getAnimation(res));
			n.setPosition(limb.getFloat("xpos"), limb.getInt("ypos"));
			n.setOrigin(limb.getFloat("xorigin"), limb.getFloat("yorigin"));
			n.setSize(limb.getFloat("width"), limb.getFloat("height"));
			n.setScale(limb.getFloat("xscale"), limb.getFloat("yscale"));
			n.setAngle(limb.getFloat("angle"));
			n.setAlpha(limb.getFloat("alpha"));
			n.setTint(new Color((int) Long.parseLong(limb.getString("tint"), 16)));
			
			sanim.addLimb(n.getName());
			sanim.setAnimation(n.getName(), an);
			
		}
		RpgUtils.logInfo("loaded:"+sanim.getName());
		return sanim;
	}


}
