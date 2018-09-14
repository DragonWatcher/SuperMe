package com.ahav.util;

public class GeneralUtils {

	public static String getString(){
		String words = "abcdefghjklmnpqrstuvwxwz";
		String str = "";
		do{
			Character cc = words.charAt((int)(Math.random() * 23));
			str = cc + str;
		}while(str.length()<4);
		do{
			double random = Math.random() * 10;
			int rr = (int) random;
			str = str + rr;
		}while(str.length()<10);
		return str;
	}
	
}
