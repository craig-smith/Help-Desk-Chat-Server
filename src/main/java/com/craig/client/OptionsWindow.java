package com.craig.client;

import com.craig.resources.Config;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class OptionsWindow extends JFrame implements ActionListener, WindowListener {
	
	private ChatClient client;
	private JButton chatAreaButton;
	private JButton userAreaButton;
	private JButton borders;
	private JButton defaultUsers;
	private JButton inUsers;
	private JButton shortBreakUsers;
	private JButton lunchUsers;
	private JButton topPanel;
	private JButton buttonBackground;
	private JButton buttonPanelBackground;
	private JButton sendPanelButton;
	private JButton fontColor1;
	private JButton fontColor2;
	private JColorChooser colorPicker;
	private Color newColor;
	
	private final String GREEN = "green";
	private final String RED = "red";
	private final String BLUE = "blue";
		
	public OptionsWindow(ChatClient client){
		super("Options");
		BoxLayout mainLayout = new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS);
		super.setLayout(mainLayout);
		this.client = client;
		this.addWindowListener(this);
		colorPicker = new JColorChooser();
		
		
		chatAreaButton = new JButton("set");
		chatAreaButton.addActionListener(this);
		
		userAreaButton = new JButton("set");
		userAreaButton.addActionListener(this);
		
		borders = new JButton("set");
		borders.addActionListener(this);
		
		defaultUsers = new JButton("set");
		defaultUsers.addActionListener(this);
		
		inUsers = new JButton("set");
		inUsers.addActionListener(this);
		
		shortBreakUsers = new JButton("set");
		shortBreakUsers.addActionListener(this);
		
		lunchUsers = new JButton("set");
		lunchUsers.addActionListener(this);
		
		topPanel = new JButton("set");
		topPanel.addActionListener(this);
		
		buttonBackground = new JButton("set");
		buttonBackground.addActionListener(this);
		
		buttonPanelBackground = new JButton("set");
		buttonPanelBackground.addActionListener(this);
		
		sendPanelButton = new JButton("set");
		sendPanelButton.addActionListener(this);
		
		fontColor1 = new JButton("set");
		fontColor1.addActionListener(this);
		
		fontColor2 = new JButton("set");
		fontColor2.addActionListener(this);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		JLabel l1 = new JLabel(String.format("%-60s", "Set chat panel background color"));
		l1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel1.add(l1);
		panel1.add(chatAreaButton);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		JLabel l2 = new JLabel(String.format("%-60s","Set Users panel background color"));
		l2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel2.add(l2);
		panel2.add(userAreaButton);
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		JLabel l3 = new JLabel(String.format("%-60s","Set border color"));
		l3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel3.add(l3);
		panel3.add(borders);
		
		JPanel panel4 = new JPanel();
		panel4.setLayout(new FlowLayout());
		JLabel l4 = new JLabel(String.format("%-60s","Set Default users color"));
		l4.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel4.add(l4);
		panel4.add(defaultUsers);
		
		JPanel panel5 = new JPanel();
		panel5.setLayout(new FlowLayout());
		JLabel l5 = new JLabel(String.format("%-60s","Set In users color"));
		l5.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel5.add(l5);
		panel5.add(inUsers);
		
		JPanel panel6 = new JPanel();
		panel6.setLayout(new FlowLayout());
		JLabel l6 = new JLabel(String.format("%-60s","set short break users color"));
		l6.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel6.add(l6);
		panel6.add(shortBreakUsers);
		
		JPanel panel7 = new JPanel();
		panel7.setLayout(new FlowLayout());
		JLabel l7 = new JLabel(String.format("%-60s","set luch users color"));
		l7.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel7.add(l7);
		panel7.add(lunchUsers);
		
		JPanel panel8 = new JPanel();
		panel8.setLayout(new FlowLayout());
		JLabel l8 = new JLabel(String.format("%-60s","set window background color"));
		l8.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel8.add(l8);
		panel8.add(topPanel);
		
		JPanel panel9 = new JPanel();
		panel9.setLayout(new FlowLayout());
		JLabel l9 = new JLabel(String.format("%-60s","set button color"));
		l9.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel9.add(l9);
		panel9.add(buttonBackground);
		
		
		JPanel panel10 = new JPanel();
		panel10.setLayout(new FlowLayout());
		JLabel l10 = new JLabel(String.format("%-60s","set button panel background"));
		l10.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel10.add(l10);
		panel10.add(buttonPanelBackground);
		
		JPanel panel11 = new JPanel();
		panel11.setLayout(new FlowLayout());
		JLabel l11 = new JLabel(String.format("%-60s","set send area background"));
		l11.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel11.add(l11);
		panel11.add(sendPanelButton);
		
		JPanel panel12 = new JPanel();
		panel12.setLayout(new FlowLayout());
		JLabel l12 = new JLabel(String.format("%-60s","set chat area font color 1 (takes affect with new messages)"));
		l12.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel12.add(l12);
		panel12.add(fontColor1);
		
		JPanel panel13 = new JPanel();
		panel13.setLayout(new FlowLayout());
		JLabel l13 = new JLabel(String.format("%-60s","set chat area font color 2 (takes affect with new messages)"));
		l13.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel13.add(l13);
		panel13.add(fontColor2);
		
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		add(panel5);
		add(panel6);
		add(panel7);
		add(panel8);
		add(panel9);
		add(panel10);
		add(panel11);
		add(panel12);
		add(panel13);
		add(colorPicker);
		
		pack();
	
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		JButton source = (JButton) a.getSource();
		
		
		newColor = colorPicker.getColor();
		if (source == chatAreaButton ){
			Config.setProperty(Config.CHAT_BOX_BACKGROUNG_COLOR_R, getColors(RED));
			Config.setProperty(Config.CHAT_BOX_BACKGROUNG_COLOR_G, getColors(GREEN));
			Config.setProperty(Config.CHAT_BOX_BACKGROUNG_COLOR_B, getColors(BLUE));
			client.reloadColors();
		} else if (source == userAreaButton){
			Config.setProperty(Config.USER_BOX_BACKGROUND_COLOR_R, getColors(RED));
			Config.setProperty(Config.USER_BOX_BACKGROUND_COLOR_G, getColors(GREEN));
			Config.setProperty(Config.USER_BOX_BACKGROUND_COLOR_B, getColors(BLUE));
			client.reloadColors();
		} else if (source == borders){
			Config.setProperty(Config.BORDER_COLOR1_R, getColors(RED));
			Config.setProperty(Config.BORDER_COLOR1_B, getColors(BLUE));
			Config.setProperty(Config.BORDER_COLOR1_G, getColors(GREEN));
			int r = newColor.getRed() - 100;
			int g = newColor.getGreen() - 100;
			int b = newColor.getBlue() - 100;
			if(r<0){
				r = 0;
			}if(g<0){
				g=0;
			}if(b<0){
				b=0;
			}
			
			Color color = new Color(r,g,b);
			Config.setProperty(Config.BORDER_COLOR2_R, String.valueOf(color.getRed()));
			Config.setProperty(Config.BORDER_COLOR2_G, String.valueOf(color.getGreen()));
			Config.setProperty(Config.BORDER_COLOR2_B, String.valueOf(color.getBlue()));
			client.reloadColors();
		} else if (source == defaultUsers){
			Config.setProperty(Config.DEFAULT_LABEL_COLOR_R, getColors(RED));
			Config.setProperty(Config.DEFAULT_LABEL_COLOR_G, getColors(GREEN));
			Config.setProperty(Config.DEFAULT_LABEL_COLOR_B, getColors(BLUE));
			client.reloadColors();
		} else if(source == inUsers){
			Config.setProperty(Config.IN_LABEL_COLOR_R, getColors(RED));
			Config.setProperty(Config.IN_LABEL_COLOR_G, getColors(GREEN));
			Config.setProperty(Config.IN_LABEL_COLOR_B, getColors(BLUE));
			client.reloadColors();
		} else if(source == shortBreakUsers){
			Config.setProperty(Config.SHORT_BREAK_LABEL_COLOR_R, getColors(RED));
			Config.setProperty(Config.SHORT_BREAK_LABEL_COLOR_G, getColors(GREEN));
			Config.setProperty(Config.SHORT_BREAK_LABEL_COLOR_B, getColors(BLUE));
			client.reloadColors();
		} else if(source == lunchUsers ){
			Config.setProperty(Config.LUNCH_LABEL_COLOR_R, getColors(RED));
			Config.setProperty(Config.LUNCH_LABEL_COLOR_G, getColors(GREEN));
			Config.setProperty(Config.LUNCH_LABEL_COLOR_B, getColors(BLUE));
			client.reloadColors();
		} else if(source == topPanel){
			Config.setProperty(Config.TOPCONTAINER_BACKGROUND_COLOR_R, getColors(RED));
			Config.setProperty(Config.TOPCONTAINER_BACKGROUND_COLOR_G, getColors(GREEN));
			Config.setProperty(Config.TOPCONTAINER_BACKGROUND_COLOR_B, getColors(BLUE));
			client.reloadColors();
		} else if(source == buttonPanelBackground){
			Config.setProperty(Config.BUTTONPANEL_BACKGROUND_COLOR_R, getColors(RED));
			Config.setProperty(Config.BUTTONPANEL_BACKGROUND_COLOR_G, getColors(GREEN));
			Config.setProperty(Config.BUTTONPANEL_BACKGROUND_COLOR_B, getColors(BLUE));
			client.reloadColors();
		} else if(source == buttonBackground){
			Config.setProperty(Config.BUTTON_COLOR_R, getColors(RED));
			Config.setProperty(Config.BUTTON_COLOR_G, getColors(GREEN));
			Config.setProperty(Config.BUTTON_COLOR_B, getColors(BLUE));
			client.reloadColors();
		} else if(source == sendPanelButton){
			Config.setProperty(Config.CHATFIELD_BACKGROUND_COLOR_R, getColors(RED));
			Config.setProperty(Config.CHATFIELD_BACKGROUND_COLOR_G, getColors(GREEN));
			Config.setProperty(Config.CHATFIELD_BACKGROUND_COLOR_B, getColors(BLUE));
			client.reloadColors();
		} else if(source == fontColor1){
			Config.setProperty(Config.FONT_COLOR1_R, getColors(RED));
			Config.setProperty(Config.FONT_COLOR1_G, getColors(GREEN));
			Config.setProperty(Config.FONT_COLOR1_B, getColors(BLUE));
			client.reloadColors();
		}else if(source == fontColor2){
			Config.setProperty(Config.FONT_COLOR2_R, getColors(RED));
			Config.setProperty(Config.FONT_COLOR2_G, getColors(GREEN));
			Config.setProperty(Config.FONT_COLOR2_B, getColors(BLUE));
			client.reloadColors();
		}
		
		
	}
	
	private String getColors(String color){
		if (color == RED){
			return String.valueOf(newColor.getRed());
		}
		else if(color == GREEN){
			return String.valueOf(newColor.getGreen());
		}
		else {
			return String.valueOf(newColor.getBlue());
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		int yes = JOptionPane.showConfirmDialog(this, "Save Theme?", "Do you want to save your configuration settings?", JOptionPane.YES_NO_CANCEL_OPTION);
		if(yes == JOptionPane.YES_OPTION){
			try {
				Config.writeProperties();
				this.dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(yes == JOptionPane.CANCEL_OPTION){
			
		} else if(yes == JOptionPane.NO_OPTION){
			this.dispose();
		}
		
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
