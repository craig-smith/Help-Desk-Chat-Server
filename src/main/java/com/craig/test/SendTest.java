package com.craig.test;

import com.craig.client.ChatClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SendTest implements ActionListener {
	ChatClient client;
	public SendTest(ChatClient client){
		this.client = client;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String s1 = RandomString.getRandomString();
		
		client.sendTest(s1);

	}

}
