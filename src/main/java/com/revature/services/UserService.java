package com.revature.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getUser(long id) {
    	Optional<User> userOpt = userRepository.findById(id);
    	if (userOpt.isPresent()) {
    		return userOpt.get();
    	}
    	return null;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers () {
        return userRepository.findAll();
    }

    /**
     * Takes in a list of user ids and returns a list of users that
     * are in the list.
     * @param userIds
     * @return
     */
    public List<User> findAllUsersFromList (List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    
    // Returns user's followers
    public Set<User> getFollowers(User user) {
    	Optional<User> userOpt = userRepository.findById(Long.valueOf(user.getId())); 
    	if (userOpt.isPresent()) {
    		return userOpt.get().getFollowers(); 
    	}
    	else {
    		return new HashSet<User>(); 
    	}
    }
    
    // Returns set of individuals the user is following
    public Set<User> getFollowing(User user) {
    	Optional<User> userOpt = userRepository.findById(Long.valueOf(user.getId())); 
    	if (userOpt.isPresent()) {
    		return userOpt.get().getFollowing(); 
    	}else {
    		return new HashSet<User>();
    	}
    }
    
    // 
    public boolean addFollower(long followedId, long follwerId) {
    	try {
        	userRepository.addFollower(followedId, follwerId);
        	return true; 
    	}catch (Exception e) {
    		e.getStackTrace(); 
    		return false; 
    	}
    }

	public boolean removeFollower(Long followed_id, Long follower_id) {
		// TODO Auto-generated method stub
		return false;
	}
}
