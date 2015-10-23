package Client;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import Resources.Config;

public class aboutJFrame extends JFrame {
	
	
	public aboutJFrame(){
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
