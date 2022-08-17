package com.revature.services;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//@Service
public class EmailServiceImpl implements EmailService {

	@Override
	public void sendEmail(String to, String subject, String text) {
		// TODO Auto-generated method stub
		
	}
	
	//private final JavaMailSender mailSender;
	
	//public EmailServiceImpl(JavaMailSender mailSender) {
		//this.mailSender= mailSender;
	//}
	


/*
	@Override	
	public void sendEmail(String to, String subject,String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("");
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		this.mailSender.send(simpleMailMessage);
	}
*/
}
