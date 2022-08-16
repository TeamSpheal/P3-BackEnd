package com.revature.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.revature.SocialMediaApplication;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;

@SpringBootTest(classes=SocialMediaApplication.class)
public class SearchServiceTest {
	
	@MockBean
	private UserRepository userRepo;
	
	@MockBean 
	private PostRepository postRepo;
	
	@Autowired
	private SearchService searchServ;
	
	
	
	@Test
	public void showAllUser() {
		
		
		ArrayList<User> mockListUser = new ArrayList<>();
		
		
		Mockito.when(userRepo.findAllByFirstNameContaining("james")).thenReturn(Optional.of(mockListUser));
		
		
		
		Optional<List<User>> returnUserlist = searchServ.queryUserTable("james");
				  
		assertNotNull(returnUserlist);
		  
		 
		
		
	}
	
	

}


