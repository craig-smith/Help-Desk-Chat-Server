package com.craig.client;

import com.craig.resources.Config;

import javax.swing.*;

public class AboutJFrame extends JFrame {
	
	
	public AboutJFrame(){
		super("About");
		//setLayout(new FlowLayout());
		JPanel panel = new JPanel();
		JTextPane textArea = new JTextPane();
		
		String about = "Welcome to the Break Manager. \n" +
				"This application was created by Craig Smith. \n\n" +
				"Version " + Config.getStringProperty("version") ;
		
		textArea.setText(about);
		panel.add(textArea);
		super.add(panel);
		super.pack();
	}

}
