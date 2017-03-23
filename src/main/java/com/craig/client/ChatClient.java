package com.craig.client;



import com.craig.message.Message;
import com.craig.resources.Config;
import com.craig.resources.Resources;
import com.craig.test.RandomString;
import com.craig.test.SendTest;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *A complex chat application which allows agents to communicate more efficiently and monitor their breaks as a team.
 */
public class ChatClient extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Message user;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private JTextPane chatArea;
	private JPanel userArea;
	private JTextField chatField;
	
	private JButton logout;
	private JButton ready;
	private JButton notReady;
	private JButton lunch;
	private JButton sendButton;
	private JPanel buttonPanel;
	private JPanel chatFieldPanel;
	private JSplitPane topPanel;
	private JPanel topContainer;
	private JScrollPane chatAreaScroll;
	private JScrollPane userAreaScroll;
	private static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();
	private FlashGui flash;
	private UpdateUsersPanel updateUsers;
	private static String me = System.getProperty("user.name");
	//protected static String me;
	protected String serverAddress;
	private Socket socket;
	private Boolean run = true;
	private Border border;
	private JMenuBar menuBar;
	private File file;
	private static File path;
	
	public boolean testing = false;
	Timer t1;
    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Return in the
     * listener sends the textfield contents to the server.  Note
     * however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED
     * message from the server.
     */
    public ChatClient() {
    	
        // Layout GUI
    	try {
    		String home = System.getProperty("user.home");
    		file = new File(home +  "/BreakManager/" + "chatClient.properties");
    		path = new File(home + "/BreakManager");
    		if(file.exists() && !file.isDirectory()){
    			Config.loadProperties(file);
    			Config.setProperty("version", "1.9");
    			Config.writeProperties();
    		}else{
    			
    			path = new File(home + "/BreakManager");
    			path.mkdir();
    			

    			Config.setFile(file);
    			Config.loadDefaultProperties(getClass().getResourceAsStream("DefaultProperties/gui.properties"));
    			Config.writeProperties();
    		}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	menuBar = new MenuBar(this);
    	super.setJMenuBar(menuBar);
    	chatArea = new JTextPane();
    	chatArea.setEditable(false);
    	
    	
    	
    	chatArea.setMinimumSize(new Dimension(200, 200));
    	
    	
    	
    	chatAreaScroll = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	chatAreaScroll.setMinimumSize(new Dimension(200, 200));
    	
    	DefaultCaret caret = (DefaultCaret) chatArea.getCaret();
    	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    	
    	
		userArea = new JPanel();
		BoxLayout userBox =  new BoxLayout(userArea, BoxLayout.Y_AXIS);
		userArea.setLayout(userBox);
		
		userAreaScroll = new JScrollPane(userArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		userAreaScroll.setMinimumSize(new Dimension(50, 200));
		
		
		topPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatAreaScroll, userAreaScroll);
		
		topPanel.setDividerLocation(520);
		topPanel.setContinuousLayout(true);
		topPanel.setPreferredSize(new Dimension(800, 200));
		topPanel.setBorder(border);
		
		topContainer = new JPanel();
		
		topContainer.setBorder(border);
		topContainer.add(topPanel);
		
		
		
		chatField = new JTextField(60);
		chatField.setEditable(false);
		
		
		
		logout = new JButton("logout");	
		logout.addActionListener(new logOutButtonActionListener(logout, this));
		
		ready = new JButton("Ready");
		ready.addActionListener(new ReadyButtonActionListener(ready, this));
		
		notReady = new JButton("Short Break");
		notReady.addActionListener(new NotReadyButtonActionListener(notReady, this));
		
		lunch = new JButton("Lunch");
		lunch.addActionListener(new LunchActionListener(lunch, this));
		
		sendButton = new JButton("Send");
		sendButton.addActionListener(new SendActionListener(sendButton, this));
		
		
		buttonPanel = new JPanel();
		chatFieldPanel = new JPanel();	
		chatFieldPanel.setBorder(border);
		BoxLayout buttonPanelBox = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
		buttonPanel.setLayout(buttonPanelBox);
		buttonPanel.setBorder(border);
		buttonPanel.add(logout);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(ready);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(notReady);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(lunch);
		
		
		BoxLayout chatFieldBox = new BoxLayout(chatFieldPanel, BoxLayout.LINE_AXIS);
		chatFieldPanel.setLayout(chatFieldBox);
		chatFieldPanel.setBorder(border);
		chatFieldPanel.add(chatField);
		chatFieldPanel.add(sendButton);
		
		BoxLayout mainBox = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		super.setLayout(mainBox);
		updateUsers = new UpdateUsersPanel(userArea, users);
		reloadColors();
		add(topContainer);
		add(buttonPanel);
		add(chatFieldPanel);
		
		
		
		super.pack();
		
		
		
        // Add Listeners
		chatField.addKeyListener(new KeyListener() {
            /**
             * Responds to pressing the enter key in the textfield by sending
             * the contents of the text field to the server.    Then clear
             * the text area in preparation for the next message.
             */
            

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){					
					send();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });
		
		//start checking for new message and if the window is active
		
		flash = new FlashGui(this);
		
    }

    
    /**
     * Prompt for and return the address of the server.
     */
    private String getServerAddress() {
      if(testing == false){
    	return JOptionPane.showInputDialog(
            this,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
      }else return "192.168.2.14";
     
    }

    private String getUserName(){
    	
    	if(testing == false){
    	//String userName = JOptionPane.showInputDialog(this, "Enter your user name", "User Name", JOptionPane.QUESTION_MESSAGE);
    	//me = userName;
    	setTitle(me + " Logged into break manager");
    	return me;
    	}else{
    		me = RandomString.getRandomString();
    		return me;
    	}
    }
    

    /**
     * Connects to the server then enters the processing loop.
     */
    public void run() throws IOException {

        // Make connection and initialize streams
        String serverAddress = getServerAddress();
        socket = new Socket(serverAddress, 9001);
        out = new ObjectOutputStream(socket.getOutputStream());
        //out.flush();
        //out.reset();
        in = new ObjectInputStream(socket.getInputStream());
        

        // Process all messages from server, according to the protocol.
        while (run == true) {
            Message line = null;
			try {
				
				line = (Message) in.readObject();
				 if (line.getType() == Message.SERVER_MESSAGE && line.getMessage().startsWith("login please")) {
					 String userName = getUserName();
		            	user = new Message(userName, "login", Message.LOG_ON);
		            	
		            	sendMessage();
		            } 
		            if (line.getType() == Message.SERVER_MESSAGE && line.getMessage().startsWith("login accepted")) {
		            	chatField.setEditable(true);		            
		            	if(testing == true){//used for performance testing
		            		performanceTest();
		            	}
		            } 
		            if(line.getType() == Message.RECONNECT){
		            	
		            		
		            	reLogin(line);
		            	if(testing == true){//used for performance testing
		            		performanceTest();
		            	}
		            }
		            if (line.getType()== Message.CHAT_MESSAGE) {
		            	Date date = new Date();
		            	DateFormat f = new SimpleDateFormat("E HH:mm:ss");
		            	String now = f.format(date);
		            	appendToPane("[" + now + "] " + line.getName() + ": ", new Color(Config.getColorProperty(Config.FONT_COLOR1_R),
		            			Config.getColorProperty(Config.FONT_COLOR1_G),
		            			Config.getColorProperty(Config.FONT_COLOR1_B)));
		            appendToPane(line.getMessage() + "\n \n", new Color(Config.getColorProperty(Config.FONT_COLOR2_R),
		            		Config.getColorProperty(Config.FONT_COLOR2_G),
		            		Config.getColorProperty(Config.FONT_COLOR2_B)));
		            	
		            		flash.setNewMessage(true);
		            	
		            } 
		            if (line.getType() == Message.LOG_ON){
		            	User user = new User(line.getName());
		            	user.setTotalDefault(line.getTotalDefault());
		            	user.setTotalIn(line.getTotalIn());
		            	user.setTotalShortBreak(line.getTotalShortBreak());
		            	user.setTotalLunch(line.getTotalLunch());
		            	
		            	users.put(line.getName(), user);
		            	auxChange(line);
		            	updateUsers.addUser(line.getName());
		            	
		            }if(line.getType() == Message.KEEP_ALIVE){
		            	sendKeepAlive();
		            }
		            if(line.getType() == Message.LOG_OFF){
		            	if(users.containsKey(line.getName())){
		            		updateUsers.removeUser(line.getName());
		            		users.remove(line.getName());
		            		appendToPane(line.getName(), Color.red);
		            		appendToPane(" Logged off" + "\n \n", Color.DARK_GRAY);
		            		if(line.getName().equals(me)){
			            		out.close();
			            		in.close();
			            		System.exit(0);
			            	}
		            	}
		            	
		            	
		            }
		            
		            if(line.getType() == Message.AUX_CHANGE){
		            	auxChange(line);
		            }
			} catch (ClassNotFoundException e) {
				run = false;
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unexpected Error \n You may not be using the latest version of the chat client. \n " +
						 "Please talk to your system administrator to get the latest version \n" +
						"\n The applicatioin will now exit.");
				System.out.println("Client ClassNotFoundException");
				System.exit(0);
			} catch (EOFException e){
				System.out.println("End Of File reached");
				e.printStackTrace();
				run = false;
			}catch (SocketException e){
				e.printStackTrace();
				System.out.println("Client SocketException");
				in.close();
				out.close();
				socket.close();
				run = false;
				System.exit(1);
			}
            
            line = null;
        }
        
    }

    


	private void reLogin(Message line) {
		User user = new User(line.getName());
    	user.setTotalDefault(line.getTotalDefault());
    	user.setTotalIn(line.getTotalIn());
    	user.setTotalShortBreak(line.getTotalShortBreak());
    	user.setTotalLunch(line.getTotalLunch());
    	
    	users.put(line.getName(), user);
    	auxChange(line);
    	updateUsers.addUser(line.getName());
		
	}


	/**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args){
    	
        ChatClient client = new ChatClient();
        client.addWindowListener(new MainFrameWindowListener(client));
        client.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        client.setIconImage(new Resources().getIcon(Resources.whitePic));
        client.setResizable(false);
        client.setVisible(true);
        
        try {
			client.run();
		}catch (ConnectException e){
			JOptionPane.showMessageDialog(null, "Connection Error \n You may have input the wrong IP address. " +
					"\n The applicatioin will now exit. \n" + e.getMessage());
			System.out.println("Client ConnectionException");
			System.exit(0);
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, "You may not be using the latest version of the chat client. " + 
					"\n Please contact your application administrator to get the latest version." +
					"\n The applicatioin will now exit. \n" + e.getMessage());
			System.out.println("Client IOException");
			System.exit(0);
			
		}
        
       
    }
    private void sendKeepAlive() {
		user.setType(Message.KEEP_ALIVE);
		sendMessage();
		
	}
    
    public void logOut(){
    	if(testing == true){
    	t1.stop();
    	}
    	setTotalAuxValues();
    	if(user != null){
    	user.setType(Message.LOG_OFF);
    	run = false;
    	sendMessage();
    	System.exit(0);
    	}
    	  
		   	
    }
    
    public void ready(){
    	setTotalAuxValues();
    	user.setType(Message.AUX_CHANGE);    	
    	user.setMessage("Ready");
    	sendMessage();
    }
    
    public void notReady(){
    	setTotalAuxValues();
    	user.setType(Message.AUX_CHANGE);
    	user.setMessage("Short Break");
    	sendMessage();
    }
    
    public void lunch(){
    	setTotalAuxValues();
    	user.setType(Message.AUX_CHANGE);
    	user.setMessage("Lunch");
    	sendMessage();
    }
    
    public void send(){
    	
    	boolean validMessage = user.setMessage(chatField.getText());   
    	if(validMessage){
    	user.setType(Message.CHAT_MESSAGE);
    	sendMessage();    	
    	}
    	chatField.setText("");
    }
    
    private void setTotalAuxValues(){
    	User thisUser = users.get(me);
    	if(thisUser != null){
    	user.setTotalDefault(thisUser.getTotalDefault());
    	user.setTotalIn(thisUser.getTotalIn());
    	user.setTotalLunch(thisUser.getTotalLunch());
    	user.setTotalShortBreak(thisUser.getTotalShortBreak());
    	}
    }
    
    private void auxChange(Message message){
    	switch (message.getMessage()){
    	case "Ready":{
    		setAuxChange(message.getName(), User.IN, message.getAuxTime());
    		break;
    	}
    	case "Short Break":{
    		setAuxChange(message.getName(), User.SHORTBREAK, message.getAuxTime());
    		break;
    	}
    	case "Lunch":{    		
    		setAuxChange(message.getName(), User.LUNCH, message.getAuxTime());
    		break;
    	}
    	case "Default":{
    		users.put(message.getName(), new User(message.getName()));
    		setAuxChange(message.getName(), User.DEFAULT, message.getAuxTime());
    	}
    	case "Reset":{
    		//setAuxChange(message.getName(), User.DEFAULT, message.getAuxTime());
    		resetAuxValues(message);
    	}
    	}
    		
    	
    }
    private void resetAuxValues(Message msg){
    	Iterator<String> iterator = users.keySet().iterator();
    	while(iterator.hasNext()){
    		String key = iterator.next();
    		users.get(key).reset();
    	}
    	
    	
    }
    
    private void setAuxChange(String user, int auxValue, long time){
    	users.get(user).setauxValue(auxValue, time);
    	users.get(user).toString();
    
    }
    private void sendMessage(){
    	 try {
    		 	if(users.get(me) != null){
	    		 	User thisUser = users.get(me);
	    		 	user.setTotalDefault(thisUser.getTotalDefault());
	    		 	user.setTotalIn(thisUser.getTotalIn());
	    		 	user.setTotalLunch(thisUser.getTotalLunch());
	    		 	user.setTotalShortBreak(thisUser.getTotalShortBreak());
	    		 	user.setAuxTime(System.currentTimeMillis());
    		 	}
				out.writeObject(user);
				out.flush();
				out.reset();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    }
   
    private void appendToPane(String msg, Color c){
    	StyleContext context = StyleContext.getDefaultStyleContext();
    	AttributeSet set = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
    	
    	set = context.addAttribute(set, StyleConstants.FontFamily, "Lucida Console");
    	set = context.addAttribute(set, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
    	
    	Document doc = chatArea.getDocument();
    	int length = chatArea.getDocument().getLength();
    	chatArea.setCaretPosition(length);
    	chatArea.setCharacterAttributes(set, false);
    	
    	try {
			doc.insertString(length, msg, set);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


	public void reloadColors() {
		chatArea.setBackground(new Color(Config.getColorProperty(Config.CHAT_BOX_BACKGROUNG_COLOR_R), 
    			Config.getColorProperty(Config.CHAT_BOX_BACKGROUNG_COLOR_G),
    			Config.getColorProperty(Config.CHAT_BOX_BACKGROUNG_COLOR_B)));
		userArea.setBackground((new Color(Config.getColorProperty(Config.USER_BOX_BACKGROUND_COLOR_R),
				Config.getColorProperty(Config.USER_BOX_BACKGROUND_COLOR_G), 
				Config.getColorProperty(Config.USER_BOX_BACKGROUND_COLOR_B))));
		topContainer.setBackground(new Color(Config.getColorProperty(Config.TOPCONTAINER_BACKGROUND_COLOR_R),
				Config.getColorProperty(Config.TOPCONTAINER_BACKGROUND_COLOR_G),
				Config.getColorProperty(Config.TOPCONTAINER_BACKGROUND_COLOR_B)));
		chatField.setBackground(new Color(Config.getColorProperty(Config.CHATFIELD_BACKGROUND_COLOR_R),
				Config.getColorProperty(Config.CHATFIELD_BACKGROUND_COLOR_G),
				Config.getColorProperty(Config.CHATFIELD_BACKGROUND_COLOR_B)));
		buttonPanel.setBackground(new Color(Config.getColorProperty(Config.BUTTONPANEL_BACKGROUND_COLOR_R), 
				Config.getColorProperty(Config.BUTTONPANEL_BACKGROUND_COLOR_G),
				Config.getColorProperty(Config.BUTTONPANEL_BACKGROUND_COLOR_B)));
		border = BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(Config.getColorProperty(Config.BORDER_COLOR1_R),
				Config.getColorProperty(Config.BORDER_COLOR1_G), Config.getColorProperty(Config.BORDER_COLOR1_B)),new Color(
				Config.getColorProperty(Config.BORDER_COLOR2_R), Config.getColorProperty(Config.BORDER_COLOR2_G), Config.getColorProperty(Config.BORDER_COLOR2_B)));
		logout.setBackground(new Color(Config.getColorProperty(Config.BUTTON_COLOR_R), 
				Config.getColorProperty(Config.BUTTON_COLOR_G),
				Config.getColorProperty(Config.BUTTON_COLOR_B)));
		ready.setBackground(new Color(Config.getColorProperty(Config.BUTTON_COLOR_R), 
				Config.getColorProperty(Config.BUTTON_COLOR_G),
				Config.getColorProperty(Config.BUTTON_COLOR_B)));
		notReady.setBackground(new Color(Config.getColorProperty(Config.BUTTON_COLOR_R), 
				Config.getColorProperty(Config.BUTTON_COLOR_G),
				Config.getColorProperty(Config.BUTTON_COLOR_B)));
		lunch.setBackground(new Color(Config.getColorProperty(Config.BUTTON_COLOR_R), 
				Config.getColorProperty(Config.BUTTON_COLOR_G),
				Config.getColorProperty(Config.BUTTON_COLOR_B)));
		sendButton.setBackground(new Color(Config.getColorProperty(Config.BUTTON_COLOR_R), 
				Config.getColorProperty(Config.BUTTON_COLOR_G),
				Config.getColorProperty(Config.BUTTON_COLOR_B)));
		topContainer.setBorder(border);
		topPanel.setBorder(border);
		chatFieldPanel.setBorder(border);
		buttonPanel.setBorder(border);
		
		updateUsers.reloadColors();
	}
    
    
    public static File getPath(){
    	return path;
    }
    
    public static User getUser(){
    	return users.get(me);
    }
    
    /****
     * Performance testing methods below...
     */
   private void performanceTest(){
	   
	   t1 = new Timer(2000, new SendTest(this));
	   t1.setRepeats(true);
	   t1.start();
	    
   }
   public void sendTest(String msg){
	   chatField.setText(msg);
	   send();
   }

	
}


