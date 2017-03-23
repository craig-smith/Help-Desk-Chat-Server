package com.craig.server;

import com.craig.message.Message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TimerTask;



public class SendKeepAlive extends TimerTask {

	private HashMap<String, ConnectedClient> clients;
	Object lock;
	public SendKeepAlive(HashMap<String, ConnectedClient> clients, Object lock){
		this.clients = clients;
		this.lock = lock;
		System.out.println("Keep alive initialized");
	}
	@Override
	public void run() {
		System.out.println("keep_alive started!!");
		
			synchronized(lock){
				Iterator<String> iterator = clients.keySet().iterator();
				Message msg = new Message("Server", "", Message.KEEP_ALIVE);
				while(iterator.hasNext()){
					String key = iterator.next();
					if(clients.get(key).isActive()){
						clients.get(key).sendMessage(msg);
					}
				}
				
			}
			

	}

}
