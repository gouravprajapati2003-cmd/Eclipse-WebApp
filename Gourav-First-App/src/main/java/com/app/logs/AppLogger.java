package com.app.logs;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class AppLogger {
	public static String getDateTime(int style) {
		Date date = new Date();// this stores current date and time
		DateFormat dtf = DateFormat.getDateTimeInstance(style, style, Locale.of("hi", "IN"));
		return dtf.format(date);
	}
	
	public static String ERROR_LOG(String msg) {
		String RED_COLOR = "\u001B[31m";
		return RED_COLOR+getDateTime(3) + " TYPE : ERROR : " + msg;
	}
	
	public static String INFO_LOG(String msg) {
		String BLUE_COLOR = "\u001B[34m";
		return BLUE_COLOR+getDateTime(3) + " TYPE : INFO : " + msg;
	}

	public static String SUCCESS_LOG(String msg) {
		String GREEN_COLOR = "\u001B[32m";
		return GREEN_COLOR+getDateTime(3) + " TYPE : SUCCESS : " + msg;
	
	}
	
	public static void main(String s []) {
		
	}

}


