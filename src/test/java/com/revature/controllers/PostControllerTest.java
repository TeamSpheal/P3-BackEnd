package com.revature.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
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

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {
    @MockBean
    private PostService postServ;

    @MockBean
    private UserService userServ;
    
    @MockBean
    private UserRepository userRepo;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllPosts() throws JsonProcessingException, Exception {
        List<Post> mockPosts = new ArrayList<>();

        Mockito.when(postServ.getAll()).thenReturn(mockPosts);
        
        mockMvc.perform(get("/post"))
                       .andExpect(status().isOk())
                       .andExpect(content().json(objectMapper.writeValueAsString(mockPosts)));
    }

   @Test
    void testUpsertPost() throws JsonProcessingException, Exception {
        Post mockPost = new Post();   
        PostDTO dto = new PostDTO(1L, "","", new ArrayList<PostDTO>(), new UserMiniDTO(), new HashSet<UserMiniDTO>());
        Post mockPostWithId = new Post(dto);
       // post.setAuthor(new UserMiniDTO(1l, "username", "profileURL"));

        Mockito.when(postServ.upsert(Mockito.any())).thenReturn(mockPostWithId);
        
        mockMvc.perform(put("/post").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(mockPostWithId)));
        
   }
    
    @Test
    void testLikePostSuccess() throws JsonProcessingException, Exception {
    	User mockUser = new User("","","","","","");
    	Post mockPost = new Post(1L,"","", new ArrayList<Post>(), mockUser, new HashSet<User>());
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
    	
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(dto)));
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
				.content(objectMapper.writeValueAsString(mockPost)))
				.andExpect(status().isBadRequest());
    }
    
   
    @Test
    void unlikePost() throws JsonProcessingException, Exception {
    	
    	User mockUser = new User("","","","","","");
    	Post mockPost = new Post(1L,"","", new ArrayList<Post>(), mockUser, new HashSet<User>());
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
    	
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(dto)));
    	
    	
    }
    
    @Test
    void cannotUnlikePost() throws JsonProcessingException, Exception {
    	LikeRequest like = new LikeRequest();
    	like.setPostId(1L);
    	like.setUserId(1L);
    	
    	mockMvc.perform(put("/post/unlike").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(like)))
				.andExpect(status().isBadRequest());
    }
    
    
    
    
   
}
