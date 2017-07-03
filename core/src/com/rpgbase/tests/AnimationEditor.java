package com.rpgbase.tests;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.InputValidator;
import com.kotcrab.vis.ui.util.Validators;
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
import com.rpg.game.nodes.kfa.KeyFrameNode;

public class AnimationEditor extends BaseMode {

	VisWindow animation_window;
	VisSelectBox<String> current_animation, current_frame, current_limb, extension;
	VisTextButton remove_animation, remove_frame, remove_limb, add_animation, add_frame, add_limb;
	// limb transform data;
	VisTextField region;
	VisValidatableTextField xpos, ypos, xscale, yscale, angle, xorigin, yorigin;

	// create new animation
	VisWindow create_window;

	// load animation
	VisWindow load_window;

	CameraNode cam;

	KeyFrameNode current_kfn;

	@Override
	public void load(GameAssets loader) {
		VisUI.load();
	}

	@Override
	public void create() {

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
		create_window = new VisWindow("Create New Animation");

		// add actors
		ui().addActor(animation_window);
	}

	public void createAniamtionwindow() {
		
		ChangeListener changelistener = new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Actor a = event.getListenerActor();
				if(a == current_animation){
					
				}
				if(a == current_frame){
					
				}
				if(a == current_limb){
					
				}
				if(a == extension){
					
				}
			}
		};
		
		ClickListener clicklistener = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Actor a = event.getListenerActor();
				if(a == add_animation){
					
				}
				if(a == remove_animation){
					
				}
				if(a == add_frame){
					
				}
				if(a == remove_frame){
					
				}
				if(a == add_limb){
					
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
		ypos = new VisValidatableTextField(Validators.FLOATS);
		ypos.addListener(changelistener);
		angle = new VisValidatableTextField(Validators.FLOATS);
		angle.addListener(changelistener);
		xscale = new VisValidatableTextField(Validators.FLOATS);
		xscale.addListener(changelistener);
		yscale = new VisValidatableTextField(Validators.FLOATS);
		yscale.addListener(changelistener);
		xorigin = new VisValidatableTextField(Validators.FLOATS);
		xorigin.addListener(changelistener);
		yorigin = new VisValidatableTextField(Validators.FLOATS);
		yorigin.addListener(changelistener);

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
		transform_table.add(angle).width(field_size).padRight(10).row();

		transform_table.pack();

		// add to animation window
		animation_window.add(selections_table).growX().row();
		animation_window.add(transform_table).growX().row();

		animation_window.pack();
	}

	@Override
	public void update(float delta) {
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
