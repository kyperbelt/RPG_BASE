package com.rpg.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.rpg.game.nodes.GameNode;

public class RpgUtils {
	
	//default tags
	private static final String INFO = "[INFO]:";
	private static final String DEBUG = "[DEBUG]:";
	private static final String ERROR = "[ERROR]:";

	/**
	 * log a message with custom tag
	 * @param tag
	 * @param message
	 */
	public static void log(String tag,String message){
		Gdx.app.log(tag, message);
	}
	/**
	 * log an info message
	 * @param message
	 */
	public static void logInfo(String message){
		Gdx.app.log(INFO, message);
	}
	/**
	 * log a debug message, log level must be set to debug to use
	 * @param message
	 */
	public static void logDebug(String message){
		Gdx.app.debug(DEBUG, message);
	}
	/**
	 * log an error message
	 * @param message
	 */
	public static void logError(String message){
		Gdx.app.error(ERROR, message);
	}
	/**
	 * check if a gamenode contains the given name
	 * @param name
	 * @param nodes
	 * @return
	 */
	public static GameNode containsName(String name,Array<GameNode> nodes){
		for (int i = 0; i < nodes.size; i++) {
			if(nodes.get(i).getName().equals(name))
				return nodes.get(i);
		}
		return null;
	}
	/**
	 * log all the children of the given game node
	 * @param node
	 */
	public static void logChildren(GameNode node){
		Gdx.app.log("NODE["+node.getName()+"]", "\n\t"
				+ "children:"+node.getChildren().size+"\n\t\t"+node.getChildren().toString("\n\t\t"));
	}
	
	public static Array<String> getChildrenNames(GameNode node){
		Array<String> names = new Array<String>();
		for (int i = 0; i < node.getChildren().size; i++) {
			names.add(node.getChildren().get(i).getName());
		}
		return names;
		
	}
	/**
	 * clamp a number within the given parameters
	 * @return
	 */
	public static float clampNum(float num,float min,float max){
		if(num > max)
			return max;
		else if(num < min)
			return min;
		else
			return num;
	}
}
