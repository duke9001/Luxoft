package com.luxoft.utility;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {

	public static Properties property;

	public ReadConfig() {

		File src = new File("./Configuration/config.properties");

		try {

			FileInputStream file = new FileInputStream(src);
			property = new Properties();
			property.load(file);

		}
		catch (Exception e) {
			System.out.println("Excetion is" + e.getMessage());
		}
	}
	
	

	public String getBaseURL() {

		String url = property.getProperty("url");
		return url;
	}		

	public String getContactUsURL() {

		String url = property.getProperty("contactUs");
		return url;
	}	
	
	
}
