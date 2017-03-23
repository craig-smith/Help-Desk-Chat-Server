package com.craig.server;

import com.craig.message.Message;
import com.craig.resources.WriteStats;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;



public class ChatServer2 {
	
	private static HashMap<String, ConnectedClient> clients = new HashMap<String, ConnectedClient>();
	
	private static final int PORT = 9001;
	
	private static Object lock = new Object();
	
	private static ServerGui gui;
	
	private static ThreadLocal<Boolean> run = new ThreadLocal<Boolean>(){
	
		@Override
		protected Boolean initialValue(){
			return Boolean.TRUE;
		}
		
		
	};
	
	public static void main(String args[]) throws Exception{
		System.out.println("The chat server is running.");
		gui = new ServerGui();
		ServerSocket listener = new ServerSocket(PORT);
		new Thread(new Runnable() {
			@Override
			public void run(){
				TimerTask keepAlive = new SendKeepAlive(clients, lock);
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(keepAlive, 5*60*1000, 5*60*1000);
			}
		}).start();
		
		new Thread(new Runnable(){
			@Override
			public void run(){
				TimerTask removeInactiveClients = new RemoveInactiveClients(clients, lock);
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(removeInactiveClients, 60*60*1000, 60*60*1000);
			}
		}).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				TimerTask reset = new ClearClients(clients, lock);
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(reset,  ((ClearClients) reset).getTime(), 24*60*60*1000);
				
			}
			
		}).start();
		try{
			while(true) {
				new Handler(listener.accept()).start();
			}
		}finally {
			listener.close();
		}
	}
	
	protected static class Handler extends Thread {
		
		private Socket socket;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private Message clientMessage;
		public Handler(Socket socket){
			this.socket = socket;
		}
		
		public void run(){
			run.set(true);
			
				try {
					in = new ObjectInputStream(socket.getInputStream());
					out = new ObjectOutputStream(socket.getOutputStream());
					
					while(true){
						ConnectedClient client = logIn();
						sendNewUserCurrentStats(client);
						if(client.getTotalDefault() > 0){
							Message msg = new Message(client.getName(), "", Message.RECONNECT);
							msg.setTotalDefault(client.getTotalDefault());
							msg.setTotalIn(client.getTotalIn());
							msg.setTotalLunch(client.getTotalLunch());
							msg.setTotalShortBreak(client.getTotalShortBreak());
							sendNewLogin(msg);
						}else{
							sendNewLogin(new Message(client.getName(), "", Message.LOG_ON));
						}
						gui.addClient(client.getName());
						break;					
					}
				
			
				
				while(run.get()){
					Message msg = (Message) in.readObject();
					synchronized(lock){
						logCurrentStats(msg.getName(), msg);
							
						if(msg.getType() == Message.LOG_OFF){
							
							 logOut(clients.get(msg.getName()), msg);
								
							
							
						}else if(msg.getType() == Message.AUX_CHANGE){
							
								auxChange(clients.get(msg.getName()), msg);
							
							
						}else if(msg.getType() == Message.CHAT_MESSAGE){
							
								sendChatMessage(msg);
							
							
						}
					}
				}
			}catch(IOException | ClassNotFoundException e){
				e.printStackTrace();
				
			}finally{
				
				}
			
		}
		
		
		private ConnectedClient logIn() throws IOException, ClassNotFoundException{
			out.writeObject(new Message("Server", "login please", Message.SERVER_MESSAGE));
			out.flush();
			
			clientMessage = (Message)in.readObject();
			if(clientMessage == null){
				return null;
			}
			synchronized(lock){	
			if(!clients.containsKey(clientMessage.getName())){
								
					ConnectedClient client = new ConnectedClient(clientMessage.getName(), out);
					clients.put(clientMessage.getName(), client);
					Message msg = new Message("Server", "login accepted", Message.SERVER_MESSAGE);
					client.setCurrentAuxValue(ConnectedClient.DEFAULT);
					sendMessage(client, msg);
					WriteStats.writeServerStats(client);
					return client;
				}
					
				else {
					ConnectedClient client = clients.get(clientMessage.getName());
					client.setOut(out);
					Message msg = new Message("Server", "login accepted", Message.SERVER_MESSAGE);
					client.setCurrentAuxValue(ConnectedClient.DEFAULT);
					sendMessage(client, msg);
					WriteStats.writeServerStats(client);
					return client;
				}
			}
			
			
			
			
		}
		
		private static void sendMessage(ConnectedClient client, Message msg) {
			synchronized(lock){
				boolean isActive = client.sendMessage(msg);
				if(!isActive){
					//run.set(false);
				}
				
			}
			
		}
		
		private void sendNewUserCurrentStats(ConnectedClient client){
			Message msg = null;
			synchronized(lock){
				
				Iterator<String> i = clients.keySet().iterator();
				while(i.hasNext()){
					
					String cc = i.next();
					if(cc != client.getName()){						
						ConnectedClient c = clients.get(cc);
							if(c.isActive()){
								msg = new Message(c.getName(), c.getCurrentAuxValue(), Message.LOG_ON);
								msg.setMessage(c.getCurrentAuxValue());
								msg.setAuxTime(c.getAuxTime());
								msg.setTotalDefault(c.getTotalDefault());
								msg.setTotalIn(c.getTotalIn());
								msg.setTotalLunch(c.getTotalLunch());
								msg.setTotalShortBreak(c.getTotalShortBreak());
								
								
									sendMessage(client, msg);
							}
						
					}
				}
			}
			
			
			
		}
		
		private void sendNewLogin(Message msg){
			synchronized(lock){
				Iterator<String> i = clients.keySet().iterator();
				while(i.hasNext()){
					String cc = i.next();
					ConnectedClient  c = clients.get(cc);
					sendMessage(c, msg);
					
				}
			}
		}
		
		private void sendChatMessage(Message msg){
			synchronized(lock){
				Iterator<String> iterator = clients.keySet().iterator();
				while(iterator.hasNext()){
					String key = iterator.next();					
					sendMessage(clients.get(key), msg);
					
				}
			}
		}
		
		private void logOut(ConnectedClient client, Message msg){
			synchronized(lock){
				Iterator<String> iterator = clients.keySet().iterator();
				while(iterator.hasNext()){
					String key = iterator.next();
					
					if(!key.equals(msg.getName())){
					sendMessage(clients.get(key), msg);
											
					}else{
						gui.removeClient(msg.getName());
						clients.get(key).closeOut();
						run.set(false);
					}
					
				
				}
			
			}
		}
		
		private void auxChange(ConnectedClient client, Message msg){
			synchronized(lock){
				client.setCurrentAuxValue(msg.getMessage());
				client.setTotalDefault(msg.getTotalDefault());
				client.setTotalIn(msg.getTotalIn());
				client.setTotalLunch(msg.getTotalLunch());
				client.setTotalShortBreak(msg.getTotalShortBreak());
				client.setAuxTime(msg.getAuxTime());
				WriteStats.writeServerStats(client);
				Iterator<String> iterator = clients.keySet().iterator();
				while(iterator.hasNext()){
					String key = iterator.next();			
					sendMessage(clients.get(key), msg);					
				}
			}
			
		}
		
		
		

	

	public static void removeClient(String clientName) {
		synchronized(lock){
			
			Message msg = new Message(clientName, "logout", Message.LOG_OFF);
			Iterator<String> iterator = clients.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
					if(clients.get(key).isActive()){
					sendMessage(clients.get(key), msg);
					}
			}
			clients.get(clientName).closeOut();
			
		}
		
	}

	
	public static void exit() {
		synchronized(lock){
			Iterator<String> iterator = clients.keySet().iterator();
			while(iterator.hasNext()){
				String c = iterator.next();
					if(clients.get(c).isActive()){
					clients.get(c).closeOut();
					clients.remove(c);
					}
			}
			System.exit(0);
		}
		
	}
	
	private void logCurrentStats(String client, Message msg){
		synchronized(lock){
			ConnectedClient cc = clients.get(client);
			cc.setTotalDefault(msg.getTotalDefault());
			cc.setTotalIn(msg.getTotalIn());
			cc.setTotalLunch(msg.getTotalLunch());
			cc.setTotalShortBreak(msg.getTotalShortBreak());
			
		}
	}

	
		
	
	

	
}

	
		
	

	
	
}

	
		
	

