package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;

@SpringBootTest(classes = PostService.class)
class PostServiceTest {
	@MockBean
	private UserRepository userRepo;
	
	@MockBean
	private PostRepository postRepo;
	
	@Autowired
	private PostService postServ;

	@Test
	void testGetPost() {
		Post mockPost = new Post();
		long id = 1;
		
		mockPost.setId(id);
		
		Mockito.when(postRepo.findById(id)).thenReturn(Optional.of(mockPost));
		
		Assertions.assertEquals(mockPost, postServ.getPost(id));
		
	}
	
	@Test
	void testGetPostNull() {
		Post mockPost = new Post();
		long id = 1;
		
		mockPost.setId(id);
		
		Mockito.when(postRepo.findById(id)).thenReturn(Optional.empty());
		
		Assertions.assertNull(postServ.getPost(id));
		
	}

	@Test
	void testGetAll() {
		List<Post> mockPostList = new ArrayList<>();
		
		Mockito.when(postRepo.findAll()).thenReturn(mockPostList);
		
		List<Post> returnedPostList = postServ.getAll();
		
		Assertions.assertNotNull(returnedPostList);
		
	}

	@Test
	void testUpsert() {
		Post mockPost = new Post();
		Post mockPostWithId = new Post();
		mockPostWithId.setId(1L);
		
		Mockito.when(postRepo.save(mockPost)).thenReturn(mockPostWithId);
		
		Post returnedPost = postServ.upsert(mockPost);
		
		Assertions.assertNotNull(returnedPost);
		
	}

	
	@Test
	void getUserFeed() {
		User user = new User();
		user.setId(1l);
		// Set<Post> set = new HashSet<>();
		List<Post> postList = new ArrayList<>();
		
		Mockito.when(postRepo.findUserPostFeed(user.getId())).thenReturn(postList);
		
		Assertions.assertNotNull(postServ.getUserFeed(user.getId()));
	}
}
