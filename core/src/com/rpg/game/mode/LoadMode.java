package com.rpg.game.mode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpg.game.assets.GameAssets;
import com.rpg.game.utils.RpgUtils;

public class LoadMode extends BaseMode{

	@Override
	public void load(GameAssets loader) {
		
	}

	@Override
	public void create() {
		assets().setCurrentMode(app().currentModeName());
		app().getCurrentMode().load(assets());
	}

	@Override
	public void update(float delta) {
		if(assets().loadNext()){
			app().setScreen(app().getCurrentMode());
			RpgUtils.logInfo("finished loading");
		}
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
