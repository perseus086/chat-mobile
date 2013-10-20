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
import com.entities.RandomString;
import com.entities.SystemAdmin;

/**
 * Servlet implementation class Confirmation
 */
@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[CONFIRMATION]: post");
		Connection conn = new Connection();
		try {
			conn.connectToDB();
		} catch (Exception e) {
			System.err.println("[ERROR] Cannot connect with DB");
			sendPage("Error connecting to DataBase", "babyfail.jpg", "Please try again later", response);
			return;
		}
		
		String token =request.getParameter("token");
		String email =request.getParameter("email");
		
		if(conn.userExist(email)){
			
			EUser usr=null;
			try {
				usr = conn.query("account/viewAll",email);
			} catch (Exception e) {
				System.err.println("[ERROR] Cannot connect with DB");
				sendPage("Error connecting to DataBase", "babyfail.jpg", "Please try again later", response);
				return;
			}
			System.out.println(usr.getToken());
			if(usr.isActive())
			{
				sendPage("Your account is already active.", "", "Please login <a href=\"login.html\"> here </a>", response);
				return;
			}
			else{
				if(usr.getToken().equals(token)&& usr.getEmailAddress().equals(email)){
					System.out.println("DBtoken="+usr.getToken()+"     tok="+token);
					RandomString rs = new RandomString();
					
					try {
						conn.edit(email, rs.generatePassword(8),"password");
						conn.edit(email, true, "active");
						conn.edit(email, "", "token");
						usr=conn.query("account/viewAll",email);
					} catch (Exception e) {
						System.err.println("[ERROR] Cannot connect with DB");
						sendPage("Error connecting to DataBase", "babyfail.jpg", "Please try again later", response);
						return;
					}
					
					String mailBody = "The password for your Proj1 account is :\n"
							+ "password: "+usr.getPassword()+"\n\n"
									+ "Keep it secret, keep it safe.";
						
					if(SystemAdmin.sendMail(request.getParameter("email"), "Account activation", mailBody))
					{
						sendPage("Successfull activation", "emailSent.jpg", "Please check your email a password should be received<br>"
						+ "Login <a href=\"login.html\"> here </a>", response);
						return;
					}else{
						sendPage("Error sending email", "babyfail.jpg", "Please try again later", response);
						return;
					}

					
					
				}
				else{
					sendPage("There was a problem activating your account", "babyfail.jpg", "Please try again with the activation link", response);
					return;
				}
			}
		}
		else{
			sendPage("There was a problem activating your account", "babyfail.jpg", "Please try again with the activation link", response);
			return;
		}
				
	}
	
	protected void sendPage(String title, String image, String footer, HttpServletResponse res) throws IOException{
		PrintWriter out = res.getWriter();
		out.println("<html>");
		out.println("<head> <link rel=\"stylesheet\" type=\"text/css\" href=\"css/error.css\">"
				+ " <title>"+title+"</title></head>");
		out.println("<body>");
		out.println("<p class=\"center\">"+title+"</p>");
		if(!image.equals(""))
			out.println("<br><img src=\"img/"+image+"\"");
		out.println("<br><br><p class=\"center\">"+footer+"</p>");
		out.println("</body></html>");
		out.close();
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
