package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class LunchActionListener implements ActionListener {
	private ChatClient client;
	
	public LunchActionListener(JButton button, ChatClient client){
		//button.addActionListener(this);
		this.client = client;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		client.lunch();

	}

}
