package com.revature.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	private JavaMailSender mailSender;
	
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendEmail(String toEmail, String username, String subject, String body) {
		
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
		
	}
	
}
