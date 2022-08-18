package com.revature.services;

import com.revature.dtos.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest(classes=SocialMediaApplication.class)
public class SearchServiceTest {
	@MockBean
	private UserRepository userRepo;
	
	@Test 
	@DisplayName("")
	public void viewAllPost() {
		
	
	}
	@Test
	@DisplayName("Test should Pass when user search for friends")
	public void showAllUser() {
		List<UserDTO> mockUsers = new ArrayList<>();
		SearchServiceImpl mockSearch = new SearchServiceImpl(userRepo);

		Optional<List<UserDTO>> serviceReturn = mockSearch.queryUserTable("test_user");
		Mockito.when(userRepo.findAllByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithOrderByFirstName(
				"test", "User"))
				.thenReturn(Optional.of(mockUsers));

		assertNotNull("Good",serviceReturn);
	}
}