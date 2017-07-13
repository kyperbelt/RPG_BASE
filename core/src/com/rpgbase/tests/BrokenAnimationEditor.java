package com.rpgbase.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.InputValidator;
import com.kotcrab.vis.ui.util.Validators;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.rpg.game.Configuration;
import com.rpg.game.assets.GameAssets;
import com.rpg.game.mode.BaseMode;
import com.rpg.game.nodes.CameraNode;
import com.rpg.game.nodes.SpriteNode;
import com.rpg.game.nodes.kfa.KeyFrame;
import com.rpg.game.nodes.kfa.KeyFrameNode;
import com.rpg.game.nodes.kfa.NodeTransform;
import com.rpg.game.utils.RpgUtils;

public class BrokenAnimationEditor extends BaseMode {
	
	//DEFUNCT TODO: REFACTOR OR DELETE
	//right now it is using the keyframe node system that i developed
	//which turned out to not be very scalable with a lot of potential
	//for error

	VisWindow animation_window;
	VisSelectBox<String> current_animation, current_frame, current_limb, extension,limb_region;
	VisTextButton remove_animation, remove_frame, remove_limb, add_animation, add_frame, add_limb;
	// limb transform data;
	VisValidatableTextField xpos, ypos, xscale, yscale, angle, xorigin, yorigin,duration,color;
	VisCheckBox visibility;

	// create new animation
	VisWindow createanimation,createlimb;
	VisTextField animnamefield,limbnamefield;
	VisTextButton createanimbutton,createlimbbutton;
	
	// load animation
	VisWindow load_window;

	CameraNode cam;

	ObjectMap<String,KeyFrameNode> keyframes;
	KeyFrameNode current_kfn;
	
	TextureAtlas atlas;
	ObjectMap<String,TextureRegion> regions;

	@Override
	public void load(GameAssets loader) {
		
		VisUI.load();
		loader.loadAtlas("image/character.pack");
	}

