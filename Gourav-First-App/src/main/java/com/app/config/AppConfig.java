package com.app.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class AppConfig {
	public static Locale getLocale() {
//		Locale l = new Locale("en", "US");
		Locale l2 = Locale.of("en", "US");
		return l2;
	}
	
	public static String getSecretData(String key) {
		ResourceBundle rb;
		rb = ResourceBundle.getBundle("authData", getLocale()); 
		
		return rb.getString(key);
		
		
	}

	
}
  