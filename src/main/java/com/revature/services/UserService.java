package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

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
}
