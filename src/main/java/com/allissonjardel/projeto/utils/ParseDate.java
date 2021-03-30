package com.allissonjardel.projeto.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ParseDate {

	public static String parseData(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf1.format(date);
	}
	
	public static String parseHorario(Date date) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		sdf2.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf2.format(date);
		
	
	}
}
