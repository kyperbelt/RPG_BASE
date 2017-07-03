package com.rpg.game.nodes;

import com.rpg.game.nodes.event.TimerEvent;
import com.rpg.game.utils.RpgUtils;

/**
 * Node to add timer utility 
 * 
 * @author kyperbelt
 *
 */
public class TimerNode extends BasicNode{
	
	private boolean repeat;
	private boolean started;
	private boolean paused;
	private float elapsed;
	private float duration;
	private int repeat_count;
	private TimerEvent event;
	
	public TimerNode(float duration,boolean repeat){
		setName("timer_node");
		started = false;
		elapsed = 0;
		this.duration = duration;
		this.repeat = repeat;
		setVisible(false);
		event = new TimerEvent(this, duration,repeat_count);
	}
	
	public void start(){
		elapsed = 0;
		paused = false;
		started = false;
	}
	
	public void stop(){
		started = false;
	}
	
	public void pause(){
		paused = true;
	}
	
	public void resume(){
		paused = false;
	}
	
	@Override
	public void addChild(GameNode node) {
		RpgUtils.logError("You Should not add nodes to a timer node. They will not be updated or drawn. ");
		super.addChild(node);
	}
	
	@Override
	public void update(float delta) {
		if(getParent() == NULL || !started || paused)
			return;
		
		elapsed+=delta;
		if(elapsed>=duration){
			event.set(delta, repeat_count);
			if(!getParent().alertAll(event))
				RpgUtils.logError("Timer event "+getName()+"is not being handled.");;
			if(repeat){
				repeat_count++;
				elapsed = elapsed-duration;
			}
		}
	}
	
	

}
