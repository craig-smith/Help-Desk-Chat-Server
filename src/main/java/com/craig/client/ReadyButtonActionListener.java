package com.craig.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ReadyButtonActionListener implements ActionListener {
	private ChatClient client;
	public ReadyButtonActionListener(JButton button, ChatClient client){
		//button.addActionListener(this);
		this.client = client;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		client.ready();

	}

}
