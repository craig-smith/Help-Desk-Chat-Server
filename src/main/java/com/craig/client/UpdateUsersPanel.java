package com.craig.client;

import com.craig.resources.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;



public class UpdateUsersPanel implements ActionListener, MouseListener{
	
	private JPanel userArea;
	private Timer timer;
	private ConcurrentHashMap<String, User> users;
	private int shortBreak = 0;
	private int lunch = 0;
	private int _default = 0;
	private int in = 0;
	
	private JLabel inCount;
	private JLabel shortBreakCount;
	private JLabel _defaultCount;
	private JLabel lunchCount;
	
	public UpdateUsersPanel(JPanel userArea, ConcurrentHashMap<String, User> users){
		this.users = users;
		this.userArea = userArea;
		timer = new Timer(250, this);
		timer.setRepeats(true);
		timer.start();
		lunchCount = new JLabel(String.format("%-28s%d","Agents on Lunch: ",lunch));
		
		lunchCount.setFont(new Font("Monospaced", Font.PLAIN, 12));
		lunchCount.addMouseListener(this);
		
		_defaultCount = new JLabel(String.format("%-28s%d","Agents on defalut: ", _default));
		
		_defaultCount.setFont(new Font("Monospaced", Font.PLAIN, 12));
		_defaultCount.setToolTipText(listAgentsOnAux(User.DEFAULT));
		_defaultCount.addMouseListener(this);
		
		shortBreakCount = new JLabel(String.format("%-28s%d","Agents on Short Break: ", shortBreak));
		
		shortBreakCount.setFont(new Font("Monospaced", Font.PLAIN, 12));
		shortBreakCount.setToolTipText(listAgentsOnAux(User.SHORTBREAK));
		shortBreakCount.addMouseListener(this);
		
		inCount = new JLabel(String.format("%-28s%d","Agents in: ", in ));
		
		inCount.setFont(new Font("Monospaced", Font.PLAIN, 12));
		inCount.setToolTipText(listAgentsOnAux(User.IN));
		inCount.addMouseListener(this);
		
		userArea.add(shortBreakCount);
		userArea.add(lunchCount);
		userArea.add(inCount);
		userArea.add(_defaultCount);
		addUsersToPane();
		reloadColors();
	}
	
	private void writeUsers(){
		resetCounters();
		Iterator<String> keySetIterator = users.keySet().iterator();
    	while(keySetIterator.hasNext()){
    		String key = keySetIterator.next();
    		User user = users.get(key);
    		countUsers(user);
    		user.toString();
    		
    	}
    	
    	printCounters();
    }
	
	private void countUsers(User user){
		switch(user.getAuxValue()){
		case(User.SHORTBREAK):{
			shortBreak ++;
			break;
		}
		case(User.IN):{
			in++;
			break;
		}
		case(User.LUNCH):{
			lunch ++;
			break;
			
		}
		case(User.DEFAULT):{
			_default ++;
			break;
		}
		}
	}
private void resetCounters(){
	lunch = 0;
	in = 0;
	_default = 0;
	shortBreak = 0;
	
}

public int getLunch(){
	return lunch;
}

private void printCounters(){
	lunchCount.setText(String.format("%-28s%d","Agents on Lunch: ", lunch) );
	shortBreakCount.setText(String.format("%-28s%d","Agents on Short Break: ", shortBreak ));
	inCount.setText(String.format("%-28s%d","Agents in: ", in ));
	_defaultCount.setText(String.format("%-28s%d","Agents on defalut: ", _default));
}

private void addUsersToPane(){
	Iterator<String> keySetIterator = users.keySet().iterator();
	while(keySetIterator.hasNext()){
		String key = keySetIterator.next();
		User user = users.get(key);
		userArea.add(user);
		userArea.validate();
	}
}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		writeUsers();
	}

	public void addUser(String user) {
		userArea.add(users.get(user));
		userArea.validate();
	}
	
	public void removeUser(String name){
		userArea.remove(users.get(name));
		userArea.repaint();
	}
	
	private String listAgentsOnAux(int auxValue){
		StringBuilder agents = new StringBuilder();
		agents.append("<HTML><p>");
		Iterator<String> i = users.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			if(users.get(key).getAuxValue() == auxValue){
				agents.append(users.get(key).getName() + "<br> ");
			}
		}
		agents.append("</p></HTML>");
		return agents.toString();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		JLabel label = (JLabel) arg0.getSource();
		if(label.getText() == inCount.getText()){
			label.setToolTipText(listAgentsOnAux(User.IN));
			
		}
		if(label.getText() == _defaultCount.getText()){
			label.setToolTipText(listAgentsOnAux(User.DEFAULT));
		}
		if(label.getText() == lunchCount.getText()){
			label.setToolTipText(listAgentsOnAux(User.LUNCH));
		}
		if(label.getText() == shortBreakCount.getText()){
			
			label.setToolTipText(listAgentsOnAux(User.SHORTBREAK));
		}
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
	
	public void reloadColors(){
		lunchCount.setForeground(new Color(Config.getColorProperty(Config.LUNCH_LABEL_COLOR_R),
				Config.getColorProperty(Config.LUNCH_LABEL_COLOR_G), 
				Config.getColorProperty(Config.LUNCH_LABEL_COLOR_B)));
		_defaultCount.setForeground(new Color(Config.getColorProperty(Config.DEFAULT_LABEL_COLOR_R),
				Config.getColorProperty(Config.DEFAULT_LABEL_COLOR_G), 
				Config.getColorProperty(Config.DEFAULT_LABEL_COLOR_B)));
		shortBreakCount.setForeground(new Color(Config.getColorProperty(Config.SHORT_BREAK_LABEL_COLOR_R),
				Config.getColorProperty(Config.SHORT_BREAK_LABEL_COLOR_G), 
				Config.getColorProperty(Config.SHORT_BREAK_LABEL_COLOR_B)));
		inCount.setForeground(new Color(Config.getColorProperty(Config.IN_LABEL_COLOR_R), 
				Config.getColorProperty(Config.IN_LABEL_COLOR_G), 
				Config.getColorProperty(Config.IN_LABEL_COLOR_B)));
		updateUsersColors();
		
	}
	
	private void updateUsersColors(){
		Iterator<String> i = users.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			int aux = users.get(key).getAuxValue();
			switch (aux){
			case User.DEFAULT:{
				users.get(key).setForeground(_defaultCount.getForeground());
				break;
			}
			case User.IN:{
				users.get(key).setForeground(inCount.getForeground());
				break;
			}
			case User.LUNCH:{
				users.get(key).setForeground(lunchCount.getForeground());
				break;
			}
			case User.SHORTBREAK:{
				users.get(key).setForeground(shortBreakCount.getForeground());
			}
			}
		}
	}
}
