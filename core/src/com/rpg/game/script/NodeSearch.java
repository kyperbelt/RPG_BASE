package com.rpg.game.script;

import com.badlogic.gdx.utils.Array;
import com.rpg.game.nodes.BasicNode;
import com.rpg.game.nodes.GameNode;

/**
 * simple interpreter to search node hierarchy 
 *  "..:player:collision" would search in the parent for a node called collision
 *  within the player node
 * @author kyperbelt
 *
 */
public class NodeSearch {
	
	/**
	 * Use the given node and do a search using the string given
	 * @param node
	 * @return
	 */
	public static GameNode findNode(GameNode node,String search){
		GameNode r = node;
		String[] search_tokens = search.split("/");
		for (int i = 0; i < search_tokens.length; i++) {
			String token = search_tokens[0];
			if(token.equals("..")){
				r = node.getParent();
				continue;
			}
			Array<GameNode> children = r.getChildren();
			for (int j = 0; j < children.size; j++) {
				if(children.get(j).getName().equals(token)){
					r = children.get(j);
					break;
				}
			}
		}
		return r;
	}
	
	public static GameNode findFirst(GameNode node,String c){
		GameNode r = BasicNode.NULL;
		for (int i = 0; i < node.getChildren().size; i++) {
			if(node.getChildren().get(i).getClass().equals(c)){
				return node.getChildren().get(i);
			}
		}
		return r;
	}
	

}
