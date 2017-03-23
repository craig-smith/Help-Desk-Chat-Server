package com.craig.client;

import com.craig.resources.Resources;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;



public class MainFrameWindowListener implements WindowListener{
	
	private ChatClient client;
	public MainFrameWindowListener(ChatClient client){
		this.client = client;
		
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		client.setIconImage(new Resources().getIcon(Resources.whitePic));
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		client.logOut();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
	

}
