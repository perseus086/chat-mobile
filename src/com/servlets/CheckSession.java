package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entities.Connection;
import com.entities.EUser;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class CheckSession
 */
@WebServlet("/CheckSession")
public class CheckSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String token = request.getParameter("token");
		
		System.out.println("E="+email);
		System.out.println("T="+token);
				
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		System.out.println("checking session");
		JsonObject jobj = new JsonObject();
		
		if(email.isEmpty()||token.isEmpty()||token.trim().equals(""))
		{
			jobj.addProperty("info", "invalidSession");
        	out.print(jobj);
		}
		else{
			
			
			EUser usr = null;
			Connection conn = new Connection();
			try {
				conn.connectToDB();
			} catch (Exception e) {
				jobj.addProperty("info", "error");
				jobj.addProperty("description", "Error connecting with DB");
	        	out.print(jobj);
			}
			try{
			if(conn.userExist(email)){
				usr = conn.query("account/viewAll", email);
				
				if(email.equals(usr.getEmailAddress()) && token.equals(usr.getToken()))
				{
					jobj.addProperty("info", "valid");
		        	out.print(jobj);
				}
				else{
					jobj.addProperty("info", "invalidSession");
			        out.print(jobj);
				}
			}
			else{
				jobj.addProperty("info", "invalidSession");
		        out.print(jobj);
			}
			}
			catch(NullPointerException ex){
				jobj.addProperty("error", "Could not connect to database");
		        out.print(jobj);
			}
		}
		out.close();
	}

}
