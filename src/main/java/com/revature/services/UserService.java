package com.revature.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    
    public Set<User> getFollowers(User user) {
    	Optional<User> userOpt = userRepository.findById(Long.valueOf(user.getId())); 
    	if (userOpt.isPresent()) {
    		return userOpt.get().getFollowers(); 
    	}
    	else {
    		return new HashSet<User>(); 
    	}
    	
    }
    
    //Todo: Following
}
