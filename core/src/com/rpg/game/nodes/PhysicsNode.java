package com.rpg.game.nodes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.rpg.game.Configuration;
import com.rpg.game.utils.PhysWorld;
import com.rpg.game.utils.RpgUtils;

/**
 * simple physics node to add physics to any parent node 
 * with a rectangle shape attached to it
 * @author kyperbelt
 *
 */
public class PhysicsNode extends BasicNode{
	
	private Body body;
	private RectangleNode rectnode;
	private float tempgrav_scale;
	private BodyDef.BodyType type;
	
	public PhysicsNode(BodyDef.BodyType type){
		super();
		this.type = type;
		setName("physics_node");
		tempgrav_scale = 1f;
	}
	
	public BodyType getType(){
		return type;
	}
	
	public float[] getRect(){
		float[] verts = rectnode.getRectangle().getTransformedVertices();
		for (int i = 0; i < verts.length; i++) {
			verts[i] = verts[i]/Configuration.PIXELS_PER_METER;
		}
		return verts;
	}
	
	public Body getBody(){
		return body;
	}
	
	public void setBody(Body body){
		this.body = body;
	}
	
	public void setGravityScale(float scale){
		if(body==null)
			tempgrav_scale = scale;
		else
			body.setGravityScale(scale);
	}
	
	public float getGravityScale(){
		if(body==null)
			return tempgrav_scale;
		else
			return body.getGravityScale();
	}
	
	public void tranform(float newx,float newy){
		body.setTransform(newx, newy, body.getAngle());
	}
	
	@Override
	public void update(float delta) {
		if(body!=null || getParent()!=BasicNode.NULL){
			Vector2 pos = body.getPosition().cpy().scl(Configuration.PIXELS_PER_METER);
			getParent().setPosition(pos.x, pos.y);
		}
		super.update(delta);
	}
	
	@Override
	public void onAdded() {
		GameNode shape = BasicNode.NULL;
		if(!(getParent() instanceof RectangleNode)){
			Array<GameNode> children = getParent().getChildren();
			for (int i = 0; i < children.size; i++) {
				if(children.get(i) instanceof RectangleNode){
					shape = children.get(i);
					break;
				}
			}
		}
		if(shape == BasicNode.NULL){
			RpgUtils.logError("PhysicsNode["+getName()+"] could not find Rectangle in ["+getParent().getName()+"].");
		}else{
			rectnode = (RectangleNode) shape;
			PhysWorld.addPhysNode(this);
		}
		super.onAdded();
	}
	
	@Override
	public void onRemove() {
		if(getParent()!=BasicNode.NULL){
			PhysWorld.removePhysNode(this);
		}
		super.onRemove();
	}

}
