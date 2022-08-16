package com.revature.services;

import com.revature.dtos.UserDTO;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
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

    public User save(User user) throws EmailAlreadyExistsException, UsernameAlreadyExistsException{
    	Optional<User> testOpt;
    	User test = new User();
    	
    	if (!userRepository.findByEmail(user.getEmail()).equals(Optional.empty())) {
    		testOpt = userRepository.findById(user.getId());
    		if (testOpt.isPresent()) {
        		test = testOpt.get();
    			if (!user.getEmail().toString().equals(test.getEmail().toString())) {
    	    		throw new EmailAlreadyExistsException();
    			}
    		} else {
	    		throw new EmailAlreadyExistsException();    			
    		}
    	}
    	
    	if (!userRepository.findByUsername(user.getUsername()).equals(Optional.empty())) {
    		testOpt = userRepository.findById(user.getId());
    		if (testOpt.isPresent()) {
        		test = testOpt.get();
    			if (!user.getUsername().toString().equals(test.getUsername().toString())) {
    	    		throw new UsernameAlreadyExistsException();
    			}
    		} else {
	    		throw new UsernameAlreadyExistsException();			
    		}
    	}
        return userRepository.save(user);
    }

    /**
     * Checks if email is already in the database.
     * @param email
     * @return
     */
    public boolean doesEmailAlreadyExist (String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Checks if username is already in the database.
     * @param username
     * @return
     */
    public boolean doesUsernameAlreadyExist (String username) {
        return userRepository.existsByUsername(username);
    }
    
    public User update(UserDTO user) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
    	Optional<User> testOpt;
    	User test = new User();
    	
    	if (!userRepository.findByEmail(user.getEmail()).equals(Optional.empty())) {
    		testOpt = userRepository.findById(user.getId());
    		if (testOpt.isPresent()) {
        		test = testOpt.get();
    			if (!user.getEmail().toString().equals(test.getEmail().toString())) {
    	    		throw new EmailAlreadyExistsException();
    			}
    		} else {
	    		throw new EmailAlreadyExistsException();    			
    		}
    	}
    	
    	if (!userRepository.findByUsername(user.getUsername()).equals(Optional.empty())) {
    		testOpt = userRepository.findById(user.getId());
    		if (testOpt.isPresent()) {
        		test = testOpt.get();
    			if (!user.getUsername().toString().equals(test.getUsername().toString())) {
    	    		throw new UsernameAlreadyExistsException();
    			}
    		} else {
	    		throw new UsernameAlreadyExistsException();			
    		}
    	}
    	
    	test.setEmail(user.getEmail());
    	test.setUsername(user.getUsername());
    	test.setFirstName(user.getFirstName());
    	test.setLastName(user.getLastName());
    	test.setProfileImg(user.getProfileImg());
      return userRepository.save(test);
    }
}
