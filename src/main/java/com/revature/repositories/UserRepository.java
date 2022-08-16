package com.revature.repositories;


import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Returns a user object based on a given email and password
	 * 
	 * @param email    (String)
	 * @param password (String)
	 * @return a user object
	 */
    Optional<User> findByEmailAndPassword(String email, String password);

	/**
	 * Returns a user object based on a given email
	 * 
	 * @param email (String)
	 * @return a user object
	 */
	  Optional<User> findByEmail(String email);

	/**
	 * Returns a user object based on a given username
	 * 
	 * @param username (String)
	 * @return a user object
	 */
    Optional<User> findByUsername(String username);

	/**
	 * Returns a ArrayList of UserDTO objects
	 * based on the input string.
	 *
	 * @param name (String)
	 * @return ArrayList of UserDTO objects.
	 */
	Optional<ArrayList<User>> findAllByFirstNameContaining(String name);
}
