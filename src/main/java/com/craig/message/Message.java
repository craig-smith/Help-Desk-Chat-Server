package com.craig.message;

import java.io.Serializable;

import javax.swing.JOptionPane;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;
	private String name;
	private int type;
	private String message;
	private long auxTime;
	public static final int CHAT_MESSAGE = 1; 
	public static final int AUX_CHANGE = 2;
	public static final int LOG_ON	= 3;
	public static final int LOG_OFF = 4;
	public static final int PRIVATE_MESSAGE = 5;
	public static final int SERVER_MESSAGE = 6;
	public static final int KEEP_ALIVE = 7;
	public static final int RECONNECT = 8;
	private long totalIn = 0;
	private long totalShortBreak = 0;
	private long totalDefault = 0;
	private long totalLunch = 0;
	
	public Message(String name, String message, int type) {
		this.name = name;
		this.type = type;
		this.message = message;
		this.auxTime = System.currentTimeMillis();
		
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public int getType(){
		return this.type;
	}
	
	public String getName(){
		return this.name;
	}
	//code to prevent the string from urls extending the frame size and sending whole books across the wire...
	public boolean setMessage(String message){
		if(message.length() < 1000 && message.length() > 1){
		int count = 0;
		StringBuilder brokenString = new StringBuilder();
		
		for(int i = 0; i < message.length(); i++){
			if(Character.isWhitespace(message.charAt(i))){ 
				brokenString.append(message.charAt(i));
				count = 0;
			}else{
				count ++;
				brokenString.append(message.charAt(i));
				if(count > 65){
					brokenString.append("\r\n");
					count = 0;
				}
			}
			
			
		}
		
		this.message = brokenString.toString();
		return true;
		} else {
			JOptionPane.showMessageDialog(null, "You have entered too much text or, \n your " +
					"message was empty. \n Please try again.");//end length test
			return false;
		}
	}

	public void setType(int type) {
		this.type = type;
		if(type == Message.AUX_CHANGE){
			this.auxTime = System.currentTimeMillis();
		}
	}

	public long getAuxTime(){
		return this.auxTime;
	}
	public void setAuxTime(long auxTime){
		this.auxTime = auxTime;
	}

	public long getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(long totalIn) {
		this.totalIn = totalIn;
	}

	public long getTotalShortBreak() {
		return totalShortBreak;
	}

	public void setTotalShortBreak(long totalShortBreak) {
		this.totalShortBreak = totalShortBreak;
	}

	public long getTotalDefault() {
		return totalDefault;
	}

	public void setTotalDefault(long totalDefault) {
		this.totalDefault = totalDefault;
	}

	public long getTotalLunch() {
		return totalLunch;
	}

	public void setTotalLunch(long totalLunch) {
		this.totalLunch = totalLunch;
	}
	
	
}
