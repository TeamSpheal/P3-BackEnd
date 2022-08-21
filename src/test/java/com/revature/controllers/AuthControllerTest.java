package com.revature.controllers;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.TokenService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.time.Instant;
import java.time.LocalDateTime;

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
public class AuthControllerTest {
	
	@MockBean
	private AuthService authServ;
	
	@MockBean 
	private TokenService tokenServ;
	
	@MockBean
	private Logger logger;
	
	@MockBean 
	private PostController postCont;
	
	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();
	
	/*
	@Test
	void login() {
		User mockUser = new User("","","","","","");
		mockUser.setId(1L);
		String returnString = "";
		UserDTO dto = new UserDTO(mockUser);

		
		Mockito.when(authServ.findByCredentials(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(mockUser));
		Mockito.when(tokenServ.createToken(Mockito.any())).thenReturn(returnString);
		
		mockMvc.perform(post("/auth/login").session(se).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new LoginRequest())).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(bodyDTO)));		
	}
	*/
	
	/*
	@Test
	void register() throws JsonProcessingException, Exception {
		RegisterRequest rr = new RegisterRequest("","","","","", new HashSet<User>(), "");
		User mockUser = new User(rr.getUsername(), rr.getEmail(), rr.getPassword(), rr.getFirstName(), rr.getLastName(), rr.getProfileImg());
		mockUser.setId(1L);
		UserDTO dto = new UserDTO(mockUser);
		
		Mockito.when(authServ.register(Mockito.any())).thenReturn(mockUser);

		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(rr)))
				.andExpect(status().isCreated())
				.andExpect(content().json(objectMapper.writeValueAsString(dto)));	
	}
	*/
	

}
