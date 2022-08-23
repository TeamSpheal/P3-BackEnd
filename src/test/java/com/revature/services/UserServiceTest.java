package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.dtos.UserDTO;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.RecordNotFoundException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@SpringBootTest
class UserServiceTest {
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
	void testFindByEmail() {
		User mockUser = new User();
		String email = "testuser@gmail.com";
		Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(mockUser));
		User returnedUser = userServ.findByEmail(email);
		assertNotNull(returnedUser);
	}
	
	@Test
	void testFindByEmailIsNull() {
		String email = "testuser@gmail.com";
		Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.empty());
		User returnedUser = userServ.findByEmail(email);
		assertNull(returnedUser);
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
	void testUserSaveSuccess() throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
		User mockUser = new User();
		User mockUserWithId = new User();
		mockUser.setId(1);
		Mockito.when(userRepo.save(mockUser)).thenReturn(mockUserWithId);
		User returnedUser = userServ.save(mockUser);
		assertNotNull(returnedUser);
	}
	
	@Test
	void testUserSaveEmailAlreadyExists() throws EmailAlreadyExistsException {
		User mockUser = new User();
		String email = "testuser@gmail.com";
		mockUser.setId(1);
		mockUser.setEmail(email);
		Mockito.when(userRepo.existsByEmail(email)).thenReturn(true);
		assertThrows(EmailAlreadyExistsException.class, () -> {
			userServ.save(mockUser);
		});
	}
	
	@Test
	void testUserSaveUsernameAlreadyExistsUser() throws UsernameAlreadyExistsException {
		User mockUser = new User();
		String username = "testuser";
		mockUser.setId(1);
		mockUser.setUsername(username);
		Mockito.when(userRepo.existsByUsername(username)).thenReturn(true);
		assertThrows(UsernameAlreadyExistsException.class, () -> {
			userServ.save(mockUser);
		});
	}
	
	@Test
	void testSaveEmailAndUsernameMatches() throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
		User mockUser = new User();
		User mockTest = new User();
		User mockUserWithId = new User();
		mockUser.setId(1);
		String username = "testuser";
		String email = "testuser@gmail.com";
		mockUser.setId(1);
		mockUser.setUsername(username);
		mockTest.setUsername(username);
		mockUser.setEmail(email);
		mockTest.setEmail(email);
		Mockito.when(userRepo.existsByUsername(username)).thenReturn(false);
		Mockito.when(userRepo.existsByEmail(email)).thenReturn(false);
		Mockito.when(userRepo.save(mockUser)).thenReturn(mockUserWithId);
		Assertions.assertEquals(mockUser.getUsername(), Optional.of(mockTest).get().getUsername());
		Assertions.assertEquals(mockUser.getEmail(), Optional.of(mockTest).get().getEmail());
		assertNotNull(userServ.save(mockUser));
	}
	
	@Test
	void testUserUpdate() throws UsernameAlreadyExistsException, EmailAlreadyExistsException, RecordNotFoundException {
		User mockUser = new User();
		mockUser.setId(1);
		mockUser.setEmail("test@gmail.com");
		mockUser.setUsername("testuser");
		mockUser.setFirstName("testfirst");
		mockUser.setLastName("testlast");
		mockUser.setProfileImg("http://testimg.com");
		UserDTO dto = new UserDTO(mockUser);

		Mockito.when(userRepo.findById(dto.getId())).thenReturn(Optional.of(mockUser));
		Mockito.when(userRepo.existsByEmail(dto.getEmail())).thenReturn(false);
		Mockito.when(userRepo.existsByUsername(dto.getUsername())).thenReturn(false);
		Mockito.when(userRepo.save(mockUser)).thenReturn(mockUser);
		
		User returnedUser = userServ.update(dto); 
		
		assertNotNull(returnedUser);
	}
	
	@Test
	void testUserUpdateUserNotPresent() throws EmailAlreadyExistsException {
		User mockUser = new User();
		mockUser.setId(1);
		mockUser.setEmail("testuser@gmail.com");
		Mockito.when(userRepo.findById(mockUser.getId())).thenReturn(Optional.empty());
		assertThrows(RecordNotFoundException.class, () -> {
			userServ.update(new UserDTO(mockUser));
		});
	}
	
	@Test
	void testUserUpdateEmailAlreadyExists() throws EmailAlreadyExistsException {
		User mockUser = new User();
		mockUser.setId(1);
		mockUser.setEmail("test@gmail.com");
		mockUser.setUsername("testuser");
		mockUser.setFirstName("testfirst");
		mockUser.setLastName("testlast");
		mockUser.setProfileImg("http://testimg.com");
		UserDTO dto = new UserDTO(mockUser);
		dto.setEmail("differentEmail@email.com");

		Mockito.when(userRepo.findById(dto.getId())).thenReturn(Optional.of(mockUser));
		Mockito.when(userRepo.existsByEmail(dto.getEmail())).thenReturn(true);
		assertThrows(EmailAlreadyExistsException.class, () -> {
			userServ.update(dto);
		});
	}

	@Test
	void testUserUpdateUsernameAlreadyExists() throws UsernameAlreadyExistsException {
		User mockUser = new User();
		mockUser.setId(1);
		mockUser.setEmail("test@gmail.com");
		mockUser.setUsername("testuser");
		mockUser.setFirstName("testfirst");
		mockUser.setLastName("testlast");
		mockUser.setProfileImg("http://testimg.com");
		UserDTO dto = new UserDTO(mockUser);
		dto.setUsername("differentUsername");

		Mockito.when(userRepo.findById(dto.getId())).thenReturn(Optional.of(mockUser));
		Mockito.when(userRepo.existsByUsername(dto.getUsername())).thenReturn(true);
		assertThrows(UsernameAlreadyExistsException.class, () -> {
			userServ.update(dto);
		});
	}
	
	@Test
	void testUpdateEmailAndUsernameMatches() throws EmailAlreadyExistsException, UsernameAlreadyExistsException, RecordNotFoundException {
		User mockUser = new User();
		User mockTest = new User();
		User mockUserWithId = new User();
		mockUser.setId(1);
		String username = "testuser";
		String email = "testuser@gmail.com";
		mockUser.setId(1);
		mockUser.setUsername(username);
		mockTest.setUsername(username);
		mockUser.setEmail(email);
		mockTest.setEmail(email);
		Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(mockUser));
		Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(mockUser));
		Mockito.when(userRepo.findById(mockUser.getId())).thenReturn(Optional.of(mockTest));
		Mockito.when(userRepo.save(mockUser)).thenReturn(mockUserWithId);
		Assertions.assertEquals(mockUser.getUsername(), Optional.of(mockTest).get().getUsername());
		Assertions.assertEquals(mockUser.getEmail(), Optional.of(mockTest).get().getEmail());
		assertNull(userServ.update(new UserDTO(mockUser)));
	}
	
	@Test
	void testAddFollower() throws RecordNotFoundException {
		User mockUser = new User();
		User mockTarget = new User();
		mockUser.setId(0);
		mockTarget.setId(1L);
		Mockito.when(userRepo.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
		Mockito.when(userRepo.findById(mockTarget.getId())).thenReturn(Optional.of(mockTarget));
		Mockito.when(userRepo.save(mockUser)).thenReturn(mockUser);
		Mockito.when(userRepo.save(mockTarget)).thenReturn(mockTarget);
		
		assertTrue(userServ.addFollower(mockUser.getId(), mockTarget.getId()));
		
	}
	
	@Test
	void testAddFollowerUserNotPresent() throws RecordNotFoundException {
		User mockUser = new User();
		User mockTarget = new User();
		mockUser.setId(0);
		mockTarget.setId(1L);
		Mockito.when(userRepo.findById(mockUser.getId())).thenReturn(Optional.empty());
		Mockito.when(userRepo.findById(mockTarget.getId())).thenReturn(Optional.of(mockTarget));
		assertThrows(RecordNotFoundException.class, () -> {
			userServ.addFollower(mockUser.getId(), mockTarget.getId());
		});
	}
	
	@Test
	void testAddFollowerTargetNotPresent() throws RecordNotFoundException {
		User mockUser = new User();
		User mockTarget = new User();
		mockUser.setId(0);
		mockTarget.setId(1L);
		Mockito.when(userRepo.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
		Mockito.when(userRepo.findById(mockTarget.getId())).thenReturn(Optional.empty());
		assertThrows(RecordNotFoundException.class, () -> {
			userServ.addFollower(mockUser.getId(), mockTarget.getId());
		});
	}
	
	@Test
	void testGetFollowersSuccess() {
		User mockUser = new User();
		User mockFollower = new User();
		Set<User> followers = new HashSet<>();
		followers.add(mockFollower);
		long id = 0;
		mockUser.setFollowers(followers);
		Mockito.when(userRepo.findById(id)).thenReturn(Optional.of(mockUser));
		assertEquals(mockUser.getFollowers(), userServ.getFollowers(mockUser));
	}
	
	@Test
	void testGetFollowersEmptySet() {
		User mockUser = new User();
		User mockFollower = new User();
		Set<User> followers = new HashSet<>();
		followers.add(mockFollower);
		long id = 1;
		mockUser.setFollowers(followers);
		Mockito.when(userRepo.findById(id)).thenReturn(Optional.of(mockUser));
		Set<User> returnedFollowers = userServ.getFollowers(mockUser);
		assertNotNull(returnedFollowers);
	}
	
	@Test
	void testGetFollowingSuccess() {
		User mockUser = new User();
		User mockFollowing = new User();
		Set<User> following = new HashSet<>();
		following.add(mockFollowing);
		long id = 0;
		mockUser.setFollowers(following);
		Mockito.when(userRepo.findById(id)).thenReturn(Optional.of(mockUser));
		assertEquals(mockUser.getFollowing(), userServ.getFollowing(mockUser));
	}
	
	@Test
	void testGetFollowingEmptySet() {
		User mockUser = new User();
		User mockFollowing = new User();
		Set<User> following = new HashSet<>();
		following.add(mockFollowing);
		long id = 1;
		mockUser.setFollowers(following);
		Mockito.when(userRepo.findById(id)).thenReturn(Optional.of(mockUser));
		Set<User> returnedFollowing = userServ.getFollowing(mockUser);
		assertNotNull(returnedFollowing);
	}
	
	@Test
	void testDoesEmailAlreadyExist() {
		String email = "test@gmail.com";
		Mockito.when(userRepo.existsByEmail(email)).thenReturn(true);
		assertTrue(userServ.doesEmailAlreadyExist(email));
	}
	
	@Test
	void testDoesUsernameAlreadyExist() {
		String username = "test";
		Mockito.when(userRepo.existsByUsername(username)).thenReturn(true);
		assertTrue(userServ.doesUsernameAlreadyExist(username));
	}
	
	@Test
	void findById(){
		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(new User()));
		Assertions.assertNotNull(userServ.findById(Mockito.anyLong()));
	}
	
	@Test
	void findAllUsers() {
		Mockito.when(userRepo.findAll()).thenReturn(new ArrayList<User>());
		Assertions.assertNotNull(userServ.findAllUsers());
	}
	
	@Test
	void findAllUsersFromList() {
		Mockito.when(userRepo.findAllById(Mockito.anyIterable())).thenReturn(new ArrayList<User>());
		Assertions.assertNotNull(userServ.findAllUsersFromList(Mockito.anyList()));
	}
	
	
	
}