	@Override
	public void create() {
		atlas = assets().getAtlas("image/character.pack");
		//VARS
		keyframes = new ObjectMap<String, KeyFrameNode>();
		regions = new ObjectMap<String, TextureRegion>();
		/// GAMDNODES ---------------------------------------------------------
		cam = new CameraNode(new FitViewport(Configuration.WIDTH / 2, Configuration.HEIGHT / 2));

		root().addChild(cam);

		// UI ------------------------------------------------------------------
		app().ui.clear();// clear if dirty

		animation_window = new VisWindow("Animations");

		animation_window.align(Align.top);
		createAniamtionwindow();
		animation_window.setPosition(Configuration.WIDTH - animation_window.getWidth(),
				Configuration.HEIGHT - animation_window.getHeight());
		// animation_window.setSize(200, 200);
		createanimation = new VisWindow("Create New Animation");
		createanimation.add("Name:").left();
		animnamefield = new VisTextField();
		createanimbutton = new VisTextButton("create");
		createanimation.add(animnamefield).width(250).growX();
		createanimation.add(createanimbutton);
		createanimation.pack();
		createanimation.setModal(true);
		createanimbutton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(animnamefield.getText().isEmpty()||keyframes.containsKey(animnamefield.getText()))
					return;
				Array<KeyFrame> frames = new Array<KeyFrame>();
				KeyFrame frame = new KeyFrame();
				frame.setLastFor(.25f);
				frames.add(frame);
				current_kfn = new KeyFrameNode(new Array<SpriteNode>(), frames);
				current_kfn.setName(animnamefield.getText());
				keyframes.put(current_kfn.getName(), current_kfn);
				current_animation.setItems(keyframes.keys().toArray());
				current_animation.setSelected(current_kfn.getName());
				createanimation.remove();
			}
		});
		
		createlimb = new VisWindow("Create New Limb");
		
		createlimb.add("Name:").left();
		limbnamefield = new VisTextField();
		createlimbbutton = new VisTextButton("create");
		createlimb.add(limbnamefield).width(250).growX();
		createlimb.add(createlimbbutton);
		createlimb.pack();
		createlimb.setModal(true);
		createlimbbutton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

				if(limbnamefield.getText().isEmpty())
					return;
				SpriteNode limb = new SpriteNode(null);
				limb.setName(limbnamefield.getText());
				current_kfn.addChild(limb);
				Array<String> limbnames = RpgUtils.getChildrenNames(current_kfn);
				current_limb.setItems(limbnames);
				current_limb.setSelected(limb.getName());
				createlimb.remove();
			}
		});

		// add actors
		ui().addActor(animation_window);
	}
	
	private Array<String> getAtlasRegions(){
		Array<String> region_names = new Array<String>();
		region_names.add("noregion");
		Array<AtlasRegion> rs = atlas.getRegions();
		for (int i = 0; i < rs.size; i++) {
			String name = rs.get(i).name;
			if(rs.get(i).index!=-1);
				name+=rs.get(i).index;
			region_names.add(name);
			regions.put(name, rs.get(i));
		}
		return region_names;
	}
	
	public void animationChanged(){
		Array<KeyFrame> keyframes = current_kfn.getFrames();
		Array<String> indi = new Array<String>();
		for (int i = 0; i < keyframes.size; i++) {
			indi.add(""+i);
		}
		current_frame.setItems(indi);
	}	
	
	public void frameChanged(){
		limbChanged();
	}
	
	public void extensionChanged(){
		
	}
	
	public void limbChanged(){
		if(current_limb.getSelected() == null || current_limb.getSelected().isEmpty())
			return;
		SpriteNode limb = (SpriteNode) current_kfn.getNode(current_limb.getSelected());
		
		KeyFrame kf = current_kfn.getFrames().get(current_frame.getSelectedIndex());
		if(!kf.getTransforms().containsKey(limb.getName())){
			kf.getTransforms().put(limb.getName(), new NodeTransform());
		}
		NodeTransform nt = kf.getTransforms().get(limb.getName());
		if(nt.region == null)
			limb_region.setSelected("noregion");
		else
			limb_region.setSelected(regions.findKey(nt.region, true));
		xpos.setText(""+nt.x);
		ypos.setText(""+nt.y);
		xscale.setText(""+nt.scalex);
		yscale.setText(""+nt.scaley);
		xorigin.setText(""+nt.originx);
		yorigin.setText(""+nt.originy);
		angle.setText(""+nt.angle);
		visibility.setChecked(nt.visible!=KeyFrame.INVISIBLE);
		color.setText(Integer.toHexString(nt.tint.toIntBits()).substring(0,6).toUpperCase());
		duration.setText(""+kf.getLastFor());
		
		
	}
	
	public void applyTransforms(){
		SpriteNode limb = (SpriteNode) current_kfn.getNode(current_limb.getSelected());
		KeyFrame kf = current_kfn.getFrames().get(current_frame.getSelectedIndex());
		NodeTransform nt = kf.getTransforms().get(limb.getName());
		//TODO:nt.region = regions.get(limb_region.getSelected());
		nt.x = Float.parseFloat(xpos.getText());
		nt.y = Float.parseFloat(ypos.getText());
		nt.scalex = Float.parseFloat(xscale.getText());
		nt.scaley = Float.parseFloat(yscale.getText());
		nt.originx = Float.parseFloat(xorigin.getText());
		nt.originy = Float.parseFloat(yorigin.getText());
		nt.angle = Float.parseFloat(angle.getText());
		nt.tint = new Color((Integer.parseInt(color.getText(),16)));
		if(visibility.isChecked())
			nt.visible = KeyFrame.VISIBLE;
		else
			nt.visible = KeyFrame.INVISIBLE;
		kf.setLastFor(Float.parseFloat(duration.getText()));
		kf.applyTransforms();
	}

	public void createAniamtionwindow() {
		
		ChangeListener changelistener = new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Actor a = event.getListenerActor();
				if(a instanceof VisValidatableTextField&&!((VisValidatableTextField)a).isInputValid())
					return;
				if(current_kfn == null)
					return;
				if(a == current_animation){
					animationChanged();
				}
				if(a == current_frame){
					frameChanged();
				}
				
				if(a == extension){
					extensionChanged();
				}
				
				if(current_limb.getSelected() == null || current_limb.getSelected().isEmpty())
					return;
				SpriteNode limb = (SpriteNode) current_kfn.getNode(current_limb.getSelected());
				
				KeyFrame kf = current_kfn.getFrames().get(current_frame.getSelectedIndex());
				NodeTransform nt = kf.getTransforms().get(limb.getName());
				
				
				
				if(a == current_limb){
					limbChanged();
				}
				
				if(a == visibility){
					if(visibility.isChecked())
						nt.visible = KeyFrame.VISIBLE;
					else
						nt.visible = KeyFrame.INVISIBLE;
				}
				if(a == xpos){
					nt.x = Float.parseFloat(xpos.getText());
					kf.applyTransforms();
				}
				if(a == ypos){
					nt.y = Float.parseFloat(ypos.getText());
					kf.applyTransforms();
				}
				if(a == xorigin){
					nt.originx = Float.parseFloat(xorigin.getText());
					kf.applyTransforms();
				}
				if(a == yorigin){
					nt.originy = Float.parseFloat(yorigin.getText());
					kf.applyTransforms();
				}
				if(a == xscale){
					nt.scalex = Float.parseFloat(xscale.getText());
					kf.applyTransforms();
				}
				if(a == yscale){
					nt.scaley = Float.parseFloat(yscale.getText());
					kf.applyTransforms();
				}
				if(a == angle){
					nt.angle = Float.parseFloat(angle.getText());
					kf.applyTransforms();
				}
				if(a == duration){
					kf.setLastFor(Float.parseFloat(duration.getText()));
					kf.applyTransforms();
				}
				if(a == color){
					if(!color.getText().isEmpty())
						nt.tint = new Color((int) Long.parseLong(color.getText()+"FF",16));
					kf.applyTransforms();
				}
				if(a == limb_region){
					//TODO:nt.region = regions.get(limb_region.getSelected());
					kf.applyTransforms();
				}
				
				
			}
		};
		
		ClickListener clicklistener = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Actor a = event.getListenerActor();
				if(a == add_animation){
					createanimation.setPosition(Gdx.graphics.getWidth()/2-createanimation.getWidth()/2
							, Gdx.graphics.getHeight()/2-createanimation.getHeight()/2);
					ui().addActor(createanimation);
					
				}
				if(a == remove_animation){
					
				}
				if(a == add_frame){
					
				}
				if(a == remove_frame){
					
				}
				if(a == add_limb){
					if(current_kfn == null)
						return;
					createlimb.setPosition(Gdx.graphics.getWidth()/2-createlimb.getWidth()/2
							, Gdx.graphics.getHeight()/2-createlimb.getHeight()/2);
					ui().addActor(createlimb);
				}
				if(a == remove_limb){
					
				}
	
			}
		};
		
		float field_size = 60;
		VisTable selections_table = new VisTable();
		VisTable transform_table = new VisTable();
		// create co mponents
		// selectboxes
		current_animation = new VisSelectBox<String>();
		current_animation.addListener(changelistener);
		current_frame = new VisSelectBox<String>();
		current_frame.addListener(changelistener);
		extension = new VisSelectBox<String>();
		extension.addListener(changelistener);
		extension.setItems("NONE");
		current_limb = new VisSelectBox<String>();
		current_limb.addListener(changelistener);
		
		// buttons
		add_animation = new VisTextButton("add");
		add_animation.addListener(clicklistener);
		remove_animation = new VisTextButton("del");
		remove_animation.addListener(clicklistener);
		add_frame = new VisTextButton("add");
		add_frame.addListener(clicklistener);
		remove_frame = new VisTextButton("del");
		remove_frame.addListener(clicklistener);
		add_limb = new VisTextButton("add");
		add_limb.addListener(clicklistener);
		remove_limb = new VisTextButton("del");
		remove_limb.addListener(clicklistener);
		
		// transform
		xpos = new VisValidatableTextField(Validators.FLOATS);
		xpos.addListener(changelistener);
		xpos.setText(""+0);
		ypos = new VisValidatableTextField(Validators.FLOATS);
		ypos.addListener(changelistener);
		ypos.setText(""+0);
		angle = new VisValidatableTextField(Validators.FLOATS);
		angle.setText(""+0);
		angle.addListener(changelistener);
		xscale = new VisValidatableTextField(Validators.FLOATS);
		xscale.setText(""+1);
		xscale.addListener(changelistener);
		yscale = new VisValidatableTextField(Validators.FLOATS);
		yscale.setText(""+1);
		yscale.addListener(changelistener);
		xorigin = new VisValidatableTextField(Validators.FLOATS);
		xorigin.setText(""+0);
		xorigin.addListener(changelistener);
		yorigin = new VisValidatableTextField(Validators.FLOATS);
		yorigin.setText(""+0);
		yorigin.addListener(changelistener);
		visibility= new VisCheckBox("show");
		visibility.setChecked(true);
		visibility.addListener(changelistener);
		duration = new VisValidatableTextField(Validators.FLOATS);
		duration.setText(""+0);
		duration.addListener(changelistener);
		color = new VisValidatableTextField(new InputValidator() {
			
			@Override
			public boolean validateInput(String input) {
				if(input.matches("^[0-9A-Fa-f]+$"))
					return true;
				return false;
			}
		});
		color.setText("FFFFFF");
		color.addListener(changelistener);
		limb_region = new VisSelectBox<String>();
		limb_region.setItems(getAtlasRegions());
		limb_region.addListener(changelistener);

		// add to selection_table
		selections_table.add("Animation:").align(Align.left).colspan(3).growX().row();
		selections_table.add(current_animation).width(200).growX();
		selections_table.add(add_animation);
		selections_table.add(remove_animation).row();
		selections_table.add("Frame:").left().colspan(3).growX().row();
		selections_table.add(current_frame).growX();
		selections_table.add(add_frame);
		selections_table.add(remove_frame).row();
		selections_table.add("Extension:").colspan(3).left().row();
		;
		selections_table.add(extension).colspan(3).growX().row();
		selections_table.addSeparator().colspan(3).growX().row();
		selections_table.add("Limb:").colspan(3).growX().row();
		selections_table.add(current_limb).growX();
		selections_table.add(add_limb);
		selections_table.add(remove_limb).row();
		selections_table.addSeparator().colspan(3).padBottom(20).row();

		// add to transform table

		
		transform_table.add("Region:").left();
		transform_table.add(limb_region).colspan(3).padRight(10).growX().row();
		transform_table.add("xpos:").left();
		transform_table.add(xpos).width(field_size).left().padRight(10);
		transform_table.add("ypos:").left();
		transform_table.add(ypos).width(field_size).left().row();

		transform_table.add("xscale:").left();
		transform_table.add(xscale).width(field_size).left().padRight(10);
		transform_table.add("yscale:").left();
		transform_table.add(yscale).width(field_size).left().row();

		transform_table.add("xorigin:").left();
		transform_table.add(xorigin).width(field_size).left().padRight(10);
		transform_table.add("yorigin:").left();
		transform_table.add(yorigin).width(field_size).left().row();
		transform_table.add("angle:").left();
		transform_table.add(angle).width(field_size).padRight(10);
		transform_table.add(visibility).colspan(2).width(field_size).left().row();
		transform_table.add("duration:").left();
		transform_table.add(duration).width(field_size).left().padRight(10);
		transform_table.add("color:").left();
		transform_table.add(color).width(field_size).left().row();
		
		
		transform_table.pack();

		// add to animation window
		animation_window.add(selections_table).growX().row();
		animation_window.add(transform_table).growX().row();

		animation_window.pack();
		
	}

	@Override
	public void update(float delta) {
		if(current_kfn!=null){
			cam.addChild(current_kfn);
		}
		root().update(delta);
		ui().act(delta);
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
