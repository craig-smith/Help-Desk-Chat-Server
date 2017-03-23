package com.craig.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class Config {
	
	private static Properties prop;
	public static final String LABEL_COLOR_R = "label_color_r";
	public static final String LABEL_COLOR_G = "label_color_g";
	public static final String LABEL_COLOR_B = "label_color_b";
	
	public static final String BUTTON_COLOR_R = "button_color_r";
	public static final String BUTTON_COLOR_G = "button_color_g";
	public static final String BUTTON_COLOR_B = "button_color_b";
	
	public static final String CHAT_BOX_BACKGROUNG_COLOR_R = "chat_box_background_color_r";
	public static final String CHAT_BOX_BACKGROUNG_COLOR_G = "chat_box_background_color_g";
	public static final String CHAT_BOX_BACKGROUNG_COLOR_B = "chat_box_background_color_b";

	public static final String USER_BOX_BACKGROUND_COLOR_R = "user_box_background_color_r";
	public static final String USER_BOX_BACKGROUND_COLOR_G = "user_box_background_color_g";
	public static final String USER_BOX_BACKGROUND_COLOR_B = "user_box_background_color_b";
	
	public static final String DEFAULT_LABEL_COLOR_R = "default_label_color_r";
	public static final String DEFAULT_LABEL_COLOR_G = "default_label_color_g";
	public static final String DEFAULT_LABEL_COLOR_B = "default_label_color_b";
	
	public static final String IN_LABEL_COLOR_R = "in_label_color_r";
	public static final String IN_LABEL_COLOR_G = "in_label_color_g";
	public static final String IN_LABEL_COLOR_B = "in_label_color_b";
	
	public static final String SHORT_BREAK_LABEL_COLOR_R = "short_break_label_color_r";
	public static final String SHORT_BREAK_LABEL_COLOR_G = "short_break_label_color_g";
	public static final String SHORT_BREAK_LABEL_COLOR_B = "short_break_label_color_b";
	
	public static final String LUNCH_LABEL_COLOR_R = "lunch_label_color_r";
	public static final String LUNCH_LABEL_COLOR_G = "lunch_label_color_g";
	public static final String LUNCH_LABEL_COLOR_B = "lunch_label_color_b";

	public static final String TOPCONTAINER_BACKGROUND_COLOR_R = "topContainer_background_color_r";
	public static final String TOPCONTAINER_BACKGROUND_COLOR_G = "topContainer_background_color_g";
	public static final String TOPCONTAINER_BACKGROUND_COLOR_B = "topContainer_background_color_b";

	public static final String CHATFIELD_BACKGROUND_COLOR_R = "chatField_background_color_r";
	public static final String CHATFIELD_BACKGROUND_COLOR_G = "chatField_background_color_g";
	public static final String CHATFIELD_BACKGROUND_COLOR_B = "chatField_background_color_b";
	
	public static final String BUTTONPANEL_BACKGROUND_COLOR_R = "buttonPanel_background_color_r";
	public static final String BUTTONPANEL_BACKGROUND_COLOR_G = "buttonPanel_background_color_g";
	public static final String BUTTONPANEL_BACKGROUND_COLOR_B = "buttonPanel_background_color_b";

	public static final String BORDER_COLOR1_R = "border_color1_r";
	public static final String BORDER_COLOR1_G = "border_color1_g";
	public static final String BORDER_COLOR1_B = "border_color1_b";
	
	public static final String BORDER_COLOR2_R = "border_color2_r";
	public static final String BORDER_COLOR2_G = "border_color2_g";
	public static final String BORDER_COLOR2_B = "border_color2_b";
	
	public static final String FONT_COLOR1_R = "font_color1_r";
	public static final String FONT_COLOR1_G = "font_color1_g";
	public static final String FONT_COLOR1_B = "font_color1_b";
	
	public static final String FONT_COLOR2_R = "font_color2_r";
	public static final String FONT_COLOR2_G = "font_color2_g";
	public static final String FONT_COLOR2_B = "font_color2_b";






	
	
	private static File propFile;
	private static InputStream inStream;
	
	
	public static void loadProperties(File file) throws IOException{
		prop = new Properties();
		propFile = file;
		
		 inStream = new FileInputStream(propFile);
		
		if(inStream != null){
			prop.load(inStream);
			


			inStream.close();


		}else {
			throw new FileNotFoundException("Property file not found: " + propFile);
		}
	}
	public static void setFile(File file){
		propFile = file;
	}
	public String getProperty(String reqProp){
		return prop.getProperty(reqProp);
		
	}
	
	public static int getColorProperty(String reqProp){
		return Integer.valueOf((String)prop.getProperty(reqProp));
		
	}
	
	public static void setProperty(String type, String value){
		
		prop.setProperty(type, value);
		
		
	}
	
	public static void writeProperties() throws IOException{
		FileOutputStream writer = new FileOutputStream(propFile);
		if(writer != null){
			prop.store(writer, "Written from writeProperties method");
			writer.close();
		}
	}

	public static void loadDefaultProperties(InputStream stream) throws IOException {
		
		
		
		if(stream != null){
			prop = new Properties();
			prop.load(stream);
			


			stream.close();


		}else {
			throw new FileNotFoundException("Property file not found: " + propFile);
		}
	}
	
	public static String getStringProperty(String reqProp){
		if(prop.getProperty(reqProp) == null){
			return "Error getting property, Please rewrite your config file from one of the defaults.";
		}
		else {
			return prop.getProperty(reqProp);
		}
	}

}

