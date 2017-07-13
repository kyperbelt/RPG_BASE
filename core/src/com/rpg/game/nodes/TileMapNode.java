package com.rpg.game.nodes;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.rpg.game.utils.RpgUtils;

public class TileMapNode extends BasicNode{
	
	private ObjectMap<String, TileLayerNode> layers;
	//the map width in tiles
	private int mapWidth;
	//the map height in tiles
	private int mapHeight;
	//the tile width in pixels
	private int tileWidth;
	//the tile height in pixels
	private int tileHeight;
	
	public TileMapNode(){
		setName("tile_map_node");
		layers = new ObjectMap<String, TileLayerNode>();
	}
	
	public void addLayer(TileLayerNode layer){
		if(layers.containsKey(layer.getName())){
			RpgUtils.logError("layers cannot have duplicate names ["+layer.getName()+"].");
			return;
		}
		layers.put(layer.getName(), layer);
		addChild(layer);
	}
	
	public void removeLayer(String layername){
		layers.remove(layername).remove();
	}
	
	public TileLayerNode geTileLayerNode(String name){
		return layers.get(name);
	}
	
	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}
	
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	@Override
	public float getWidth() {
		return mapWidth*tileWidth;
	}
	
	@Override
	public float getHeight() {
		return mapHeight*tileHeight;
	}
	
	public static TileMapNode load(TiledMap map){
		TileMapNode m = new TileMapNode();
		
		return m;
	}

}
