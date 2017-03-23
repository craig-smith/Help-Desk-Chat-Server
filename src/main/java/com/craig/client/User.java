package com.craig.client;

import com.craig.resources.Config;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class User extends JLabel implements MouseListener{
	
	
	private static final long serialVersionUID = 1L;
	private String name;
	private int auxValue;
	private long time;	
	public static final int SHORTBREAK = 1;
	public static final int IN = 2;
	public static final int LUNCH = 3;
	public static final int DEFAULT = 4;
	private long totalLunch = 0;
	private long totalShortBreak = 0;
	private long totalIn = 0;
	private long totalDefault = 0;
	
	
	public User(String name){
		super.addMouseListener(this);
		super.setFont(new Font("Monospaced", Font.PLAIN, 12));
		this.name = name;
		
		this.time = System.currentTimeMillis();
		this.auxValue = 4;
	}
	
	public String getName(){
		return this.name;
	}
	public int auxValue(){
		return this.auxValue;
	}
	public String getTime(){
		long tempAuxTime = System.currentTimeMillis() - time;
		int hours = (int) TimeUnit.MILLISECONDS.toHours(tempAuxTime);
		int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(tempAuxTime) - (int)TimeUnit.HOURS.toMinutes(hours);
		int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(tempAuxTime) - (int) TimeUnit.MINUTES.toSeconds(minutes) - (int) TimeUnit.HOURS.toSeconds(hours);
		
		String auxTime = String.format("%d:%02d:%02d", hours, minutes, seconds);
		return auxTime;
	}
	public long getTime(boolean now){
		long tempTime = System.currentTimeMillis();
		return tempTime - time;
	}
	public void setauxValue(int inauxValue, long time){
		setTotalTime(auxValue);
		
		this.auxValue = inauxValue;
		this.time = time;
		setUserColor();
	}
	
	public int getAuxValue(){
		return auxValue;
	}
	public long getTotalLunch(){
		if(auxValue == User.LUNCH){
			return System.currentTimeMillis() - time + this.totalLunch;
		}
		else{ 
			return this.totalLunch;
		}
	}
	public void setTotalLunch(long totalLunch){
		this.totalLunch = totalLunch;
	}
	public long getTotalDefault(){
		if(auxValue == User.DEFAULT){
			return System.currentTimeMillis() - time + this.totalDefault;
		}
		else {
			return this.totalDefault;
		}
	}
	public void setTotalDefault(long totalDefault){
		this.totalDefault = totalDefault;
	}
	public long getTotalIn(){
		if(auxValue == User.IN){
			return System.currentTimeMillis() - time + this.totalIn;
		}
		else {
			return this.totalIn;
		}
	}
	public void setTotalIn(long totalIn){
		this.totalIn = totalIn;
	}
	public long getTotalShortBreak(){
		if(auxValue == User.SHORTBREAK){
			return System.currentTimeMillis() - time + this.totalShortBreak;
		}
		else {
			return this.totalShortBreak;
		}
	}
	public void setTotalShortBreak(long totalShortBreak){
		this.totalShortBreak = totalShortBreak;
	}
	private void setUserColor(){
		switch(auxValue){
		case(User.DEFAULT):{
			this.setForeground(new Color(Config.getColorProperty(Config.DEFAULT_LABEL_COLOR_R),
					Config.getColorProperty(Config.DEFAULT_LABEL_COLOR_G), 
					Config.getColorProperty(Config.DEFAULT_LABEL_COLOR_B)));
			break;
		}
		case(User.IN):{
			this.setForeground(new Color(Config.getColorProperty(Config.IN_LABEL_COLOR_R), 
					Config.getColorProperty(Config.IN_LABEL_COLOR_G), 
					Config.getColorProperty(Config.IN_LABEL_COLOR_B)));
			break;
		}
		case(User.LUNCH):{
			this.setForeground(new Color(Config.getColorProperty(Config.LUNCH_LABEL_COLOR_R),
					Config.getColorProperty(Config.LUNCH_LABEL_COLOR_G), 
					Config.getColorProperty(Config.LUNCH_LABEL_COLOR_B)));			
			break;
		}
		case(User.SHORTBREAK):{
			this.setForeground(new Color(Config.getColorProperty(Config.SHORT_BREAK_LABEL_COLOR_R), 
					Config.getColorProperty(Config.SHORT_BREAK_LABEL_COLOR_G), 
					Config.getColorProperty(Config.SHORT_BREAK_LABEL_COLOR_B)));
		}
		}
	}
	private String getAuxString(){
		String auxString = "";
		switch (auxValue){
		case 1:{
			auxString =  "Short Break";
			break;
		}
		case 2:{
			auxString =  "In";
			break;
		}
		case 3:{
			auxString =  "Lunch";
			break;
		}
		case 4:{
			auxString = "Default";
			break;
		}
		default: {
			auxString =  "ERROR";
			break;
		}
		}
		return auxString;
	}
	
	private void setTotalTime(int auxValue){
		switch(auxValue){
		case (User.SHORTBREAK):{
			totalShortBreak += System.currentTimeMillis() - time;
			break;
		}
		case (User.IN):{
			totalIn += System.currentTimeMillis() - time;
			break;
		}
		case (User.LUNCH):{
			totalLunch += System.currentTimeMillis() - time;
			break;
		}
		case (User.DEFAULT):{
			totalDefault += System.currentTimeMillis() - time;
			break;
		}
		
		}
	}
	
	private String showStats(boolean showDialog){
		switch(auxValue){
		case (User.SHORTBREAK):{
			long tempTime = System.currentTimeMillis() - time + totalShortBreak;
			String message = "Total In Time: " + getAuxTime(totalIn) + "\r\n" + 
					"Total Short Break time: " + getAuxTime(tempTime) + "\r\n" +
					"Total Lunch Time: " + getAuxTime(totalLunch) +  "\r\n" + 
					"Total Default Time: " + getAuxTime(totalDefault);
			if(showDialog == true){
				JOptionPane.showMessageDialog(this, message, this.getName() + "'s Stats", JOptionPane.DEFAULT_OPTION);
			}
					
					return message;
					//break;
		}
		case (User.IN):{
			long tempTime = System.currentTimeMillis() - time + totalIn;
			String message = "Total In Time: " + getAuxTime(tempTime) + "\r\n" + 
					"Total Short Break time: " + getAuxTime(totalShortBreak) + "\r\n" +
					"Total Lunch Time: " + getAuxTime(totalLunch) +  "\r\n" + 
					"Total Default Time: " + getAuxTime(totalDefault);
			if(showDialog == true){
				JOptionPane.showMessageDialog(this, message, this.getName() + "'s Stats", JOptionPane.DEFAULT_OPTION);
			}					return message;
					//break;
		}
		case (User.LUNCH):{
			long tempTime = System.currentTimeMillis() - time + totalLunch;
			String message = "Total In Time: " + getAuxTime(totalIn) + "\r\n" + 
					"Total Short Break time: " + getAuxTime(totalShortBreak) + "\r\n" +
					"Total Lunch Time: " + getAuxTime(tempTime) +  "\r\n" + 
					"Total Default Time: " + getAuxTime(totalDefault);
			if(showDialog == true){
				JOptionPane.showMessageDialog(this, message, this.getName() + "'s Stats", JOptionPane.DEFAULT_OPTION);
			}					return message;
					//break;
		}
		case (User.DEFAULT):{
			long tempTime = System.currentTimeMillis() - time + totalDefault;
			String message = "Total In Time: " + getAuxTime(totalIn) + "\r\n" + 
					"Total Short Break time: " + getAuxTime(totalShortBreak) + "\r\n" +
					"Total Lunch Time: " + getAuxTime(totalLunch) +  "\r\n" + 
					"Total Default Time: " + getAuxTime(tempTime);
			if(showDialog == true){
				JOptionPane.showMessageDialog(this, message, this.getName() + "'s Stats", JOptionPane.DEFAULT_OPTION);
			}					return message;
					//break;
		}
		}
		return null;
		
	}
	private String getAuxTime(long tempAuxTime){
		int hours = (int) TimeUnit.MILLISECONDS.toHours(tempAuxTime);
		int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(tempAuxTime) - (int)TimeUnit.HOURS.toMinutes(hours);
		int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(tempAuxTime) - (int) TimeUnit.MINUTES.toSeconds(minutes) - 
				(int) TimeUnit.HOURS.toSeconds(hours);
		
		String auxTime = String.format("%d:%02d:%02d", hours, minutes, seconds);
		return auxTime;
	}
	
	public String writeAuxTime(){
		return showStats(false);
	}
	@Override 
	public String toString(){
		
		
		String aux = String.format("%-15s %-11s %s", name, getAuxString(), getTime());
		super.setText(aux);
		
		return aux;
	}

	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		showStats(true);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void reset() {
		totalIn = 0;
		totalDefault = 0;
		totalLunch = 0;
		totalShortBreak = 0;
		time = System.currentTimeMillis();
		auxValue = User.DEFAULT;
		setUserColor();
		
	}
	
	
}
