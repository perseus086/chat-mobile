package com.entities;

import java.util.List;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.Response;

import com.google.gson.JsonObject;


public class Connection {

	private CouchDbClient dbClient;


	public Connection() {

	}

	public Connection(CouchDbClient dbClient) {
		super();
		this.dbClient = dbClient;
	}

	public void connectToDB() throws Exception{
		
		CouchDbProperties properties = new CouchDbProperties()
		.setDbName("newdb")//not use capital letters
		.setCreateDbIfNotExist(true)
		.setProtocol("http")
		.setHost("localhost")
		.setPort(5984);

		dbClient = new CouchDbClient(properties);
		System.out.println("Connected to database");
	}


	public CouchDbClient getDbClient() {
		return dbClient;
	}



	public void setDbClient(CouchDbClient dbClient) {
		this.dbClient = dbClient;
	}

	public Response save(Object o){

		Response res = dbClient.save(o);
		//dbClient.shutdown();
		return res;

	}

	public EUser query(String desAndView, String key){


		List<EUser> list = dbClient.view(desAndView).key(key)
				.includeDocs(true)
				.query(EUser.class);

		if(list.size()>0){
			//dbClient.shutdown();
			System.out.println(list.get(0).toString());
			return list.get(0);
		}
		//dbClient.shutdown();
		return null;

	}
	public boolean userExist(String key){
		List<EUser> list = dbClient.view("account/viewToken").key(key)
				.query(EUser.class);
		if(list.size()>0){
			return true;
		}
		return false;
	}
	
	public String lookUpPassword(String key){
		List<JsonObject> list = dbClient.view("account/viewPassword").key(key)
				.query(JsonObject.class);
		if(list.size()>0){
			return list.get(0).get("value").getAsString();
		}
		return null;
	}
	public String lookUpToken(String key){
		List<JsonObject> list = dbClient.view("account/viewToken").key(key)
				.query(JsonObject.class);
		if(list.size()>0){
			return list.get(0).get("value").getAsString();
		}
		return null;
	}
	public String getID(String key){
		List<JsonObject> list = dbClient.view("account/viewID").key(key)
				.query(JsonObject.class);
		if(list.size()>0){
			return list.get(0).get("value").getAsString();
		}
		return null;
	}


	public Response edit(String email, Object change, String attribute){

		EUser usr = this.query("account/viewAll", email);
		
		if(attribute.equals("password"))
			usr.setPassword((String)change);
		if(attribute.equals("token"))
			usr.setToken((String)change);
		if(attribute.equals("active")){
			if(change.equals(true))
				usr.setActive(true);
			else
				usr.setActive(false);
			
		}
		if(attribute.equals("timestamp"))
			usr.setTimestamp((Long)change);
		
		Response res=dbClient.update(usr);
		
		//sdbClient.shutdown();
		return res;
	}

	public Response delete(Object obj){
		Response res=dbClient.remove(obj);
		//dbClient.shutdown();
		return res;
	}

}
