package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class logOutButtonActionListener implements ActionListener {

	private ChatClient chatClient;
	
	public logOutButtonActionListener(JButton button, ChatClient chatClient){
		
		this.chatClient = chatClient;
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		chatClient.logOut();

	}

}
