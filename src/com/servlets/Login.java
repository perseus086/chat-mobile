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
import com.entities.RandomString;
import com.entities.SystemAdmin;
import com.google.gson.Gson;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void sendMess(String type, String message, String token, String eemail,HttpServletResponse res) throws IOException{
		ErrorMessage errm = new ErrorMessage(type, message,token,eemail);
		res.setContentType("application/json");
		Gson gsn = new Gson();
		String json = gsn.toJson(errm);
		PrintWriter out = res.getWriter();
		out.print(json);
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		System.out.println(email);
		String password = request.getParameter("password");
		System.out.println(password);
		if(password.trim().equals(""))
		{
			sendMess("error", "Password is invalid","","", response);
			return;
		}
			
		
		if(!SystemAdmin.validateEmail(email)){

			sendMess("error", "Email is invalid","","", response);
			return;
		} else {

			Connection conn;
			try{
				conn = new Connection();
				conn.connectToDB();
			}catch(Exception e){
				sendMess("error", "Error connecting to db", "", "", response);
				System.out.println("Error connecting to db");
				return;
			}
			
			try {
				if(conn.userExist(email)){
					EUser usr=null;
					try{
						usr = conn.query("account/viewAll", email);
					}catch(Exception e){
						sendMess("error", "User does not exist","","", response);
						//System.out.println("Cannot get user"+e.getMessage());
						return;
					}

					if(usr.getEmailAddress().equals(email)&&usr.getPassword().equals(password))
					{
						RandomString rs = new RandomString();
						String newToken = rs.generatePassword(30);
						conn.edit(email, newToken,"token");
						sendMess("success", "Welcome to the best Chat System",newToken,usr.getEmailAddress(), response);					
					}
					else{
						sendMess("error", "Wrong user or password","","", response);
						return;
						//System.out.println("No se pudo ingresar");
					}
				}
				else{
					System.err.println("Account does not exist");
					sendMess("error", "User does not exist","","", response);
					return;
				}
			} catch (Exception e) {
				//System.out.println("cannot find user"+email);
				sendMess("error", "Something wrong occured while connecting to server","","", response);
				return;
			}
		}
	}
}
