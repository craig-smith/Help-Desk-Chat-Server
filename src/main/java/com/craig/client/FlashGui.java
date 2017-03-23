package com.craig.client;



import com.craig.resources.Resources;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;




public class FlashGui implements ActionListener{

	
	private ChatClient client;
	private Timer timer;
	private Boolean newMessage = false;
	private Boolean whitePic = false;
	private Boolean soundPlayed;
	
	public FlashGui(ChatClient client){
		this.client = client;
		timer = new Timer(300, this);
		timer.setRepeats(true);
		timer.start();
	}
	
	
	
	public void setNewMessage(Boolean msg){
		newMessage = msg;
		soundPlayed = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(newMessage == true && client.isActive() == false){
			if(!client.testing){
			if(soundPlayed == false){
			Resources sound = new Resources();
			try {
				sound.playSound(Resources.sound);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			soundPlayed = true;
			}//end if for testing
			}
			if(whitePic == true){
				client.setIconImage(new Resources().getIcon(Resources.blackPic));
				whitePic = false;
			}
			else{
				client.setIconImage(new Resources().getIcon(Resources.whitePic));
				whitePic = true;
			}
		}
		else{
			newMessage = false;
		}
		
	}
		
}
	


