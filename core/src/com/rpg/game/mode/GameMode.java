package com.rpg.game.mode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rpg.game.Configuration;
import com.rpg.game.assets.GameAssets;
import com.rpg.game.nodes.CameraNode;
import com.rpg.game.nodes.CullNode;
import com.rpg.game.nodes.SpriteNode;
import com.rpg.game.nodes.TweenNode;
import com.rpg.game.nodes.YSortNode;
import com.rpg.game.nodes.kfa.KeyFrame;
import com.rpg.game.nodes.kfa.KeyFrameNode;
import com.rpg.game.nodes.kfa.NodeTransform;
import com.rpg.game.utils.Keybinds;
import com.rpg.game.utils.PhysWorld;

public class GameMode extends BaseMode{
	
	CameraNode camnode;
	SpriteNode sprite,sprite2;
	YSortNode ysort;
	TweenNode tween;
	CullNode cull;
	KeyFrameNode kfn;

	@Override
	public void load(GameAssets loader) {
		loader.loadTexture("badlogic.jpg");
		loader.loadAtlas("image/character.pack");
	}

	@Override
	public void create() {
		if(isCreated())
			return;
		
		camnode = new CameraNode(new FitViewport(Configuration.WIDTH, Configuration.HEIGHT));
		
		TextureAtlas atlas = assets().getAtlas("image/character.pack");
		
		SpriteNode down_walk = new SpriteNode(null);
		down_walk.setName("down_walk");
		down_walk.setSize(48, 48);
		SpriteNode down_leg = new SpriteNode(null);
		down_leg.setName("down_leg");
		SpriteNode down_boot = new SpriteNode(null);
		down_boot.setName("down_boot");
		SpriteNode down_chest = new SpriteNode(null);
		down_chest.setName("down_chest");
		
		Array<SpriteNode> walk_nodes = new Array<SpriteNode>();
		walk_nodes.add(down_walk);
		walk_nodes.add(down_leg);
		walk_nodes.add(down_boot);
		walk_nodes.add(down_chest);
		
		
		NodeTransform down_walk_t1 = new NodeTransform();
		down_walk_t1.region = atlas.findRegion("male_walk_down", 1);
		NodeTransform down_walk_t2 = new NodeTransform();
		down_walk_t2.region = atlas.findRegion("male_walk_down", 1);
		NodeTransform down_walk_t3 = new NodeTransform();
		down_walk_t3.region = atlas.findRegion("male_walk_down", 2);
		NodeTransform down_walk_t4 = new NodeTransform();
		down_walk_t4.region = atlas.findRegion("male_walk_down", 3);
		
		NodeTransform down_leg_t1 = new NodeTransform();
		down_leg_t1.region = atlas.findRegion("legs_01_down");
		down_leg_t1.x = 15f;
		down_leg_t1.y = 2f;
		NodeTransform down_boot_t1 = new NodeTransform();
		down_boot_t1.region = atlas.findRegion("boot_01_down");
		down_boot_t1.x = 14f;
		down_boot_t1.y = 1f;
		
		NodeTransform down_chest_t1 = new NodeTransform();
		down_chest_t1.region = atlas.findRegion("chest_01_down");
		down_chest_t1.x = 6.2f;
		down_chest_t1.y = 9;
		
		KeyFrame frame_1 = new KeyFrame();
		frame_1.addTransform("down_walk", down_walk_t1);
		frame_1.addTransform("down_leg", down_leg_t1);
		frame_1.addTransform("down_boot", down_boot_t1);
		frame_1.addTransform("down_chest", down_chest_t1);
		
		Array<KeyFrame> frames = new Array<KeyFrame>();
		frames.add(frame_1);
		
		kfn = new KeyFrameNode(walk_nodes, frames);
		//kfn.setSize(48,48);

		ysort = new YSortNode();
		ysort.addChild(kfn);
		camnode.addChild(ysort);
		//sprite.setAlpha(.0f);
//		tween = new TweenNode(new Sequence(new Parallel(new ScaleBy(2f,2f),new FadeIn(2f)),
//				new Parallel(new ScaleBy(-2f,2f),new FadeOut(2f))
//				));
//		tween.getTween().setEquation(Interpolation.linear);
//		sprite.addChild(tween);
		cull = new CullNode(camnode);
		ysort.addChild(cull);
		root().addChild(camnode);
		camnode.setZoom(.6f);
	}

	@Override
	public void update(float delta) {
		root().update(delta);
		kfn.setPosition(kfn.getX(),kfn.getY()-(100*delta));
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.begin();
		root().draw(batch);
		batch.end();
		if(Configuration.DEBUG)
			PhysWorld.render(camnode);
	}
	
	@Override
	public void remove() {
		
	}

}
