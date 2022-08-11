package com.revature.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.PostDTO;
import com.revature.models.Post;
import com.revature.repositories.UserRepository;
import com.revature.services.PostService;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {
    @MockBean
    private PostService postServ;
    
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
        Post mockPostWithId = new Post();
        mockPostWithId.setId(1);

        Mockito.when(postServ.upsert(mockPost)).thenReturn(mockPostWithId);
        
        mockMvc.perform(put("/post").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockPost)))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(new PostDTO(mockPostWithId))));
        
    }
    
    
    
    
    
    
    
    
    
    
    
}
