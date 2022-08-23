package com.revature.services;

import com.revature.dtos.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
public class SearchServiceTest {
	@MockBean
	private UserRepository userRepo;

	@Test
	@DisplayName("Test should Pass when user search for friends")
	void showAllUser() {
		List<UserDTO> mockUsers = new ArrayList<>();
		SearchServiceImpl mockSearch = new SearchServiceImpl(userRepo);

		Optional<List<UserDTO>> serviceReturn = mockSearch.queryUserTable("test_user");
		Mockito.when(userRepo.findAllByFirstNameStartingWithOrLastNameStartingWithOrderByFirstName(
				"test", "User"))
				.thenReturn(Optional.of(mockUsers));

		assertNotNull("Good",serviceReturn);
	}
}
