package com.revature.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.UserDTO;
import com.revature.dtos.UserMiniDTO;
import com.revature.dtos.UserPassDTO;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.RecordNotFoundException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.ImageService;
import com.revature.services.ResetPWService;
import com.revature.services.UserService;

/**
 * @author Colby Tang
 */
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
	@MockBean
	private UserService userService;

	@MockBean
	private ResetPWService resetPWService;

	@MockBean
	private ImageService imageService;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Tests getting a user by id
	 * 
	 * @throws JsonProcessingException
	 * @throws Exception
	 * @author Colby Tang
	 */
	@Test
	void getUserById() throws JsonProcessingException, Exception {
		User mockUser = new User();
		mockUser.setId(1l);
		mockUser.setUsername("username");
		mockUser.setEmail("email");
		mockUser.setFirstName("firstName");
		mockUser.setLastName("lastName");
		mockUser.setPassword("password");
		mockUser.setProfileImg("profileImg");
		mockUser.setFollowers(new HashSet<User>());
		mockUser.setFollowing(new HashSet<User>());
		UserDTO mockUserDTO = new UserDTO(mockUser);
		Mockito.when(userService.findById(1l)).thenReturn(Optional.of(mockUser));
		mockMvc.perform(get("/user/1")).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(mockUserDTO)));
	}

	/**
	 * Try to get a user that doesn't exist in the database.
	 * 
	 * @throws JsonProcessingException
	 * @throws Exception
	 * @author Colby Tang
	 */
	@Test
	void getUserByIdEmpty() throws JsonProcessingException, Exception {
		Mockito.when(userService.findById(1l)).thenReturn(Optional.empty());
		mockMvc.perform(get("/user/1")).andExpect(status().isNotFound());
	}

	/**
	 * Retrieving a list of UserMiniDTO by passing in a list of user ids
	 * 
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test
	void getUsersMiniList() throws JsonProcessingException, Exception {
		User mockUser = new User();
		mockUser.setId(1l);
		mockUser.setUsername("username");
		mockUser.setProfileImg("profileImg");

		UserMiniDTO mockUserDTO = new UserMiniDTO(mockUser);
		List<Long> userIds = new ArrayList<>();
		userIds.add(1l);
		List<User> usersList = new ArrayList<>();
		usersList.add(mockUser);
		List<UserMiniDTO> usersMiniList = new ArrayList<>();
		usersMiniList.add(mockUserDTO);

		Mockito.when(userService.findAllUsersFromList(userIds)).thenReturn(usersList);
		mockMvc.perform(get("/user/mini").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userIds))).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(usersMiniList)));
	}

	/**
	 * Passing in an empty list of user ids should return an empty list of
	 * UserMiniDTO
	 * 
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test
	void getUsersMiniListEmpty() throws JsonProcessingException, Exception {
		List<Long> userIds = new ArrayList<>();
		List<User> usersList = new ArrayList<>();
		List<UserMiniDTO> usersMiniList = new ArrayList<>();

		Mockito.when(userService.findAllUsersFromList(userIds)).thenReturn(usersList);
		mockMvc.perform(get("/user/mini").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userIds))).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(usersMiniList)));
	}

	/**
	 * Passing an email that does not exist, should return a 400 status As of the
	 * writing of this test, the email does not exist
	 * 
	 * @throws Exception
	 */
	@Test
	void getResetPWTokenBadRequest() throws Exception {
		/* Local Variables */
		String email = "nonexistent@void.net";

		/* Test */
		Mockito.when(userService.doesEmailAlreadyExist(email)).thenReturn(false);
		mockMvc.perform(post("/user/resetPW").contentType(MediaType.APPLICATION_JSON).content(email))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Passing an email that exists, should return a 200 status and a "ResetToken"
	 * header As of the writing of this test, the email does exist
	 * 
	 * @throws Exception
	 */
	@Test
	void getResetPWTokenOk() throws Exception {
		/* Local Variables */
		String email = "testuser@gmail.com";
		User mockUser = new User();

		/* Test */
		Mockito.when(userService.doesEmailAlreadyExist(email)).thenReturn(true);
		Mockito.when(resetPWService.generateResetToken(email)).thenReturn("123");
		Mockito.when(userService.findByEmail(email)).thenReturn(mockUser);
		mockMvc.perform(post("/user/resetPW").contentType(MediaType.APPLICATION_JSON).content(email))
				.andExpect(status().isOk());
	}

	@Test
	void cannotAddFollower() throws Exception {
		UserDTO dto = new UserDTO();
		Mockito.when(userService.addFollower(1L, 1L)).thenReturn(dto);

		mockMvc.perform(post("/user/1/follower/1")).andExpect(status().isPreconditionFailed());

	}

	@Test
	void addFollower() throws Exception {
		UserDTO dto = new UserDTO();
		Mockito.when(userService.addFollower(1L, 2L)).thenReturn(dto);

		mockMvc.perform(post("/user/1/follower/2")).andExpect(status().isOk());
	}
	
	@Test
	void addFollowerBadRequest() throws Exception {
		UserDTO dto = new UserDTO();
		Mockito.when(userService.addFollower(1L, 2L)).thenReturn(null);

		mockMvc.perform(post("/user/1/follower/2")).andExpect(status().isBadRequest());
	}
	
	
	@Test
	void removeFollower() throws JsonProcessingException, Exception {
		UserDTO dto = new UserDTO(1L, "user");
		Mockito.when(userService.removeFollower(Mockito.anyLong(), Mockito.anyLong())).thenReturn(dto);
		
		mockMvc.perform(delete("/user/1/unfollow/2")).andExpect(status().isOk())
		.andExpect(content().json(objectMapper.writeValueAsString(dto)));

	}
	
	@Test
	void removeFollowerBadRequest() throws JsonProcessingException, Exception {
		UserDTO dto = new UserDTO(1L, "user");
		Mockito.when(userService.removeFollower(Mockito.anyLong(), Mockito.anyLong())).thenReturn(null);
		
		mockMvc.perform(delete("/user/1/unfollow/2")).andExpect(status().isBadRequest());
	}
	
	@Test
	void removeFollowerRecordNotFound() throws JsonProcessingException, Exception {
		UserDTO dto = new UserDTO(1L, "user");
		Mockito.when(userService.removeFollower(Mockito.anyLong(), Mockito.anyLong())).thenThrow(new RecordNotFoundException());
		
		mockMvc.perform(delete("/user/1/unfollow/2")).andExpect(status().isNotFound());
	}

	@Test
	void cannotFindFollower() throws Exception {
		Mockito.when(userService.addFollower(1L, 2L)).thenThrow(new RecordNotFoundException());

		mockMvc.perform(post("/user/1/follower/2")).andExpect(status().isNotFound());
	}
	
	@Test
	void isFollowing() throws JsonProcessingException, Exception {
		User mockUser = new User("","","","","","");
		mockUser.setId(1L);
		Optional<User> user = Optional.of(mockUser);
		Set<User> set = new HashSet<>();
		set.add(mockUser);
		
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(user);
		Mockito.when(userService.getFollowing(Mockito.any())).thenReturn(set);
		
		
		mockMvc.perform(get("/user/1/follower/2")).andExpect(status().isOk())
		.andExpect(content().json(objectMapper.writeValueAsString(set)));
	}
	
	@Test
	void isFollowingPreConditionFail() throws JsonProcessingException, Exception {
		mockMvc.perform(get("/user/1/follower/1")).andExpect(status().isPreconditionFailed());
	}

	@Test
	void isFollowingNotFound() throws Exception {
		Optional<User> user = Optional.empty();
		
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(user);
		
		mockMvc.perform(get("/user/1/follower/2")).andExpect(status().isNotFound());
	}
	
	@Test
	void updateUser() throws Exception {
		UserDTO dto = new UserDTO(1L, "user");
		User user = new User();
		user.setId(1L);
		user.setUsername("user");
		UserMiniDTO bodyDTO = new UserMiniDTO(user);

		Mockito.when(userService.update(Mockito.any())).thenReturn(user);

		mockMvc.perform(post("/user/update/profile").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(bodyDTO)));
	}

	@Test
	void updateUserNotFound() throws Exception {
		UserDTO dto = new UserDTO(1L, "user");

		Mockito.when(userService.update(Mockito.any())).thenThrow(RecordNotFoundException.class);

		mockMvc.perform(post("/user/update/profile").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	void updateUserUsernameAlreadyExists() throws Exception {
		UserDTO dto = new UserDTO(1L, "user");

		Mockito.when(userService.update(Mockito.any())).thenThrow(UsernameAlreadyExistsException.class);

		mockMvc.perform(post("/user/update/profile").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isConflict());
	}

	@Test
	void updateUserEmailAlreadyExists() throws Exception {
		UserDTO dto = new UserDTO(1L, "user");

		Mockito.when(userService.update(Mockito.any())).thenThrow(EmailAlreadyExistsException.class);

		mockMvc.perform(post("/user/update/profile").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isConflict());
	}

	@Test
	void updatePassword() throws JsonProcessingException, Exception {
		User mockUser = new User("","","","","","");
		mockUser.setId(1L);
		UserPassDTO passDto = new UserPassDTO(mockUser); 
		UserMiniDTO bodyDTO = new UserMiniDTO(mockUser);

		Mockito.when(userService.save(Mockito.any())).thenReturn(mockUser);
		
		mockMvc.perform(post("/user/update/password").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(passDto))).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(bodyDTO)));
	}
	
}
