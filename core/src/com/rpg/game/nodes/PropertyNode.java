package com.rpg.game.nodes;

import com.badlogic.gdx.utils.ObjectMap;
import com.rpg.game.utils.RpgUtils;

/**
 * This node is made to hold properties 
 * in a map used for stat values,flags,tags ect.
 * @author kyperbelt
 *
 */
public class PropertyNode extends BasicNode{
	
	private ObjectMap<String, Object> properties;
	
	public PropertyNode(){
		setName("property_node");
		properties = new ObjectMap<String, Object>();
	}
	/**
	 * get the property as an object. 
	 * Use this if the property set was not string/primitive
	 * @param name
	 * @return
	 */
	public Object getProperty(String name){
		if(!properties.containsKey(name)){
			RpgUtils.logError("Property not found["+getName()+":"+name+"] make sure you used correct spelling.");
			return null;
		}
		return properties.get(name);
	}
	/**
	 * get the property as an int (throws error if its not an int)
	 * @param name
	 * @return
	 */
	public int getInt(String name){
		return (Integer)getProperty(name);
	}
	/**
	 * get the property as an float (throws error if its not an int)
	 * @param name
	 * @return
	 */
	public float getFloat(String name){
		return (Float)getProperty(name);
	}
	/**
	 * get the property as an boolean (throws error if its not an int)
	 * @param name
	 * @return
	 */
	public boolean getBool(String name){
		return (Boolean)getProperty(name);
	}
	/**
	 * get the property as an String (throws error if its not an int)
	 * @param name
	 * @return
	 */
	public String getString(String name){
		return (String)getProperty(name);
	}
	/**
	 * add a property to this node, use this to initially setup 
	 * the node in order to avoid duplicates
	 * @param name
	 * @param property
	 */
	public void addProperty(String name,Object property){
		if(properties.containsKey(name)){
			RpgUtils.logError("You may not add duplicate properties to the same node. ["+name+"]"
					+ "choose a different name or use changeProperty().");
			return;
		}
		properties.put(name, property);
	}
	
	public void changeProperty(String name,Object property){
		if(!properties.containsKey(name)){
			RpgUtils.logError("Property change failed for ["+getName()+":"+name+"] make sure you used correct spelling.");
			return;
		}
		properties.put(name, property);
	}
	

}
