package Server;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ServerGui implements Runnable, ActionListener, WindowListener {

	//private ChatServer server;
	private static JPanel panel;
	private JFrame frame;
	
	private static ConcurrentHashMap<String, JButton> buttons = new ConcurrentHashMap<String, JButton>();
	public ServerGui(){
		//this.server = server;
		run();
		
	}
	
	public void addClient(String name){
		JButton button = new JButton(name);
		buttons.put(name, button);
		button.addActionListener(this);
		panel.add(button);
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void run() {
		frame = new JFrame("Server GUI");
		frame.setSize(new Dimension(100, 100));
		frame.setMinimumSize(new Dimension( 100, 100));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(this);
		panel = new JPanel();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		ChatServer2.Handler.removeClient(button.getText());
		panel.remove(button);
		frame.repaint();
		
	}
	
	public void removeClient(String name){
		panel.remove(buttons.get(name));
		panel.repaint();
		buttons.remove(name);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		System.exit(0);
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		int yes = JOptionPane.showConfirmDialog(null, "Shut Down?!?", "Are you sure you want to shut down the server?", JOptionPane.YES_NO_CANCEL_OPTION);
		if(yes == JOptionPane.YES_OPTION){
		ChatServer2.Handler.exit();
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
