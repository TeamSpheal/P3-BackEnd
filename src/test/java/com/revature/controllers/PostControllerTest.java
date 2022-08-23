package com.revature.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.Instant;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.LikeRequest;
import com.revature.dtos.PostDTO;
import com.revature.dtos.UserMiniDTO;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.PostService;
import com.revature.services.UserService;

@TestInstance(Lifecycle.PER_CLASS)
@WebMvcTest(controllers = PostController.class)
class PostControllerTest {
	@MockBean
	private PostService postServ;

	@MockBean
	private UserService userServ;

	@MockBean
	private UserRepository userRepo;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();
	
	private Timestamp fixedTimedStamp;
	
	@BeforeAll
	public void setUp() {
		 //fixedTimedStamp = Timestamp.from(Instant.EPOCH);
	}

	@Test
	void testGetAllPosts() throws JsonProcessingException, Exception {
		List<Post> mockPosts = new ArrayList<>();

		Mockito.when(postServ.getAll()).thenReturn(mockPosts);

		mockMvc.perform(get("/post")).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(mockPosts)));
	}

	@Test
	void cannotUpsertPost() throws JsonProcessingException, Exception {
		User user = new User();
		user.setId(1L);
		PostDTO dto = new PostDTO(1L, "", "", new HashSet<PostDTO>(), new UserMiniDTO(user),
				new HashSet<UserMiniDTO>(),  Timestamp.from(Instant.EPOCH));
		
		Mockito.when(userServ.getUser(Mockito.anyLong())).thenReturn(null);
		
		mockMvc.perform(
				put("/post").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test
	void testUpsertPost() throws JsonProcessingException, Exception {
		User mockUser = new User("", "", "", "", "", "");
		mockUser.setId(1L);
		PostDTO dto = new PostDTO(1L, "", "", new HashSet<PostDTO>(), new UserMiniDTO(mockUser),
				new HashSet<UserMiniDTO>(),  Timestamp.from(Instant.EPOCH));
		Post mockPostWithId = new Post(dto);
		mockPostWithId.setAuthor(mockUser);
		mockPostWithId.setCreatedDate(Timestamp.from(Instant.EPOCH));

		Mockito.when(userServ.getUser(Mockito.anyLong())).thenReturn(mockUser);
		Mockito.when(postServ.upsert(Mockito.any())).thenReturn(mockPostWithId);

		mockMvc.perform(
				put("/post").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk());

	}

	@Test
	void testLikePostSuccess() throws JsonProcessingException, Exception {
		User mockUser = new User("", "", "", "", "", "");
		Post mockPost = new Post(1L, "", "", new HashSet<Post>(), new User(), new HashSet<User>(), fixedTimedStamp);
		LikeRequest like = new LikeRequest();
		Set<User> likers = new HashSet<User>();
		likers.add(mockUser);
		mockPost.setUsers(likers);
		PostDTO dto = new PostDTO(mockPost);

		like.setPostId(mockPost.getId());
		like.setUserId(mockUser.getId());

		Mockito.when(userServ.getUser(Mockito.anyLong())).thenReturn(mockUser);
		Mockito.when(postServ.getPost(Mockito.anyLong())).thenReturn(mockPost);
		Mockito.when(postServ.upsert(Mockito.any())).thenReturn(mockPost);

		mockMvc.perform(put("/post/like").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(like)))

				.andExpect(status().isOk());
	}

	@Test
	void testLikePostBadRequest() throws JsonProcessingException, Exception {
		Post mockPost = new Post();
		User mockUser = new User();
		LikeRequest like = new LikeRequest();
		mockPost.setId(1L);
		mockUser.setId(1L);

		like.setPostId(mockPost.getId());
		like.setUserId(mockUser.getId());

		Mockito.when(postServ.getPost(like.getPostId())).thenReturn(mockPost);

		mockMvc.perform(put("/post/like").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockPost))).andExpect(status().isBadRequest());
	}

	@Test
	void unlikePost() throws JsonProcessingException, Exception {

		User mockUser = new User("", "", "", "", "", "");
		Post mockPost = new Post(1L, "", "", new HashSet<Post>(), new User(), new HashSet<User>(),
				Timestamp.from(Instant.now()));
		LikeRequest like = new LikeRequest();
		Set<User> likers = new HashSet<User>();
		likers.remove(mockUser);
		mockPost.setUsers(likers);
		PostDTO dto = new PostDTO(mockPost);

		like.setPostId(mockPost.getId());
		like.setUserId(mockUser.getId());

		Mockito.when(userServ.getUser(Mockito.anyLong())).thenReturn(mockUser);
		Mockito.when(postServ.getPost(Mockito.anyLong())).thenReturn(mockPost);
		Mockito.when(postServ.upsert(Mockito.any())).thenReturn(mockPost);

		mockMvc.perform(put("/post/unlike").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(like)))

				.andExpect(status().isOk());

	}

	@Test
	void cannotUnlikePost() throws JsonProcessingException, Exception {
		LikeRequest like = new LikeRequest();
		like.setPostId(1L);
		like.setUserId(1L);

		mockMvc.perform(put("/post/unlike").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(like))).andExpect(status().isBadRequest());
	}
	
	@Test
	void getPost() throws JsonProcessingException, Exception {
		Post mockPost = new Post(1L, "", "", new HashSet<Post>(), new User(), new HashSet<User>(),
				Timestamp.from(Instant.now()));
		
		Mockito.when(postServ.getPost(Mockito.anyLong())).thenReturn(mockPost);		
		mockMvc.perform(get("/post/1")).andExpect(status().isOk());
	}
	
	@Test
	void cannotGetPost() throws Exception {		
		Mockito.when(postServ.getPost(Mockito.anyLong())).thenReturn(null);
		mockMvc.perform(get("/post/1")).andExpect(status().isBadRequest());
	}
	
	@Test
	void getFollowingPostFeed() throws Exception {
		User mockUser = new User("", "", "", "", "", "");
		mockUser.setId(1L);
		List<Post> postList = new ArrayList<>();
		Post mockPost = new Post(1L, "", "", new HashSet<Post>(), new User(), new HashSet<User>(),
				Timestamp.from(Instant.now()));
		postList.add(mockPost);
		
		Mockito.when(postServ.getUserFeed(1l)).thenReturn(postList);
		
		mockMvc.perform(get("/post/following/1")).andExpect(status().isOk());

	}
	

}
