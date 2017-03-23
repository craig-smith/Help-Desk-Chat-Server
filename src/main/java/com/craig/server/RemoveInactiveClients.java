package com.craig.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TimerTask;

public class RemoveInactiveClients extends TimerTask {
	
	private HashMap<String, ConnectedClient> clients;
	private Object lock;
	public RemoveInactiveClients(HashMap<String, ConnectedClient> clients, Object lock){
		this.clients = clients;
		this.lock = lock;
		System.out.println("RemoveInactivClients initialized");
	}

	@Override
	public void run() {
		synchronized(lock){
			System.out.println("Removing Inactive Clients");
			Iterator<String> iterator = clients.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				int hoursInactive = clients.get(key).checkInactiveHours();
				if(hoursInactive > 24){					
					ChatServer2.Handler.removeClient(clients.get(key).getName());
					clients.remove(key);
				}
			}
			
		}

	}

}
