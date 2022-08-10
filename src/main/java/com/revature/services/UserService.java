package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    
    public Set<User> getFollowers(int id) {
    	Optional<User> user = userRepository.findById(id); 
    	if (user.isPresent()) {
    		return user.get().getFollowers(); 
    	}
    	return new HashSet<User>(); 
    }
    
    //Todo: Following
}
