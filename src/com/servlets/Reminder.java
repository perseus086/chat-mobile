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
import com.entities.SystemAdmin;
import com.google.gson.Gson;

/**
 * Servlet to remind password to user 
 */
@WebServlet("/Reminder")
public class Reminder extends HttpServlet {
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
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");

		if(!SystemAdmin.validateEmail(email)){
			sendMess("error", "Email format is incorrect.  Try again", response);
			return;
		}
		else{
			Connection conn = new Connection();
			try {
				conn.connectToDB();
			} catch (Exception e) {
				sendMess("error", "Error connecting to DB: connection refused", response);
				System.err.println("Error connecting to DB: connection refused");
				return;
			}
			if(conn.userExist(request.getParameter("email"))){
				EUser usr = conn.query("account/viewAll", email);
				if(usr.isActive())
				{
					//If confirmation has been sent in less than 5 minutes ago
					if(SystemAdmin.getTimestamp()-usr.getTimestamp()<300000)
					{
						sendMess("error", "Your activation email was recently sent", response);
//						response.sendRedirect("recentlySent.html");
						return;
					}
					//If more than 5 minutes it sends again the email
					else
					{
						String mailBody = "This is a reminder of your password\n"
								+"Password: "+usr.getPassword();
						
						if(SystemAdmin.sendMail2(request.getParameter("email"), "Password reminder", mailBody))
						{
							conn.edit(email, SystemAdmin.getTimestamp(), "timestamp");
							
							sendMess("success", "Your new password has been sent.  Check your email", response);
							return;
						}
						else{
							sendMess("error", "An error ocurred while sending the email<br>"
									+ "Please try again later...", response);
							return;
						}
						
						
						
					}
				}else{
					//response.sendRedirect("accountPending.html");
					sendMess("error", "Your account is pending of activation", response);
					return;
				}
			}
			else{
				sendMess("error", "User does not exist", response);
				//response.sendRedirect("userDontExist.html");
				return;
			}
		}
	}

}
