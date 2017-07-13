package com.rpg.game.nodes;

import com.rpg.game.utils.RpgUtils;

public class TileLayerNode extends BasicNode{
	//array of tiles
	private TileNode[] tiles;
	//layer width and height in tiles
	private int twidth,theight;
	public TileLayerNode(int width,int height){
		this.twidth = width;
		this.theight = height;
		tiles = new TileNode[width*height];
	}
	/**
	 * The layers width measured in tiles;
	 * @return
	 */
	public int getLayerWidth(){
		return this.twidth;
	}
	/**
	 * the layers height measured in tiles.
	 * @return
	 */
	public int getLayerHeight(){
		return this.theight;
	}
	/**
	 *set the tile at the given location. if there was already a tile there
	 *then remove it and add the new tile to the layer node
	 * @param x
	 * @param y
	 * @param tile
	 */
	public void setTile(int x,int y,TileNode tile){
		if(x<0 || x > twidth || y < 0 || y > theight){
			RpgUtils.logError("value is not widthing the layer bounds[w:"+twidth+" h:"+theight+"]");
			return;
		}
		int index = twidth*y+x;
		if(tiles[index]!=null){
			tiles[index].remove();
		}
		tiles[index] = tile;
		addChild(tiles[index]);
	}
	/**
	 * get the tile at the given location.
	 * Must be within layer bounds
	 * @param x
	 * @param y
	 * @return
	 */
	public TileNode getTile(int x,int y){
		if(x<0 || x > twidth || y < 0 || y > theight){
			RpgUtils.logError("value is not widthing the layer bounds[w:"+twidth+" h:"+theight+"]");
			return null;
		}
		int index = twidth*y+x;
		return tiles[index];
	}

}
