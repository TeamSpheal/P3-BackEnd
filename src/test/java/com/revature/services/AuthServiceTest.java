package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@SpringBootTest(classes=SocialMediaApplication.class)
class AuthServiceTest {
	@MockBean
	private UserRepository userRepo;
	
	@MockBean
	private UserService userService;

	@Autowired
	private AuthService authService;
	
	@Test
	void testFindByCredentials() {
		User mockUser = new User();
		String email = "testuser@gmail.com";
		String password = "password";
		Mockito.when(userRepo.findByEmailAndPassword(email, password)).thenReturn(Optional.of(mockUser));
		assertNotNull(authService.findByCredentials(email, password));
	}

	@Test
	void testRegister() throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
		User mockUser = new User();
		mockUser.setEmail("email");
		mockUser.setUsername("username");
		Mockito.when(userService.doesEmailAlreadyExist(mockUser.getEmail())).thenReturn(false);
		Mockito.when(userService.doesUsernameAlreadyExist(mockUser.getUsername())).thenReturn(false);
		Mockito.when(userService.save(mockUser)).thenReturn(mockUser);
		assertNotNull(authService.register(mockUser));
	}

	@Test
	void testRegisterEmailAlreadyExists() throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
		User mockUser = new User();
		mockUser.setEmail("email");
		mockUser.setUsername("username");
		Mockito.when(userService.doesEmailAlreadyExist(mockUser.getEmail())).thenReturn(true);
		assertThrows(EmailAlreadyExistsException.class, () -> {authService.register(mockUser);});
	}

	@Test
	void testRegisterUsernameAlreadyExists() throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
		User mockUser = new User();
		mockUser.setEmail("email");
		mockUser.setUsername("username");
		Mockito.when(userService.doesUsernameAlreadyExist(mockUser.getUsername())).thenReturn(true);
		assertThrows(UsernameAlreadyExistsException.class, () -> {authService.register(mockUser);});
	}
}