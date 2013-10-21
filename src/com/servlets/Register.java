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


@WebServlet("/Register")
public class Register extends HttpServlet {
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
		
		//Checks if email is valid
		String email = request.getParameter("email");
		try {
			if(!SystemAdmin.validateEmail(email))
				throw new Exception("Invalid email format");
		} catch (Exception e) {

			sendMess("error","<b>Email format is invalid<br><br>"
					+ "Valid formats are:</b><br>user@unimelb.edu.au"
					+ " <br>or <br>user@student.unimelb.edu.au",response);
			return;
		}

		//Connect to DB
		Connection conn = new Connection();
		try {
			conn.connectToDB();
		} catch (Exception e) {
			
			sendMess("error","Error connecting to DB: connection refused", response);
			return;
		}

		//
		if(!conn.userExist(request.getParameter("email"))){

			RandomString rs = new RandomString();
			EUser newUser = new EUser(email,"",rs.generateToken(30),false,SystemAdmin.getTimestamp());

			try {
				conn.save(newUser);
			} catch (Exception e) {
				
				sendMess("error", "Error connecting to DB: connection refused", response);
				System.err.println("Error connecting to DB: connection refused");
				return;
			}


			/////////TRY CATCH EMAIL	//enviar mail
			String mailBody = "Somebody, probably you, has registered your email address.\n"
					+ "click the following link to complete registration: \n\n"
					+ "http://localhost:8080/chat-mobile/Confirmation?token="+conn.lookUpToken(request.getParameter("email"))+
					"&email="+request.getParameter("email");

			if(SystemAdmin.sendMail2(request.getParameter("email"), "Account activation", mailBody))
			{
				sendMess("success", "Your account was successfully created.  Check your email for activation", response);
				return;
			}else
			{
				sendMess("error", "An error ocurred sending your email, please try again later", response);
				return;
			}
			
		}else{

			EUser usr = conn.query("account/viewAll", email);
			if(usr.isActive())
			{
				sendMess("error", "User already exists <br>Forgot your password? click <a href=\"reminder.html\">here</a>", response);
				
				return;
			}else{

				//If confirmation has been sent in less than 5 minutes ago
				if(SystemAdmin.getTimestamp()-usr.getTimestamp()<300000)
				{
					sendMess("error", "Account is pending for activation", response);
					
					return;
				}
				//If more than 5 minutes it sends again the email
				else
				{
					String tok= conn.lookUpToken(request.getParameter("email"));
					String mailBody = "Somebody, probably you, has registered your email address.\n"
							+ "click the following link to complete registration: \n\n"
//////////////////CAMBIAR AQUI
							+ "localhost:8080/chat-mobile/Confirmation?token="+tok+
							"&email="+request.getParameter("email");

					if(SystemAdmin.sendMail2(request.getParameter("email"), "Account activation", mailBody))
					{
						sendMess("success", "Your account was successfully created, please check your email", response);
						return;
					}else
					{
						conn.delete(usr);
						sendMess("error", "An error ocurred sending your email, please try again later", response);
						return;
					}
					
					
				}
			}

		}

	}

}
