package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;


import com.revature.annotations.Authorized;
import com.revature.dtos.UserDTO;
import com.revature.dtos.UserMiniDTO;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.UserService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    /**
     * Get the user by id. Returns a 404 if the user cannot be found.
     * @param id
     * @return
     */
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser (@PathVariable long id) {
        // Finding a user by id from the repository returns an optional user.
        Optional<User> oUser = userService.findById(id);

        // If user is not null, then send a 200 with the user object.
        if (oUser.isPresent()) {
            UserDTO user = new UserDTO(oUser.get());
            return ResponseEntity.ok(user);
        }

        // Otherwise, send 404
        return ResponseEntity.notFound().build();
    }

    

    // Add follower to the logged in user 
    @PostMapping("/{followedId}/follower/{followerId}") 
    	public ResponseEntity<Void> addFollower(@PathVariable("followedId") Long followed_id, 
    											@PathVariable("followerId") Long follower_id) {
    		//TODO: check if id's are the same
    		if ( followed_id instanceof Long && follower_id instanceof Long) {
    			if (true) {
    				boolean isAdded = userService.addFollower(followed_id, follower_id); 
    	    		if (isAdded) {
    	    			return ResponseEntity.status(HttpStatus.OK).build();
    	    		} else {
    	    			return ResponseEntity.status(HttpStatus.CONFLICT).build();
    	    		}
    			} else {
    				return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); 
    			}
    		}else {
    			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
    		}
    		
    	
    }
    
    
    /**
     * Gets a list of users from the list of user ids. Returns an empty list if none of the
     * users can be found.
     * @param id
     * @return
     */
    @Authorized
    @GetMapping("/mini")
    public ResponseEntity<List<UserMiniDTO>> getUsersMiniList (@RequestBody List<Long> userIds) {
        // Finding a user by id from the repository returns an optional user.
        List<User> usersList = userService.findAllUsersFromList(userIds);

        // Convert user list into user mini dto list
        List<UserMiniDTO> usersDTOList = new ArrayList<>();
        usersList.stream().forEach(n -> usersDTOList.add(new UserMiniDTO(n)));

        // Send a 200 with the users.
        return ResponseEntity.ok(usersDTOList);
    }
    
    /**
     * Update a user's information based on a given user object
     * @param updatedUser
     * @return a UserMiniDTO object
     * @throws EmailAlreadyExistsException
     * @throws UsernameAlreadyExistsException
     */
    @PostMapping("/update/profile")
    public ResponseEntity<UserMiniDTO> updateUser (@RequestBody UserDTO updatedUser) throws EmailAlreadyExistsException, UsernameAlreadyExistsException{
    	//Pass object to service layer
    	User result = userService.update(updatedUser);
    	
    	//Assuming an exception is not thrown, remove unnecessary data and return it with a status of 200
    	UserMiniDTO bodyDTO = new UserMiniDTO(result);
    	return ResponseEntity.ok(bodyDTO);
    }
    
    /**
     * Update a user's password based on a given user object
     * @param updatedUser
     * @return a UserMiniDTO object
     * @throws EmailAlreadyExistsException
     * @throws UsernameAlreadyExistsException
     */
    @PostMapping("/update/password")
    public ResponseEntity<UserMiniDTO> updatePW (@RequestBody User updatedUser) throws EmailAlreadyExistsException, UsernameAlreadyExistsException{
    	//Pass object to service layer
    	User result = userService.save(updatedUser);

    	//Assuming an exception is not thrown, remove unnecessary data and return it with a status of 200
    	UserMiniDTO bodyDTO = new UserMiniDTO(result);
    	return ResponseEntity.ok(bodyDTO);
    }
}
