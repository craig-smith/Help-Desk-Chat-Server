package com.craig.test;

import java.util.Random;


public class RandomString {
	private static Random randy;
	
	public static String getRandomString(){
		randy = new Random();
		String s = String.valueOf(randy.nextLong());
		return s;
	}
}
