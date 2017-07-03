package com.rpg.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ObjectMap;
import com.rpg.game.Configuration;
import com.rpg.game.nodes.BasicNode;
import com.rpg.game.nodes.KeybindNode;
import com.rpg.game.nodes.event.KeybindEvent;

public class Keybinds {
	
	//movement
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	public static final String UP = "up";
	public static final String DOWN = "down";
	
	//actions
	public static final String[] ACTIONS = new String[Configuration.MAX_KEY_ACTIONS];
	
	public static final String INTERACT = "interact";
	
	private static Keybinds binds;
	private static Keybinds getKeybinds(){
		if(binds == null)
			binds = new Keybinds();
		return binds;
	}
	
	private ObjectMap<String, IntArray> keys;
	private ObjectMap<String,Boolean> pressed;
	private ObjectMap<String ,Boolean> once;
	
	private KeybindEvent event;
	
	private Array<KeybindNode> knodes;
	
	private Keybinds(){
		keys = new ObjectMap<String, IntArray>();
		pressed = new ObjectMap<String, Boolean>();
		once = new ObjectMap<String, Boolean>();
		knodes = new Array<KeybindNode>();
		event = new KeybindEvent(BasicNode.NULL,new Object[2]);
		addBind(LEFT);
		mapKeyToBind(LEFT, Keys.LEFT);
		addBind(RIGHT);
		mapKeyToBind(RIGHT, Keys.RIGHT);
		addBind(UP);
		mapKeyToBind(UP, Keys.UP);
		addBind(DOWN);
		mapKeyToBind(DOWN, Keys.DOWN);
		
		try {
			for (int i = 0; i < ACTIONS.length; i++) {
				ACTIONS[i] = "action_"+i;
				addBind(ACTIONS[i]);
			
					mapKeyToBind(ACTIONS[i], Keys.class.getField("NUM_"+i).getInt(null));
			
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		addBind(INTERACT);
		mapKeyToBind(INTERACT, Keys.Z);
		
		
	}
	
	private void add_Keynode(KeybindNode node){
		knodes.add(node);
	}
	
	private void remove_Keynode(KeybindNode node){
		knodes.removeValue(node, true);
	}
	
	
	private void addBind(String bind){
		if(keys.containsKey(bind)){
			RpgUtils.logError("Cannot bind duplicate ["+bind+"]");
			return;
		}
		keys.put(bind, new IntArray());
		pressed.put(bind, false);
		once.put(bind, false);
	}
	
	private void mapKeyToBind(String bind,int key){
		if(!bindExists(bind))
			return;
		if(keys.get(bind).contains(key)){
			RpgUtils.logError("Key["+Keys.toString(key)+"]  already mapped to this bind");
			return;
		}
		keys.get(bind).add(key);
	}
	
	private IntArray getMapped_Keys(String bind){
		if(!bindExists(bind))
			return null;
		return keys.get(bind);
	}
	
	private boolean isBind_Pressed(String bind){
		if(!bindExists(bind))
			return false;
		return pressed.get(bind).booleanValue();
	}
	
	private boolean isBind_JustPressed(String bind){
		if(!bindExists(bind))
			return false;
		return once.get(bind).booleanValue();
	}
	
	private boolean bindExists(String bind){
		if(!keys.containsKey(bind)){
			RpgUtils.logError("No such bind exists ["+bind+"].");
			return false;
		}
		return true;
	}
	
	private void update_keys(){
		reset_keys();
		Array<String> _keybinds = keys.keys().toArray();
		for (int i = 0; i < _keybinds.size; i++) {
			String keybind = _keybinds.get(i);
			IntArray press_keys = keys.get(keybind);
			for (int j = 0; j < press_keys.size; j++) {
				int pressed_key = press_keys.get(j);
				if(Gdx.input.isKeyJustPressed(pressed_key)){
					once.put(keybind, true);
					event.setEvent(keybind, true);
					for (int k = 0; k < knodes.size; k++) {
						knodes.get(k).castKeyEvent(event);
					}
				}
				if(Gdx.input.isKeyPressed(pressed_key)){
					pressed.put(keybind, true);
					event.setEvent(keybind, false);
					for (int k = 0; k < knodes.size; k++) {
						knodes.get(k).castKeyEvent(event);
					}
					break;
				}
			}
		}
	}
	
	private void reset_keys(){
		Array<String> _keybinds = keys.keys().toArray();
		for (int i = 0; i < _keybinds.size; i++) {
			String keybind = _keybinds.get(i);
			pressed.put(keybind, false);
			once.put(keybind, false);
		}
	}
	
	
	public static void addKeybind(String bind){
		getKeybinds().addBind(bind);
	}
	
	public static IntArray getMappedKeys(String bind){
		return getKeybinds().getMapped_Keys(bind);
	}
	
	public static void mapKey(String bind,int key){
		getKeybinds().mapKeyToBind(bind, key);
	}
	
	public static boolean isPressed(String bind){
		return getKeybinds().isBind_Pressed(bind);
	}
	
	public static boolean isJustPressed(String bind){
		return getKeybinds().isBind_JustPressed(bind);
	}
	
	public static void addKeyNode(KeybindNode node){
		getKeybinds().add_Keynode(node);
	}
	
	public static void removeKeyNode(KeybindNode node){
		getKeybinds().remove_Keynode(node);
	}
	
	public static void update(){
		getKeybinds().update_keys();
	}

}
