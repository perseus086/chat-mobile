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
import com.entities.ErrorMessage;
import com.google.gson.Gson;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	protected void sendMess(String type, String message, HttpServletResponse res) throws IOException{
		ErrorMessage errm = new ErrorMessage(type, message);
		res.setContentType("application/json");
		Gson gsn = new Gson();
		String json = gsn.toJson(errm);
		PrintWriter out = res.getWriter();
		out.print(json);
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("application/json");
		Connection conn;
		String email = request.getParameter("email");
		String token = request.getParameter("token");
		System.out.println("logout servlet:"+email+token);
		try{
			conn = new Connection();
			conn.connectToDB();
		}catch(Exception e){
			sendMess("error", "Error connecting to database ", response);
			return;
		}
		EUser usr = null;

		if (conn.userExist(email)){
			usr = conn.query("account/viewAll", email);
			System.out.println(usr.getEmailAddress());
			if(!token.equals(usr.getToken())){
				sendMess("error", "Invalid token", response);
				return;
			}else{
				if(email.equals(usr.getEmailAddress())){
					conn.edit(email,"", "token");
					System.out.println("success");
					sendMess("success", "You are logged out", response);    
					return;
				}else{
					sendMess("error", "Invalid user account",response);
					return;
				}
			}

		}else{
			sendMess("error", "User does not exist",response);
			return;
		}
	}

}
