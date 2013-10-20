package com.entities;

import java.util.Random;


public class RandomString {

	public final char[] c = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	public final char[] pass = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	
	
	public String generateToken(int size){
		
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < size; i++) {
		    char ch = c[random.nextInt(c.length)];
		    sb.append(ch);
		}
		String output = sb.toString();
		return output;
		
	}
	
public String generatePassword(int size){
		
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < size; i++) {
		    char ch = pass[random.nextInt(pass.length)];
		    sb.append(ch);
		}
		String output = sb.toString();
		return output;
		
	}
	
}
