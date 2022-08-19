package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
public class UserServiceTest {
	@MockBean
	private UserRepository userRepo;
	
	@Autowired
	private UserService userServ;
	
	@Test
	void testGetUser() {
		User mockUser = new User();
		Mockito.when(userRepo.findById((long) 1)).thenReturn(Optional.of(mockUser));
		assertNotNull(userServ.getUser((long) 1));
		Mockito.verify(userRepo, Mockito.times(1)).findById((long) 1);
	}
	
	@Test
	void testGetUserNull() {
		Mockito.when(userRepo.findById((long) 1)).thenReturn(Optional.empty());
		assertNull(userServ.getUser((long) 1));
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
	void testFindById() {
		User mockUser = new User();
		long id = 0;
		Mockito.when(userRepo.findById(id)).thenReturn(Optional.of(mockUser));
		Optional<User> returnedUser = userServ.findById(id);
		assertNotNull(returnedUser);
	}
	
	@Test
	void testFindAllUsers() {
		List<User> mockUserList = new ArrayList<>();
		Mockito.when(userRepo.findAll()).thenReturn(mockUserList);
		List<User> returnedUserList = userServ.findAllUsers();
		assertNotNull(returnedUserList);
	}
	
	@Test
	void testFindAllUsersFromList() {
		List<Long> mockUserIds = new ArrayList<>();
		List<User> mockUserList = new ArrayList<>();
		Mockito.when(userRepo.findAllById(mockUserIds)).thenReturn(mockUserList);
		List<User> returnedUsersList = userServ.findAllUsersFromList(mockUserIds);
		assertNotNull(returnedUsersList);
	}
	
	@Test
	void testUserSave() throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
		User mockUser = new User();
		User mockUserWithId = new User();
		mockUser.setId(1);
		Mockito.when(userRepo.save(mockUser)).thenReturn(mockUserWithId);
		User returnedUser = userServ.save(mockUser);
		assertNotNull(returnedUser);
	}
	
	@Test
	void testSaveUserEmailExists() {
		User mockUser = new User();
		String email = "testuser@gmail.com";
		
		Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.empty());
		
		assertThrows(EmailAlreadyExistsException.class, () -> {
			userServ.save(mockUser);
		});
	}
	
	
//	@Test
//	void testUpdateUser() throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
//		User mockUser = new User();
//		UserDTO mockDto = new UserDTO(mockUser);
//		long id = 0;
//		
//		Mockito.when(userRepo.findById(id)).thenReturn(Optional.of(mockUser));
//		Mockito.when(userRepo.save(mockUser)).thenReturn(mockUser);
//		
//		assertEquals(mockUser, userServ.update(mockDto));
//		
//	}
	
	@Test
	void testGetFollowers() {
		User mockUser = new User();
		//Set<User> followers = new HashSet<>();
		long id = 1;
		Mockito.when(userRepo.findById(id)).thenReturn(Optional.of(mockUser));
		Set<User> returnedFollowers = userServ.getFollowers(mockUser);
		assertNotNull(returnedFollowers);
	}
	
	
	
	
}
