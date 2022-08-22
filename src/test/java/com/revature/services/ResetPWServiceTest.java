package com.revature.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@SpringBootTest(classes=ResetPWService.class)
public class ResetPWServiceTest {
	@MockBean
	private EmailService emailService;

	@MockBean
	private UserRepository userRepo;
	
	@Autowired
	private ResetPWService resetPWServ;
	
	/**
	 * Testing reset token generation
	 */
	@Test
	public void testGenerateResetToken() {
		/*Local Variables*/
		String result = null;
		String email = "email";
		User mockUser = new User();
		mockUser.setUsername("Username");
		
		/*Mocks*/
		//Mockito.when(emailService.sendEmailWithToken("")).thenReturn(true);
		
		/*Function*/
		Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(mockUser));
		result = resetPWServ.generateResetToken("");

		System.out.println(result);
		
		/*Test*/
		Assertions.assertNotNull(result);
	}
}
