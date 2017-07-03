package com.rpg.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rpg.game.Configuration;
import com.rpg.game.nodes.BasicNode;
import com.rpg.game.nodes.CameraNode;
import com.rpg.game.nodes.GameNode;
import com.rpg.game.nodes.PhysicsNode;
import com.rpg.game.nodes.event.ContactEvent;

public class PhysWorld {
	
	
	private World world;
	private float accumulator = 0;
	private Box2DDebugRenderer debug;
	private Array<PhysicsNode> removequeue;
	
	private PhysWorld(){
		world = new World(new Vector2(Configuration.GRAVITY_X,Configuration.GRAVITY_Y), true);
		debug = new Box2DDebugRenderer();
		removequeue = new Array<PhysicsNode>();
		world.setContactListener(new CollisionDetectionMechanism());
	}
	
	private void debug_render(CameraNode camnode){
		debug.render(world, camnode.getCam().combined.cpy().scl(Configuration.PIXELS_PER_METER));
	}
	

	private void doPhysicsStep(float deltaTime) {
	    // fixed time step
	    // max frame time to avoid spiral of death (on slow devices)
	    float frameTime = Math.min(deltaTime, 0.25f);
	    accumulator += frameTime;
	    while (accumulator >= Configuration.WORLD_STEP) {
	        world.step(Configuration.WORLD_STEP, Configuration.VELOCITY_ITERATIONS, Configuration.POSITION_ITERATIONS);
	        accumulator -= Configuration.WORLD_STEP;
	    }
	}
	
	private void removeBodies(){
		for (int i = 0; i < removequeue.size; i++) {
			world.destroyBody(removequeue.get(i).getBody());
			removequeue.removeIndex(i);
		}
	}
	
	private void add_PhysNode(PhysicsNode node){
		BodyDef bdef = new BodyDef();
		bdef.type = node.getType();
		bdef.gravityScale = node.getGravityScale();
		bdef.fixedRotation = true;
		bdef.allowSleep = true;
		bdef.position.set(new Vector2(node.worldX(),node.worldY()).scl(1f/Configuration.PIXELS_PER_METER));
		node.setBody(world.createBody(bdef));
		PolygonShape shape = new PolygonShape();
		shape.set(node.getRect());
		FixtureDef fdef = new FixtureDef();
		fdef.density = .5f;
		fdef.friction = .1f;
		fdef.isSensor = false;
		fdef.restitution = 1f;
		fdef.shape = shape;
		Fixture f = node.getBody().createFixture(fdef);
		f.setUserData(node);
		shape.dispose();
		
	}
	
	private void remove_PhysNode(PhysicsNode node){
		removequeue.add(node);
	}
	
	private static PhysWorld pw;
	
	private static PhysWorld getWorld(){
		if(pw==null){
			pw = new PhysWorld();
		}
		return pw;
	}
	
	public static void render(CameraNode camnode){
		getWorld().debug_render(camnode);
	}
	
	public static void update(float delta){
		getWorld().doPhysicsStep(delta);
		getWorld().removeBodies();
	}
	
	public static void addPhysNode(PhysicsNode node){
		getWorld().add_PhysNode(node);
		
	}
	
	public static void removePhysNode(PhysicsNode node){
		getWorld().remove_PhysNode(node);
	}
	
	public class CollisionDetectionMechanism implements ContactListener{

		ContactEvent event1;
		ContactEvent event2;
		
		
		public CollisionDetectionMechanism() {
			event1 = new ContactEvent(BasicNode.NULL, new Object[1]);
			event2 = new ContactEvent(BasicNode.NULL, new Object[1]);
		}
		@Override
		public void beginContact(Contact contact) {
			GameNode node1 = (GameNode) contact.getFixtureA().getUserData();
			GameNode node2 = (GameNode) contact.getFixtureB().getUserData();
			event1.setOrigin(node2);
			event1.setType(ContactEvent.BEGIN);
			node1.alertAll(event1);
			event2.setOrigin(node1);
			event2.setType(ContactEvent.BEGIN);
			node2.alertAll(event2);
			
		}

		@Override
		public void endContact(Contact contact) {
			GameNode node1 = (GameNode) contact.getFixtureA().getUserData();
			GameNode node2 = (GameNode) contact.getFixtureB().getUserData();
			event1.setOrigin(node2);
			event1.setType(ContactEvent.END);
			node1.alertAll(event1);
			event2.setOrigin(node1);
			event2.setType(ContactEvent.END);
			node2.alertAll(event2);
		}

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {
			
		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {
			
		}
		
	}
	

}
