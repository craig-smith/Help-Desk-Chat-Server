package Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Client.ChatClient;

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
