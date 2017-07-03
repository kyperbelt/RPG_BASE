package com.rpg.game.messages;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.rpg.game.utils.RpgUtils;

public class MessageManager {
	
	private static MessageManager manager;
	
	private Array<MessageListener> listeners;
	
	private MessageManager(){
		listeners = new Array<MessageListener>();
	}
	
	private void addMessageListener(MessageListener listener){
		int id = MathUtils.random(10000);
		while (containsID(id)) {
			id = MathUtils.random(10000);
		}
		listener.id = id;
		listener.setManager(this);
		listeners.add(listener);
	}
	
	protected void removeListener(MessageListener listener){
		for (int i = 0; i < listeners.size; i++) {
			if(listeners.get(i).id == listener.id){
				listeners.removeIndex(i);
			}
		}
	}
	
	private boolean containsID(int id){
		for (int i = 0; i < listeners.size; i++) {
			if(listeners.get(i).id == id){
				return true;
			}
		}
		return false;
	}
	
	private void sendMessage(Message m){
		for (int i = 0; i < listeners.size; i++) {
			listeners.get(i).handle(m);
			if(m.handled)
				break;
		}
		RpgUtils.logInfo("Message sent:"+m);
	}
	
	
	private static MessageManager getManager(){
		if(manager == null){
			manager = new MessageManager();
		}
		return  manager;
	}
	


	public static void sendMessage(Message m,Object...objects){
		m.reset();
		m.args.addAll(objects);
		getManager().sendMessage(m);
	}
	
	public static void addListener(MessageListener listener){
		getManager().addMessageListener(listener);
	}

}
