package com.revature.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.dtos.UserDTO;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.RecordNotFoundException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

	/**
	 * A constructor to be used to inject dependencies
	 * 
	 * @param userRepository
	 */
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Returns a user object with a given id
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			return userOpt.get();
		}
		return null;
	}

	/**
	 * Returns a user object with a given email and password
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public Optional<User> findByCredentials(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	/**
	 * This is unnecessary, Going to talk to the group about removing it
	 * 
	 * @param id
	 * @return
	 */
	public Optional<User> findById(long id) {
		return userRepository.findById(id);
	}

	/**
	 * Returns a list of all users in the database
	 * 
	 * @return
	 */
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Takes in a list of user ids and returns a list of users that are in the list.
	 * 
	 * @param userIds
	 * @return
	 */
	public List<User> findAllUsersFromList(List<Long> userIds) {
		return userRepository.findAllById(userIds);
	}

	/**
	 * Validate the information within a given user object and saves the object ot
	 * the database
	 * 
	 * @param user
	 * @return
	 * @throws EmailAlreadyExistsException
	 * @throws UsernameAlreadyExistsException
	 */
	public User save(User user) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
		/* Local Variables */
		Optional<User> testOpt;
		User test = new User();

		/* Validate Data */
		// Test if the email exists and if it does test if it is linked to the current
		// user object
		if (!userRepository.findByEmail(user.getEmail()).equals(Optional.empty())) {// Email already exists
			testOpt = userRepository.findById(user.getId());
			if (testOpt.isPresent()) {// Record with the given id exists
				test = testOpt.get();
				if (!user.getEmail().toString().equals(test.getEmail().toString())) {// Given email does not match the
																						// of current object in database
					throw new EmailAlreadyExistsException();
				}
			} else {// Record with given id does not exists
				throw new EmailAlreadyExistsException();
			}
		}

		// Test if the username exists and if it does test if it is linked to the
		// current user object
		if (!userRepository.findByUsername(user.getUsername()).equals(Optional.empty())) {// Username already exists
			testOpt = userRepository.findById(user.getId());
			if (testOpt.isPresent()) {// Record with the given id exists
				test = testOpt.get();
				if (!user.getUsername().toString().equals(test.getUsername().toString())) {// Given email does not match
																							// the of current object in
																							// database
					throw new UsernameAlreadyExistsException();
				}
			} else {// Record with given id does not exists
				throw new UsernameAlreadyExistsException();
			}
		}

		/* Pass to repository and return the result */
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
    	Optional<User> userOpt = userRepository.findById(user.getId());
    	if (userOpt.isPresent()) {
    		return userOpt.get().getFollowing(); 
    	}
		return new HashSet<>();
    }
    
    // 
    public boolean addFollower(long userId, long targetId) throws RecordNotFoundException {
		Optional<User> oUser = userRepository.findById(userId);
		
		Optional<User> oTargetUser = userRepository.findById(targetId);
		if (!oUser.isPresent()) {
			throw new RecordNotFoundException("Current user not found!");
		}
		if (!oTargetUser.isPresent()) {
			throw new RecordNotFoundException("Target user not found!");
		}
		
		// to see if it already exist. 
		
    	try {
			User user = oUser.get();
			User targetUser = oTargetUser.get();

			// Update user follower/following lists
			user.followUser(targetUser);

			// Save both users
        	userRepository.save(user);
        	userRepository.save(targetUser);
        	return true;
    	}catch (Exception e) {
    		e.getStackTrace(); 
    		return false; 
    	}
    }

    /**
     * To remove follower from the table
     * @param userId, the user that follows
     * @param targetId, the followed user
     * @return
     */
	public boolean removeFollower(long userId, long targetId) throws RecordNotFoundException {
		Optional<User> fUser = userRepository.findById(userId);
		Optional<User> oTargetUser = userRepository.findById(targetId);
		if (!fUser.isPresent()) {
			throw new RecordNotFoundException("Current user not found!");
		}
		if (!oTargetUser.isPresent()) {
			throw new RecordNotFoundException("Target user not found!");
		}
    	try {
			User user = fUser.get();
			User targetUser = oTargetUser.get();

			// Update user follower/following lists
			user.unFollowUser(targetUser);

			// Save both users
        	userRepository.save(user);
        	userRepository.save(targetUser);
        	return true;
    	}catch (Exception e) {
    		e.getStackTrace(); 
    		return false; 
    	}
	}
	
	


	/**
	 * Checks if email is already in the database.
	 * 
	 * @param email
	 * @return
	 */
	public boolean doesEmailAlreadyExist(String email) {
		return userRepository.existsByEmail(email);
	}

	/**
	 * Checks if username is already in the database.
	 * 
	 * @param username
	 * @return
	 */
	public boolean doesUsernameAlreadyExist(String username) {
		return userRepository.existsByUsername(username);
	}

	/**
	 * Validate the information within a given user object and saves the object ot
	 * the database
	 * 
	 * @param user
	 * @return
	 * @throws EmailAlreadyExistsException
	 * @throws UsernameAlreadyExistsException
	 */
	public User update(UserDTO user) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
		/* Local Variables */
		Optional<User> testOpt;
		User test = new User();

		/* Validate Data */
		// Test if the email exists and if it does test if it is linked to the current
		// user object
		if (!userRepository.findByEmail(user.getEmail()).equals(Optional.empty())) {// Email already exists
			testOpt = userRepository.findById(user.getId());
			if (testOpt.isPresent()) {// Record with the given id exists
				test = testOpt.get();
				if (!user.getEmail().toString().equals(test.getEmail().toString())) {// Given email does not match the
																						// of current object in database
					throw new EmailAlreadyExistsException();
				}
			} else {// Record with given id does not exists
				throw new EmailAlreadyExistsException();
			}
		}

		// Test if the username exists and if it does test if it is linked to the
		// current user object
		if (!userRepository.findByUsername(user.getUsername()).equals(Optional.empty())) {// Username already exists
			testOpt = userRepository.findById(user.getId());
			if (testOpt.isPresent()) {// Record with the given id exists
				test = testOpt.get();
				if (!user.getUsername().toString().equals(test.getUsername().toString())) {// Given email does not match
																							// the of current object in
																							// database
					throw new UsernameAlreadyExistsException();
				}
			} else {// Record with given id does not exists
				throw new UsernameAlreadyExistsException();
			}
		}

		/* Construct User Object */
		test.setEmail(user.getEmail());
		test.setUsername(user.getUsername());
		test.setFirstName(user.getFirstName());
		test.setLastName(user.getLastName());
		test.setProfileImg(user.getProfileImg());

		/* Pass to repository and return the result */
		return userRepository.save(test);
	}
}

