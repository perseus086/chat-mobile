package com.entities;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SystemAdmin {

	public static boolean sendMail(String to, String subject, String text){

		String host = "localhost";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply@swen90002-08.cis.unimelb.edu.au"));
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));

			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
			System.out.println("Message sent successfully to: "+ to);
			return true;

		}catch (MessagingException mex) {
			return false;
		}

	}

	public static boolean sendMail2(String to, String subject, String text){
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("labeiaunimelb","guaguapichincha");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);

			System.out.println("Email sent to: "+to);
			return true;

		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			return false;

		}
	}

	public Connection connectDB() throws Exception{

		Connection conn =new Connection();

		return conn;
	}

	public static boolean validateEmail(String email){
		if(email.matches("^[^@]+@(student.unimelb.edu.au|unimelb.edu.au|gmail.com)"))
			return true;
		return false;
	}

	public static long getTimestamp(){
		Date date = new Date();
		return date.getTime();
	}

}
