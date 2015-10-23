package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Resources.Config;
import Resources.WriteStats;
import Resources.resources;

public class MenuBar extends JMenuBar implements ActionListener{
	JMenuItem customTheme;
	JMenuItem pinkTheme;
	JMenuItem greyTheme;
	JMenuItem blackTheme;
	JMenuItem saveCurrentTheme;
	JMenuItem aboutItem;
	JMenuItem saveStats;
	JMenu file;
	JMenu about;
	JMenu options;
	private ChatClient client;
	
	public MenuBar(ChatClient client){
		this.client = client;
		pinkTheme = new JMenuItem("Load Pink Theme");
		pinkTheme.addActionListener(this);
		
		greyTheme = new JMenuItem("Load Grey Theme");
		greyTheme.addActionListener(this);
		
		saveCurrentTheme = new JMenuItem("Save Current Theme");
		saveCurrentTheme.addActionListener(this);
		
		aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(this);
		
		saveStats = new JMenuItem("Save Stats");
		saveStats.addActionListener(this);
		
		file = new JMenu("File");
		add(file);
		
		options = new JMenu("Options");
		add(options);
		
		about = new JMenu("About");
		add(about);
		
		
		customTheme = new JMenuItem("Create Custom Theme");
		customTheme.addActionListener(this);
		
		blackTheme = new JMenuItem("Load Black Theme");
		blackTheme.addActionListener(this);
		
		
		about.add(aboutItem);
		file.add(saveStats);
		options.add(customTheme);
		options.add(greyTheme);
		options.add(pinkTheme);
		options.add(blackTheme);
		options.add(saveCurrentTheme);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JMenuItem item = (JMenuItem) arg0.getSource();
		
		if(item == customTheme){
			OptionsWindow optionsWindow = new OptionsWindow(client);
			optionsWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			optionsWindow.setVisible(true);
		}else if(item == greyTheme){
			InputStream file = this.getClass().getResourceAsStream("/grey.properties");
			try {
				Config.loadDefaultProperties(file);
				client.reloadColors();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(item == pinkTheme){
			InputStream file = this.getClass().getResourceAsStream("/pink.properties");
			try {
				Config.loadDefaultProperties(file);
				client.reloadColors();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(item == saveCurrentTheme ){
			try {
				Config.writeProperties();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(item == blackTheme){
			InputStream file = this.getClass().getResourceAsStream("/black.properties");
			try {
				Config.loadDefaultProperties(file);
				client.reloadColors();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(item == aboutItem){
			JFrame frame = new aboutJFrame();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			//frame.setSize(100, 100);
			frame.setVisible(true);
		}else if(item == saveStats){
			WriteStats write = new WriteStats();
			write.writeUserStats();
		}
		
	}

}
