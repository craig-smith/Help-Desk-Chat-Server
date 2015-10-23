package Test;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Client.ChatClient;
import Client.MainFrameWindowListener;
import Resources.resources;

public class ServerStressTest extends Thread implements Runnable {

	public ServerStressTest(String name){
		super(name);
	}
	@Override
	public void run() {
		ChatClient client = new ChatClient();
        client.addWindowListener(new MainFrameWindowListener(client));
        client.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        client.setIconImage(new resources().getIcon(resources.whitePic));
        client.setResizable(false);
        client.setVisible(true);
		try {
			client.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String args[]){
		for(int i = 0; i < 5; i ++){
			SwingUtilities.invokeLater(new ServerStressTest(String.valueOf(i)));
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
