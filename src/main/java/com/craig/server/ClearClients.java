package com.craig.server;

import com.craig.message.Message;
import com.craig.resources.WriteStats;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimerTask;


public class ClearClients extends TimerTask {

	HashMap<String, ConnectedClient> clients;
	Object lock;
	
	public ClearClients(HashMap<String, ConnectedClient> clients, Object lock){
		this.clients = clients;
		this.lock = lock;
		System.out.println("ClearClients Initialized");
	}
	@Override
	public void run() {
		synchronized(lock){
		System.out.println("resetting clients!!");
		Iterator<String> iterator = clients.keySet().iterator();
		
		while(iterator.hasNext()){
			String cc = iterator.next();
			ConnectedClient client = clients.get(cc);
			client.setAuxTime(0);
			client.setTotalDefault(0);
			client.setTotalIn(0);
			client.setTotalLunch(0);
			client.setTotalShortBreak(0);
			client.setCurrentAuxValue(ConnectedClient.DEFAULT);
			sendReset(client);
			WriteStats.writeServerStats(client);
			
		}
		
	}

	}
	
	private void sendReset(ConnectedClient resetClient){
		synchronized(lock){
			
			Message msg = new Message(resetClient.getName(), "Reset", Message.AUX_CHANGE);
			msg.setAuxTime(System.currentTimeMillis());
			msg.setTotalDefault(0);
			msg.setTotalIn(0);
			msg.setTotalLunch(0);
			msg.setTotalShortBreak(0);
			resetClient.sendMessage(msg);
			}
		}
	
	
	public Date getTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 5);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date date = cal.getTime();
		return date;
		
	}

}
