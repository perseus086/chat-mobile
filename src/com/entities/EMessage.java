package com.entities;

public class EMessage {

	private String to;
	private String title;
	private String from;
	private String message;
	
	public EMessage(){}
	
	
	public EMessage(String to, String title, String from, String message) {
		
		this.to = to;
		this.title = title;
		this.from = from;
		this.message = message;
	}


	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
