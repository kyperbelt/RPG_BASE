package com.rpg.game.nodes;

import java.util.Comparator;
/**
 * All children are ysorted
 * @author kyperbelt
 *
 */
public class YSortNode extends BasicNode{
	
	private YComparator comparator;
	
	public YSortNode(){
		setName("ysort_node");
		comparator = new YComparator();
		
	}
	
	public void update(float delta) {
		getChildren().sort(comparator);
		super.update(delta);
	}
	
	public class YComparator implements Comparator<GameNode>{

		@Override
		public int compare(GameNode o1, GameNode o2) {
			if(o1.getY() < o2.getY())
				return 1;
			else if(o1.getY() == o2.getY())
				return 0;
			else
				return -1;
		}
		
	}

}
