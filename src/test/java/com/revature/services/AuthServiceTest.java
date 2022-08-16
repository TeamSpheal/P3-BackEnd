package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@SpringBootTest
public class AuthServiceTest {
	@MockBean
	private UserService userServ;
	

	@Test
	void testGetUser() {
		User mockUser = new User();
		Mockito.when(userRepo.findById((long) 1)).thenReturn(Optional.of(mockUser));
		assertNotNull(userServ.getUser((long) 1));
		
	}

	
	@Test
	void testFindByCredentials() {
		User mockUser = new User();
		String email = "testuser@gmail.com";
		String password = "password";
		Mockito.when(userServ.findByCredentials(email, password)).thenReturn(Optional.of(mockUser));
		assertNotNull(authServ.findByCredentials(email, password));
	}
	
	@Test
	void testUserSave() throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
		User mockUser = new User();
		User mockUserWithId = new User();
		mockUser.setId(1);
		Mockito.when(userServ.save(mockUser)).thenReturn(mockUserWithId);
		User returnedUser = authServ.register(mockUser);
		assertNotNull(returnedUser);
	}
}