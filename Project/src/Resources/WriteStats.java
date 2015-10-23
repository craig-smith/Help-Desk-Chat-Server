package Resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import Client.ChatClient;
import Client.User;
import Message.Message;
import Server.ConnectedClient;

public class WriteStats {
	private static FileWriter writer;
	private static FileWriter serverWriter;
	private static PrintWriter errorWriter;
	private static final Object WRITER_LOCK = new Object();
	
	public WriteStats(){
		
		
	
	}
	
	public void writeUserStats(){
		User user = ChatClient.getUser();
		File path = ChatClient.getPath();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date now = new Date();
		File fileName = new File(path.toString() + "/" + format.format(now) + " " +  user.getName() + ".txt");
		try {
			
			writer = new FileWriter(fileName);
			
			writer.write(user.getName() + "'s Stats:\r\n");
			writer.write(user.writeAuxTime());
			writer.flush();
			writer.close();
			
			JOptionPane.showConfirmDialog(null, "Your file was saved to " + fileName, "File Saved!", JOptionPane.DEFAULT_OPTION);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "There was an error writing you stats. \n " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void writeServerStats(ConnectedClient client)  {
		synchronized (WRITER_LOCK){
			try {
				Date time = new Date();
				DateFormat format = new SimpleDateFormat("HH:mm:ss");
				Date now = new Date();
				serverWriter = new FileWriter("chatserver.log", true);
				serverWriter.write(client.getName() + "," + client.getCurrentAuxValue() + "," + format.format(now) + ",\r\n");
				serverWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} finally{
				try {
					serverWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public static void serverLog(Exception e){
		try{
			/*
		Date time = new Date();
		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		serverWriter = new FileWriter("error-log.log", true);
		serverWriter.write(logMessage + " " + format.format(now)+  ",\r\n");
		serverWriter.close();
		*/
			errorWriter = new PrintWriter("error-log.log");
			e.printStackTrace(errorWriter);
	} catch (IOException ex) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	} finally{
		errorWriter.close();
		}
	}

}
