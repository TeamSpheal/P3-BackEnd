package com.revature.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.revature.annotations.Authorized;
import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
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
    public ResponseEntity<User> getUser (@PathVariable long id) {
        Optional<User> oUser = userService.findById(id);
        if (oUser.isPresent()) {
            return ResponseEntity.ok(oUser.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    
    // Add follower to the logged in user 
    @PostMapping("/{followedId}/follower/{followerId}") 
	public ResponseEntity<Void> addFollower(@PathVariable("followedId") Long followed_id, 
											@PathVariable("followerId") Long follower_id) {
		//TODO: check if id's are the same
		if ( followed_id instanceof Long && follower_id instanceof Long) {
			if (true) { // check the ids if they are the same.
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
    
    // TODO: unfollow 
    @PutMapping("/{followedId}/unfollower/{followerId}") 
    public ResponseEntity<Void> removeFollower(@PathVariable("followedId") Long followed_id, 
			@PathVariable("followerId") Long follower_id) {
    	
    		boolean isRemoved = userService.removeFollower(followed_id, follower_id); 
    		if (isRemoved) {
    			return ResponseEntity.status(HttpStatus.OK).build();
    			
    		}else {
    			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    		}
    	
    	
    	
				
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
