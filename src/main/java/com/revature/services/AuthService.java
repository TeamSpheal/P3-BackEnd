package com.revature.services;

import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userService.findByCredentials(email, password);
    }

    public User register(User user) {
        try {
			return userService.save(user);
		} catch (EmailAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsernameAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
}
