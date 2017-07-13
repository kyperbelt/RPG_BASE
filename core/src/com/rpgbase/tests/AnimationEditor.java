package com.rpgbase.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.rpg.game.Configuration;
import com.rpg.game.assets.GameAssets;
import com.rpg.game.mode.BaseMode;
import com.rpg.game.nodes.AnimationNode;
import com.rpg.game.nodes.CameraNode;
import com.rpg.game.nodes.SpriteNode;
import com.rpg.game.nodes.SyncAnimationNode;
import com.rpg.game.utils.RpgUtils;

public class AnimationEditor extends BaseMode {

	// vars
	TextureAtlas character_atlas;
	Array<String> unique_animations;
	CameraNode cam;

	// ui
	// --AnimationNode
	ObjectMap<String, SyncAnimationNode> animations;
	ObjectMap<String, ObjectMap<String,String>> limb_animation_names;
	SyncAnimationNode current_animation;

	VisWindow main_window;
	VisSelectBox<String> animation_select, limb_select, regions_select;
	VisWindow createAnim, createLimb;
	VisTextButton addlimb, removelimb, addanim, removeanim, createanim, createlimb, save;
	VisTextField animnamefield, limbnamefield;

	@Override
	public void load(GameAssets loader) {
		VisUI.load();
		loader.loadAtlas("image/character.pack");
	}

