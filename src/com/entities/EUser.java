package com.entities;

public class EUser {
	
	private String _id;
	private String _rev;
	private String emailAddress;
	private String password;
	private String token;
	private boolean active;
	private long timestamp;
	
	

	public EUser(){}
	
	
	public EUser(String emailAddress, String password, String token,
			boolean active, long timestamp) {
		super();
		this.emailAddress = emailAddress;
		this.password = password;
		this.token = token;
		this.active = active;
		this.timestamp = timestamp;
	}


	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "EUser [_id=" + _id + ", _rev=" + _rev + ", emailAddress="
				+ emailAddress + ", password=" + password + ", token=" + token
				+ ", active=" + active + "]";
	}

}
