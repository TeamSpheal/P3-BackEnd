package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private UserService userServ;
	
	@Test
	void testGetUser() {
		User mockUser = new User();
		Mockito.when(userRepo.findById((long) 1)).thenReturn(Optional.of(mockUser));
		assertNotNull(userServ.getUser((long) 1));
		Mockito.verify(userRepo, Mockito.times(1)).findById((long) 1);
	}
	
	@Test
	void testFindByCredentials() {
		User mockUser = new User();
		String email = "testuser@gmail.com";
		String password = "password";
		Mockito.when(userRepo.findByEmailAndPassword(email, password)).thenReturn(Optional.of(mockUser));
		assertNotNull(userServ.findByCredentials(email, password));
		Mockito.verify(userRepo, Mockito.times(1)).findByEmailAndPassword(email, password);
	}
	
	@Test
	void testUserSave() throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
		User mockUser = new User();
		String username = "testuser";
		String email = "testuser@gmail.com";
		Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(mockUser));
		Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(mockUser));
		assertNotNull(userServ.save(mockUser));
		Mockito.verify(userRepo, Mockito.times(1)).findByUsername(username);
		Mockito.verify(userRepo, Mockito.times(1)).findByEmail(email);
	}
}
