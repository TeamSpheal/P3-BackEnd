package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.*;

@Service
public class EmailService {
	private JavaMailSender mailSender;
	
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public SimpleMailMessage sendEmail(String toEmail, String username, String subject, String body) {
		
		Boolean checkEmail = Pattern.matches("[a-z0-9_-]{1,63}@[a-z]{1,30}[.][a-z]{2,5}", toEmail);
	
		if(checkEmail == false) {
			return null;
		}
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("revSpherePassReset@gmail.com");
		message.setTo(toEmail);
		message.setSubject(subject);
		
		String completeBody = "Hello " + username + "!\n\n"
				+ "Here is a token to reset your password.\n"
				+ "Copy this token to your clipboard, and return to RevaSphere to proceed.\n"
				+ "Token Here: ";
		
		completeBody += body;
		
		message.setText(completeBody);
		
		mailSender.send(message);
		
		System.out.println("Email Success!");
		
		return message;
		
	}
	
}
