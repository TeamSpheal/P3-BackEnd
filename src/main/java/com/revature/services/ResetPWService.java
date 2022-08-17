package com.revature.services;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class ResetPWService {
	// private EmailService emailService

	/**
	 * A constructor to be used to inject dependencies
	 * 
	 * @param random
	 * @param emailService
	 */
	public ResetPWService() {// EmailService emailService
		// this.emailService = emailService;
	}

	/**
	 * Provides a random string to act as a reset token
	 * @return
	 */
	public String generateResetToken(String email) {
		/*Local Variables*/
		int leftLimit = 48; // '0'
	    int rightLimit = 122; // 'z'
	    int targetStringLength = 7;
	    Random random = new Random();

	    /*Generating token*/
	    /*How this works:
	     *1: random.ints generates a "unlimited" stream of integers
	     *  a:Integers correspond to a unicode character
	     *    (48 - 57) = 0-9
	     *    (65 - 90) = A-Z
	     *    (97 - 122) = a-z
	     *2: .filter filters the stream to only include numbers the correspond to alphanumeric characters
	     *3: .limit limits the stream to a given amount of characters (targetStringLength)
	     *4: .collect collects the representation of each integer
	     *5: .toString converts from StringBuilder to String
	     */
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
	    /*Sending Email*/
	    //emailService.sendEmailWithToken(email, generatedString);

	    /*Returning String*/
		return generatedString;
	}
}
