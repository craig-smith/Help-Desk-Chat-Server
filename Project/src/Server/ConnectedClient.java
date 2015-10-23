package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;

import Message.Message;

public class ConnectedClient {
	
	private String name;
	private long totalIn = 0;
	private long totalShortBreak = 0;
	private long totalDefault = 0;
	private long totalLunch = 0;	
	private String currentAuxValue;
	private int inactiveHours = 0;
	public final static String DEFAULT = "Default";
	public final static String READY = "Ready";
	public final static String Lunch = "Lunch";
	public final static String SHORT_BREAK = "Short Break";
	

	private ObjectOutputStream out;
	private boolean outIsGood;
	private long auxTime;
	
	public ConnectedClient(String name, ObjectOutputStream out){
		this.name = name;
		this.out = out;
		outIsGood = true;
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

	public String getName(){
		return name;
	}
	public String getCurrentAuxValue() {
		return currentAuxValue;
	}
	public void setCurrentAuxValue(String currentAuxValue) {
		this.currentAuxValue = currentAuxValue;
	}
	
	public boolean sendMessage(Message msg){
		if(outIsGood){
			try {
				out.writeObject(msg);
				out.flush();
				out.reset();
				return true;
			} catch (IOException e) {
				outIsGood = false;
				
				//out.close();
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public void setOut(ObjectOutputStream out){
		this.out = out;
		outIsGood = true;
	}
	
	public void closeOut(){
		if(outIsGood){
			outIsGood = false;
			
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isActive(){
		return outIsGood;
	}
	public long getAuxTime() {
		return this.auxTime;
	}
	public void setAuxTime(long auxTime){
		this.auxTime = auxTime;
	}
	public int checkInactiveHours(){
		if(isActive()){
			inactiveHours = 0;
		}else {
			inactiveHours ++;
		}
		return inactiveHours;
	}

}
