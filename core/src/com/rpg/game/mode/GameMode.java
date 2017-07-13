package com.rpg.game.mode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rpg.game.Configuration;
import com.rpg.game.assets.AnimationAtlas;
import com.rpg.game.assets.GameAssets;
import com.rpg.game.nodes.CameraNode;
import com.rpg.game.nodes.CullNode;
import com.rpg.game.nodes.TweenNode;
import com.rpg.game.nodes.YSortNode;
import com.rpg.game.utils.PhysWorld;
import com.rpgbase.tests.TestPlayer;

public class GameMode extends BaseMode{
	
	CameraNode camnode;
	YSortNode ysort;
	TweenNode tween;
	CullNode cull;
	TestPlayer test;
	
	AnimationAtlas atlas;
	

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
		
		atlas = new AnimationAtlas(assets().getAtlas("image/character.pack"));
		test = new TestPlayer(atlas);
		

		ysort = new YSortNode();
		camnode.addChild(ysort);
		cull = new CullNode(camnode);
		ysort.addChild(cull);
		ysort.addChild(test);
		root().addChild(camnode);
		camnode.setZoom(.6f);
		camnode.setFollow(test);
	}

	@Override
	public void update(float delta) {
		root().update(delta);
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
