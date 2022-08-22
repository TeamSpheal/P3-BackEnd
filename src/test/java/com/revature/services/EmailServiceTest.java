package com.revature.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailServiceTest {
	
	@MockBean
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailService testEmailService;
	
	@Test
	public void testBadEmail() {
		/*Local Variables*/
		SimpleMailMessage resultMessage = testEmailService.sendEmail("sioughsohosjdhf", "", "", "");
	
		/*Test*/
		Assertions.assertNull(resultMessage);

	}
	
	@Test
	public void testGoodEmail() {
		/*Local Variables*/
		SimpleMailMessage resultMessage = testEmailService.sendEmail("cheeseandpie2000@gmail.com", "", "", "");
	
		/*Mocks*/
		
		SimpleMailMessage mockMessage = new SimpleMailMessage();
		mockMessage.setFrom("revSpherePassReset@gmail.com");
		mockMessage.setTo("cheeseandpie2000@gmail.com");
		mockMessage.setSubject("");
		
		String mockBody = "Hello " + "!\n\n"
				+ "Here is a token to reset your password.\n"
				+ "Copy this token to your clipboard, and return to RevaSphere to proceed.\n"
				+ "Token Here: ";
		
		mockMessage.setText(mockBody);
		
		//Mockito.when(mailSender.send(mockMessage)).thenReturn(null);
		
		/*Test*/
		Assertions.assertEquals(mockMessage, resultMessage);

	}

}
