package com.revature.services;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
public class ResetPWService {
	private EmailService emailService;
	
	private UserRepository userRepo;

	SecureRandom random = new SecureRandom();

	/**
	 * A constructor to be used to inject dependencies
	 * 
	 * @param random
	 * @param emailService
	 */
	public ResetPWService(EmailService emailService, UserRepository userRepo) {
		// inject email function here
		this.emailService = emailService;
		this.userRepo = userRepo;
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
	    String subject = "RevaSphere Reset Password Token";
		String username = "";

		Optional<User> oUser = userRepo.findByEmail(email);
		if (oUser.isPresent()) {
			username = oUser.get().getUsername();
		}

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
	    // Call Email Function here
	    emailService.sendEmail(email, username, subject, generatedString);

	    /*Returning String*/
		return generatedString;
	}
}
