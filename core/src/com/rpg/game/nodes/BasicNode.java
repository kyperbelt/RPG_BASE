package com.rpg.game.nodes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.rpg.game.RpgBase;
import com.rpg.game.nodes.event.NodeAddedEvent;
import com.rpg.game.nodes.event.NodeEvent;
import com.rpg.game.script.NodeSearch;
import com.rpg.game.utils.RpgUtils;

public class BasicNode implements GameNode{
	
	public static final GameNode NULL = new BasicNode();
	public static final NodeAddedEvent event = new NodeAddedEvent(NULL, null);
	private String name;
	private GameNode parent;
	private Array<GameNode> children;
	
	//position data
	private float x;
	private float y;
	private float originx;
	private float originy;
	private float angle;
	
	//asthetic data
	private float width;
	private float height;
	private float alpha;
	private float xscale;
	private float yscale;
	private Color tint;
	private boolean visible;
	private boolean update_on_visibility;
	
	private RpgBase app;
	
	public BasicNode(){
		children = new Array<GameNode>();
		name = "basic_node";
		x = 0;
		y = 0;
		angle = 0;
		alpha = 1;
		xscale = 1;
		yscale = 1;
		tint = Color.WHITE;
		visible = true;
		originx = 0;
		originy = 0;
		width = 2;
		height = 2;
		parent = NULL;
		update_on_visibility = false;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public GameNode getParent() {
		if(parent==null)
			parent = NULL;
		return parent;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setScale(float xscale, float yscale) {
		this.xscale = xscale;
		this.yscale = yscale;
		
	}

	@Override
	public float getXScale() {
		return xscale;
	}

	@Override
	public float getYScale() {
		return yscale;
	}

	@Override
	public void remove() {
		if(parent==NULL)
			return;
		onRemove();
		parent.getChildren().removeValue(this, true);
		setParent(NULL);
	}

	@Override
	public void clearChildren() {
		children.clear();
	}

	@Override
	public void setTint(Color tint) {
		this.tint = tint;
	}

	@Override
	public Color getTint() {
		return tint;
	}

	@Override
	public float getAlpha() {
		return alpha;
	}

	@Override
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	@Override
	public Array<GameNode> getChildren() {
		return children;
	}

	@Override
	public void update(float delta) {
		if(update_on_visibility&&visible)
			return;
		for (int i = 0; i < children.size; i++) {
			children.get(i).update(delta);
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		if(!isVisible())
			return;
		for (int i = 0; i < children.size; i++) {
			children.get(i).draw(batch);
		}
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public void setAngle(float degrees) {
		this.angle = degrees;
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public float rotate(float degrees) {
		return (angle+=degrees);
	}

	@Override
	public float originX() {
		return originx;
	}

	@Override
	public float originY() {
		return originy;
	}

	@Override
	public void setOrigin(float originx, float originy) {
		this.originx = originx;
		this.originy = originy;
	}

	@Override
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void addChild(GameNode node) {
		node.remove();
		node.setParent(this);
		String name = ""+node.getName();
		while(RpgUtils.containsName(name, children)!=null){
			name=node.getName()+"@"+children.size;
		}
		node.setName(name);
		children.add(node);
		node.onAdded();
	}

	@Override
	public void setParent(GameNode node) {
		this.parent = node;
	}

	@Override
	public void setOriginCenter() {
		setOrigin(getWidth()*.5f, getHeight()*.5f);
		
	}

	@Override
	public void setScale(float scale) {
		setScale(scale, scale);
	}

	@Override
	public void scale(float amount) {
		setScale(xscale+amount, yscale+amount);
	}

	@Override
	public GameNode getNode(String query) {
		GameNode n = NodeSearch.findNode(this, query);
		if(n==BasicNode.NULL){
			RpgUtils.logError("No such node["+query+"] from ["+getName()+"].");
		}
		return n;
	}
	
	@Override
	public String toString() {
		return name;
	}

	//TODO change to events
	@Override
	public boolean alertAll(NodeEvent e) {
		for (int i = 0; i < children.size; i++) {
			if(children.get(i).alertAll(e))
				return true;
		}
		return onAlert(e);
	}

	@Override
	public boolean alert(String name, NodeEvent e) {
		GameNode n = RpgUtils.containsName(name, children);
		if(n==null){
			RpgUtils.logError("No such node["+name+"] within ["+getName()+"]. Alert failed");
		}else{
			return n.onAlert(e);
		}
		return false;
	}

	@Override
	public boolean onAlert(NodeEvent e) {
		return false;
	}

	@Override
	public void onAdded() {
		event.setOrigin(this);
		for (int i = 0; i < getParent().getChildren().size; i++) {
			getParent().getChildren().get(i).onAlert(event);
		}
	}

	@Override
	public GameNode getRoot() {
		GameNode p = parent;
		if(p == NULL)
			return this;
		while (p.hasParent()) {
			p=p.getParent();
		}
		return p;
	}

	@Override
	public void onRemove() {
	}

	@Override
	public boolean hasParent() {
		return parent!=NULL;
	}

	@Override
	public float worldX() {
		if(getParent()==NULL)
			return getX();
		return getParent().worldX()+getX();
	}

	@Override
	public float worldY() {
		if(getParent()==NULL)
			return getY();
		return getParent().worldY()+getY();
	}

	@Override
	public void setUpdateOnVisibility(boolean enable) {
		update_on_visibility = enable;
	}

	@Override
	public RpgBase getApp() {
		if(app==null&&getParent()!=NULL){
			return getParent().getApp();
		}
		return app;
	}

	@Override
	public void setApp(RpgBase base) {
		this.app = base;
	}

	@Override
	public void clear() {
		children.clear();
	}

}