	@Override
	public void create() {
		// load class vars
		cam = new CameraNode(new FitViewport(Configuration.WIDTH, Configuration.HEIGHT));
		cam.setZoom(.5f);
		character_atlas = assets().getAtlas("image/character.pack");
		//AtlasRegion r = character_atlas.findRegion("male_walk_down");
		//RpgUtils.logInfo("pw:"+r.packedWidth+" rw"+r.getRegionWidth());
		unique_animations = new Array<String>();
		limb_animation_names = new ObjectMap<String, ObjectMap<String,String>>();
		Array<AtlasRegion> regions = character_atlas.getRegions();
		for (int i = 0; i < regions.size; i++) {
			if (!unique_animations.contains(regions.get(i).name, false))
				unique_animations.add(regions.get(i).name);
		}

		animations = new ObjectMap<String, SyncAnimationNode>();
		loadAnimations();

		// change listener
		ChangeListener change = new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Actor a = event.getListenerActor();
				if (a == animation_select) {
					if (current_animation != null)
						current_animation.remove();
					current_animation = animations.get(animation_select.getSelected());
					cam.addChild(current_animation);
					Array<String> list = RpgUtils.getChildrenNames(current_animation);
					limb_select.setItems(list);
					if (list.size > 0)
						limb_select.setSelected(list.first());
				}
				if (a == limb_select) {
					if(limb_select.getItems().size>0){
						String r = limb_animation_names.get(current_animation.getName()).get(limb_select.getSelected());
						//RpgUtils.logInfo("SL REGION:"+r);
						if (r != null && !r.isEmpty()) {
							regions_select.setSelected(r);
						}
					}
				}
				if (a == regions_select) {
					String r = regions_select.getSelected();
					if(limb_select.getSelected()==null||limb_select.getSelected().isEmpty()){
					}else
					if (limb_animation_names.get(current_animation.getName()).containsKey(limb_select.getSelected())
							&& !limb_animation_names.get(current_animation.getName()).get(limb_select.getSelected()).equals(r)) {
						Animation<Sprite> anim = new Animation<Sprite>(current_animation.getFrameDuration(),
								character_atlas.createSprites(r));
						limb_animation_names.get(current_animation.getName()).put(limb_select.getSelected(), r);
						current_animation.setAnimation(limb_select.getSelected(), new AnimationNode(anim));
					}
				}

			}
		};

		// click listener
		ClickListener click = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Actor a = event.getListenerActor();
				if (a == addanim) {
					createAnim.setPosition(Gdx.graphics.getWidth() / 2 - createAnim.getWidth() / 2,
							Gdx.graphics.getHeight() / 2 - createAnim.getHeight() / 2);
					ui().addActor(createAnim);
				}
				if (a == addlimb) {
					createLimb.setPosition(Gdx.graphics.getWidth() / 2 - createLimb.getWidth() / 2,
							Gdx.graphics.getHeight() / 2 - createLimb.getHeight() / 2);
					ui().addActor(createLimb);

				}
				if (a == removeanim) {

				}
				if (a == removelimb) {

				}
				if (a == save) {
					saveAnimations();
				}
				if (a == createanim) {
					createAnimation();
				}
				if (a == createlimb) {
					createLimb();
				}
			}
		};

		// inputlistener
		InputListener input = new InputListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				Actor a = event.getListenerActor();
				if (a == animnamefield) {
					if (event.getKeyCode() == Keys.ENTER)
						createAnimation();
				}
				if (a == limbnamefield) {
					if (event.getKeyCode() == Keys.ENTER)
						createLimb();
				}
				return false;
			}
		};

		// create main animation window
		main_window = new VisWindow("Animation Editor");
		main_window.align(Align.top);

		// create and populate upper table
		VisTable upper_table = new VisTable();

		animation_select = new VisSelectBox<String>();
		animation_select.addListener(change);
		addanim = new VisTextButton("add");
		addanim.addListener(click);
		removeanim = new VisTextButton("del");
		removeanim.addListener(click);
		limb_select = new VisSelectBox<String>();
		limb_select.addListener(change);
		addlimb = new VisTextButton("add");
		addlimb.addListener(click);
		removelimb = new VisTextButton("del");
		removelimb.addListener(click);
		regions_select = new VisSelectBox<String>();
		regions_select.setItems(unique_animations);
		regions_select.addListener(change);
		regions_select.setSelected("noregion");

		upper_table.add("Animations:").colspan(3).growX().row();
		upper_table.add(animation_select).width(200).growX();
		upper_table.add(addanim);
		upper_table.add(removeanim).row();

		upper_table.add("Limbs:").colspan(3).growX().row();
		upper_table.add(limb_select).width(200).growX();
		upper_table.add(addlimb);
		upper_table.add(removelimb).row();

		upper_table.add("Frames:").colspan(3).growX().row();
		upper_table.add(regions_select).colspan(3).width(200).growX();

		// add all to main_window
		save = new VisTextButton("SAVE YOU NOOB!");
		save.addListener(click);
		main_window.add(upper_table).grow().row();
		main_window.add(save).padTop(20).growX();
		main_window.pack();
		main_window.setPosition(Gdx.graphics.getWidth() - main_window.getWidth(),
				Gdx.graphics.getHeight() - main_window.getHeight());

		// create "create animation" window
		createAnim = new VisWindow("Create Animation");
		createAnim.setModal(true);
		animnamefield = new VisTextField();
		animnamefield.addListener(input);
		createanim = new VisTextButton("create");
		createanim.addListener(click);
		createAnim.add(animnamefield).width(200).growX();
		createAnim.add(createanim);
		createAnim.pack();
		// create "create limb" window;
		createLimb = new VisWindow("Create Limb");
		createLimb.setModal(true);
		limbnamefield = new VisTextField();
		limbnamefield.addListener(input);
		createlimb = new VisTextButton("create");
		createlimb.addListener(click);
		createLimb.add(limbnamefield).width(200).growX();
		createLimb.add(createlimb);
		createLimb.pack();
		
		Array<String> anames = animations.keys().toArray();
		if(anames.size > 0){
			animation_select.setItems(anames);
			animation_select.setSelected(current_animation.getName());
		}
		ui().addActor(main_window);
		root().addChild(cam);
	}

	private void createAnimation() {
		if (animnamefield.getText() == null || animnamefield.getText().isEmpty()
				|| animations.containsKey(animnamefield.getText()))
			return;
		SyncAnimationNode animation = new SyncAnimationNode();
		animation.setName(animnamefield.getText());
		animations.put(animation.getName(), animation);
		animation_select.setItems(animations.keys().toArray());
		animation_select.setSelected(animation.getName());
		current_animation.remove();
		current_animation = animation;
		limb_animation_names.put(current_animation.getName(), new ObjectMap<String, String>());
		cam.addChild(current_animation);
		createAnim.remove();
	}

	private void createLimb() {
		if (limbnamefield.getText() == null || limbnamefield.getText().isEmpty()
				|| current_animation.getLimb(limbnamefield.getText()) != null)
			return;
		SyncAnimationNode animation = current_animation;

		animation.addLimb(limbnamefield.getText());
		Array<String> list = RpgUtils.getChildrenNames(animation);
		limb_select.setItems(list);
		limb_select.setSelected(limbnamefield.getText());
		Animation<Sprite> a = new Animation<Sprite>(animation.getFrameDuration(),
				character_atlas.createSprites("noregion"));
		limb_animation_names.get(current_animation.getName()).put(limbnamefield.getText(), "noregion");
		animation.setAnimation(limbnamefield.getText(), new AnimationNode(a));
		createLimb.remove();
	}

	private void saveAnimations() {
		Array<SyncAnimationNode> sanimations = animations.values().toArray();
		for (int i = 0; i < sanimations.size; i++) {
			String anim_save = "{"+newLineIndent(1);
			SyncAnimationNode sanim = sanimations.get(i);
			anim_save+=inQuotes("name")+":"+inQuotes(sanim.getName());
			anim_save+=","+newLineIndent(1);
			anim_save+=inQuotes("frameDuration")+":"+sanim.getFrameDuration();
			anim_save+=","+newLineIndent(1);
			anim_save+=inQuotes("limbs")+":{";
			anim_save+=newLineIndent(2);
			Array<SpriteNode> limbs = sanim.getLimbs();
			boolean lstarted = false;
			for (SpriteNode limb : limbs) {
				if(!lstarted){
					lstarted = true;
				}else{
					anim_save+=","+newLineIndent(3);
				}
				anim_save+=inQuotes(limb.getName())+":"+"{"+newLineIndent(3);
				anim_save+=inQuotes("regions")+":"+inQuotes(limb_animation_names.get(sanim.getName()).get(limb.getName()));
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("xpos")+":"+limb.getX();
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("ypos")+":"+limb.getY();
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("xorigin")+":"+limb.originX();
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("yorigin")+":"+limb.originY();
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("width")+":"+limb.getWidth();
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("height")+":"+limb.getHeight();
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("xscale")+":"+limb.getXScale();
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("yscale")+":"+limb.getYScale();
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("angle")+":"+limb.getAngle();
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("alpha")+":"+limb.getAlpha();
				anim_save+=","+newLineIndent(3);
				anim_save+=inQuotes("tint")+":"+inQuotes(limb.getTint().toString());
				
				
				anim_save+=newLineIndent(2)+"}";
				
			}
			anim_save+=newLineIndent(1)+"}";
			anim_save+=newLineIndent(0)+"}";
			
			FileHandle saveto = Gdx.files.local("../android/assets/animations/"+sanim.getName()+".anim");
			if(saveto.exists())
				saveto.writeString(anim_save, false);
			else
				saveto.writeString(anim_save, true);
		}
	}
	
	private String inQuotes(String s){
		return "\""+s+"\"";
	}
	
	private String newLineIndent(int indents){
		String s = "\n";
		for (int i = 0; i < indents; i++) {
			s+="\t";
		}
		return s;
	}

	private void loadAnimations() {
		FileHandle dir = Gdx.files.internal("bin/animations/");
		JsonReader reader = new JsonReader();
		JsonValue root = null;
		for (FileHandle entry : dir.list()) {
			SyncAnimationNode sanim = new SyncAnimationNode();
			root = reader.parse(entry);
			
			sanim.setName(root.get("name").asString());
			sanim.setFrameDuration(root.getFloat("frameDuration"));
			limb_animation_names.put(sanim.getName(), new ObjectMap<String, String>());
			
			JsonValue limbs = root.get("limbs");
			for (int i = 0; i < limbs.size; i++) {
				JsonValue limb = limbs.get(i);
				SpriteNode n = new SpriteNode(null);
				n.setName(limb.name);
				String res = limb.getString("regions", "noregion");
				limb_animation_names.get(sanim.getName()).put(n.getName(), res);
				AnimationNode an = new AnimationNode(new Animation<Sprite>(sanim.getFrameDuration(),
						character_atlas.createSprites(res)));
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
			animations.put(sanim.getName(), sanim);
			if(current_animation==null)
				current_animation = sanim;
			
		}
	}
	
	float elapsed = 0;
	float interval = 10f;

	@Override
	public void update(float delta) {
		ui().act(delta);
		root().update(delta);
		elapsed+=delta;
		if(current_animation!=null && elapsed > interval){
			elapsed = 0;
			RpgUtils.logError("Animation:"+current_animation.getName());
			RpgUtils.logError("selectedLimb:"+limb_select.getSelected());
			RpgUtils.logError(limb_animation_names.get(current_animation.getName()).toString());
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.begin();
		root().draw(batch);
		batch.end();
		ui().draw();
	}

	@Override
	public void remove() {

	}

}
