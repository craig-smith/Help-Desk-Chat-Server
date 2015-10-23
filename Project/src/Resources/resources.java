package Resources;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class resources {
	
	public static final String blackPic = "/black.png";
	public static final String whitePic = "/white.png";
	public static final String sound = "/pew.wav";
	
	public Image getIcon(String img){
		
		
		return getImage(img);		
	}
	
	private Image getImage(String name){
		return new ImageIcon( getClass().getResource(name)).getImage();
	}
	
	public void playSound(String sound) throws IOException{
		
		InputStream inputStream = getClass().getResourceAsStream(sound);    
		AudioStream audioStream = new AudioStream(inputStream);    
		AudioPlayer.player.start(audioStream);
	}

}
