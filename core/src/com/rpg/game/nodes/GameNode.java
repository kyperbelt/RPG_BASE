package com.rpg.game.nodes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.rpg.game.RpgBase;
import com.rpg.game.nodes.event.NodeEvent;

public interface GameNode{
	
	/**
	 * set name of node
	 * @param name
	 */
	public void setName(String name);
	/**
	 * get node name
	 * @return
	 */
	public String getName();
	/**
	 * get the parent node if any
	 * @return
	 */
	public GameNode getParent();
	/**
	 * get x position
	 * @return
	 */
	public float getX();
	/**
	 * get y position 
	 * @return
	 */
	public float getY();
	/**
	 * set the position of the node
	 * @param x
	 * @param y
	 */
	public void setPosition(float x,float y);
	/**
	 * set the scale of the node
	 * @param xscale
	 * @param yscale
	 */
	public void setScale(float xscale,float yscale);
	/**
	 * set the scale of the node
	 */
	public void setScale(float scale);
	/**
	 * scale the node by amount
	 */
	public void scale(float amount);
	/**
	 * get the xscale of the node
	 * @return
	 */
	public float getXScale();
	/**
	 * get the yscale of the node
	 * @return
	 */
	public float getYScale();
	/**
	 * remove the node from the parent node
	 */
	public void remove();
	/**
	 * clear all the children of this node
	 */
	public void clearChildren();
	/**
	 * set the tint coloring of this node
	 * @param tint
	 */
	public void setTint(Color tint);
	/**
	 * get the current tint color of this node
	 * null if not visible
	 * @return
	 */
	public Color getTint();
	/**
	 * get the current node alpha
	 * @return
	 */
	public float getAlpha();
	/**
	 * set the alpha of the node
	 * @param alpha
	 */
	public void setAlpha(float alpha);
	/**
	 * get all the children of this node if any
	 * @return
	 */
	public Array<GameNode> getChildren();
	/**
	 * update this node and all its children
	 * @param delta
	 */
	public void update(float delta);
	/**
	 * draw this node and all its children
	 * @param batch
	 */
	public void draw(SpriteBatch batch);
	/**
	 * get the visibility of this node
	 * @return
	 */
	public boolean isVisible();
	/**
	 * set the visibility of this node
	 * @param visible
	 */
	public void setVisible(boolean visible);
	/**
	 * set the angle of this node
	 * @param radians
	 */
	public void setAngle(float degrees);
	/**
	 * get the current angle of this node
	 * @return
	 */
	public float getAngle();
	/**
	 * rotate this node by a certain amount
	 * @param radians
	 * @return
	 */
	public float rotate(float degress);
	/**
	 * get the origin x of the node
	 * @return
	 */
	public float originX();
	/**
	 * get the origin y of the node
	 * @return
	 */
	public float originY();
	/**
	 * set the origin of the node
	 * @param originx
	 * @param originy
	 */
	public void setOrigin(float originx,float originy);
	/**
	 * set the size of the node 
	 * {not required for all nodes}
	 * @param width
	 * @param height
	 */
	public void setSize(float width,float height);
	/**
	 * get the node width
	 * @return
	 */
	public float getWidth();
	/**
	 * get the node height
	 * @return
	 */
	public float getHeight();
	/**
	 * add a child to the node
	 * @param node
	 */
	public void addChild(GameNode node);
	/**
	 * set the parent of this node
	 * @param node
	 */
	void setParent(GameNode node);
	/**
	 * set the origin of this node to the center
	 */
	void setOriginCenter();
	/**
	 * get node by name
	 */
	GameNode getNode(String name);
	/**
	 * alert all children
	 */
	boolean alertAll(NodeEvent e);
	/**
	 * alert named node
	 */
	boolean alert(String name,NodeEvent e);
	/**
	 * when alerted override to do specific things
	 */
	public boolean onAlert(NodeEvent e);
	/**
	 * called when node is added to parent override to use
	 */
	public void onAdded();
	/**
	 * called when node is removed  override to use
	 */
	public void onRemove();
	/**
	 * get the root node
	 */
	public GameNode getRoot();
	/**
	 * check if node has parent
	 */
	public boolean hasParent();
	/**
	 * get the x position of the node in the world
	 * @return
	 */
	public float worldX();
	/**
	 * get the y position of the node in the world
	 */
	public float worldY();
	/**
	 * do not render if not visible
	 */
	public void setUpdateOnVisibility(boolean enable);
	
	public RpgBase getApp();
	
	public void setApp(RpgBase base);
	/**
	 * clear all child nodes from this node
	 */
	public void clear();

}
