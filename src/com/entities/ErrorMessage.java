package com.entities;

public class ErrorMessage {
	private String typeOfMessage;
	private String description;
	private String token;
	private String email;
	
	
	public ErrorMessage(){}
	
	public ErrorMessage(String typeOfMessage, String description) {
		this.typeOfMessage = typeOfMessage;
		this.description = description;
	}
	
	public ErrorMessage(String typeOfMessage, String description, String token, String email) {
		super();
		this.typeOfMessage = typeOfMessage;
		this.description = description;
		this.token = token;
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTypeOfMessage() {
		return typeOfMessage;
	}
	public void setTypeOfMessage(String typeOfMessage) {
		this.typeOfMessage = typeOfMessage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	


}
