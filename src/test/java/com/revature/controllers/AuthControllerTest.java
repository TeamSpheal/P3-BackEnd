package com.revature.controllers;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.TokenService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.dtos.LoginRequest;


@WebMvcTest(controllers = AuthController.class)
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
	
	@Test
	void login() throws JsonProcessingException, Exception {
		User mockUser = new User("","","","","","");
		mockUser.setId(1L);
		String returnString = "";
		UserDTO dto = new UserDTO(mockUser);
		LoginRequest loginRequest = new LoginRequest("email", "password");

		
		Mockito.when(authServ.findByCredentials(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(mockUser));
		Mockito.when(tokenServ.createToken(Mockito.any())).thenReturn(returnString);
		
		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(dto)));
	}

	@Test
	void loginWrongCredentials() throws JsonProcessingException, Exception {
		User mockUser = new User("","","","","","");
		mockUser.setId(1L);
		LoginRequest loginRequest = new LoginRequest("email", "password");

		Mockito.when(authServ.findByCredentials(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
		
		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void logout() throws Exception {
		MockHttpSession session = new MockHttpSession();
		mockMvc.perform(post("/auth/logout").contentType(MediaType.APPLICATION_JSON)
		.session(session))
		.andExpect(status().isOk());
	}
	
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

	@Test
	void registerEmailAlreadyExists() throws JsonProcessingException, Exception {
		RegisterRequest rr = new RegisterRequest("","","","","", new HashSet<User>(), "");
		Mockito.when(authServ.register(Mockito.any())).thenThrow(EmailAlreadyExistsException.class);

		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(rr)))
				.andExpect(status().isConflict());
	}

	@Test
	void registerUsernameAlreadyExists() throws JsonProcessingException, Exception {
		RegisterRequest rr = new RegisterRequest("","","","","", new HashSet<User>(), "");
		Mockito.when(authServ.register(Mockito.any())).thenThrow(UsernameAlreadyExistsException.class);

		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(rr)))
				.andExpect(status().isConflict());
	}

}
