package com.revature.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import EmailService here when it exists

@SpringBootTest
public class ResetPWServiceTest {
	/*@MockBean
	private EmailService emailService*/
	
	@Autowired
	private ResetPWService resetPWServ;
	
	/**
	 * Testing reset token generation
	 */
	@Test
	public void testGenerateResetToken() {
		/*Local Variables*/
		String result = null;
		
		/*Function*/
		result = resetPWServ.generateResetToken();
		System.out.println(result);
		
		/*Test*/
		Assertions.assertNotNull(result);
	}
}
