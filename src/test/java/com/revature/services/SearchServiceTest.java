package com.revature.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;

@SpringBootTest(classes=SocialMediaApplication.class)
public class SearchServiceTest {
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private PostRepository postRepo;
	
	@Test 
	@DisplayName("")
	public void viewAllPost() {
		
	
	}
	@Test
	@DisplayName("Test should Pass when user search for friends")
	public void showAllUser() {
		SearchServiceImpl mockSearch = new SearchServiceImpl(userRepo, postRepo);
		
		
	}
	

}


