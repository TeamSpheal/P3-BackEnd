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
	 * Returns a user object with a given email
	 * @param email
	 * @return
	 */
	public User findByEmail(String email) {
		Optional<User> userOpt = userRepository.findByEmail(email);
		if (userOpt.isPresent()) {
			return userOpt.get();
		}
		return null;
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
	 * Validates a given user object and saves the object to the database.
	 * 
	 * @param user
	 * @return
	 * @throws EmailAlreadyExistsException
	 * @throws UsernameAlreadyExistsException
	 */
	public User save(User user) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {

		// If email already exists in the database, throw an EmailAlreadyExistsException.
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new EmailAlreadyExistsException();
		}

		// If username already exists in the database, throw an UsernameAlreadyExistsException.
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new UsernameAlreadyExistsException();
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
    		return new HashSet<>(); 
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
	public UserDTO addFollower(long userId, long targetId) throws RecordNotFoundException {
		Optional<User> oUser = userRepository.findById(userId);
		Optional<User> oTargetUser = userRepository.findById(targetId);
		User result;
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
			targetUser.addFollower(user);

			// Save both users
        	result = userRepository.save(user);
        	userRepository.save(targetUser);
        	return new UserDTO(result);
    	} catch (Exception e) {
    		e.getStackTrace(); 
    		return null; 
    	}
    }

    /**
     * To remove follower from the table
     * @param userId, the user that follows
     * @param targetId, the followed user
     * @return
     */
	public UserDTO removeFollower(long userId, long targetId) throws RecordNotFoundException {
		Optional<User> fUser = userRepository.findById(userId);
		Optional<User> oTargetUser = userRepository.findById(targetId);
		User result;
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
			targetUser.removeFollower(user);

			// Save both users
        	result = userRepository.save(user);
        	userRepository.save(targetUser);
        	return new UserDTO(result);
    	}catch (Exception e) {
    		e.getStackTrace(); 
    		return null; 
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
	 * @param dto
	 * @return
	 * @throws EmailAlreadyExistsException
	 * @throws UsernameAlreadyExistsException
	 * @throws RecordNotFoundException
	 */
	public User update(UserDTO dto) throws EmailAlreadyExistsException, UsernameAlreadyExistsException, RecordNotFoundException {
		/* Local Variables */
		Optional<User> oUser = userRepository.findById(dto.getId());
		User user;

		/* Validate Data */

		// If user does not exist, throw an exception.
		if (!oUser.isPresent()) {
			throw new RecordNotFoundException("User is not found!");
		}

		// Unwrap the user from the optional
		user = oUser.get();

		// If emails are different and if it already exists in the database, throw an EmailAlreadyExistsException.
		if (!dto.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
			throw new EmailAlreadyExistsException();
		}

		// If usernames are different and if it already exists in the database, throw an UsernameAlreadyExistsException.
		if (!dto.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(dto.getUsername())) {
			throw new UsernameAlreadyExistsException();
		}

		/* Construct User Object */
		user.setEmail(dto.getEmail());
		user.setUsername(dto.getUsername());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setProfileImg(dto.getProfileImg());

		/* Pass to repository and return the result */
		return userRepository.save(user);
	}
}

