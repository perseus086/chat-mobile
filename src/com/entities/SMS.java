package com.entities;
 
public class SMS {
	
	private String _id;
	private String _rev;
	private String msg;
	private String timestamp;
	private EUser owner;
	
	public SMS(String msg, String timestamp,
			EUser owner) {
		super();
		
		this.msg = msg;
		this.timestamp = timestamp;
		this.owner = owner;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public EUser getOwner() {
		return owner;
	}
	public void setOwner(EUser owner) {
		this.owner = owner;
	}
	
	
	

}
