package com.entities;

import java.util.List;


public class Group {

	private String _id;
	private String _rev;
	private EUser admin;
	private List<EUser> users;
	private String name;
	private List<SMS> messages;
	
	
	public Group(EUser admin, List<EUser> users, String name, List<SMS> messages) {
		super();
		this.admin = admin;
		this.users = users;
		this.name = name;
		this.messages = messages;
	}
	
	
	public Group(String _id, String _rev, EUser admin, List<EUser> users,
			String name, List<SMS> messages) {
		super();
		this._id = _id;
		this._rev = _rev;
		this.admin = admin;
		this.users = users;
		this.name = name;
		this.messages = messages;
	}


	public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}


	public String get_rev() {
		return _rev;
	}


	public void set_rev(String _rev) {
		this._rev = _rev;
	}


	public EUser getAdmin() {
		return admin;
	}
	public void setAdmin(EUser admin) {
		this.admin = admin;
	}
	public List<EUser> getUsers() {
		return users;
	}
	public void setUsers(List<EUser> users) {
		this.users = users;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SMS> getMessages() {
		return messages;
	}
	public void setMessages(List<SMS> messages) {
		this.messages = messages;
	}
	
	
}
