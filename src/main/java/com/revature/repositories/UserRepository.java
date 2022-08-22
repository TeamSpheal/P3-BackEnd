package com.revature.repositories;


import java.util.ArrayList;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// To check if the user is already following the target id. 
	@Query(value = "select * from follow where target_id = ?1 and user_id = ?2", nativeQuery = true)
	public boolean isAlready(int target, int user); 
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
	 * 
	 * @param name
	 * @return
	 */
	Optional<ArrayList<User>> findAllByFirstNameContaining(String name);

	/**
	 * Checks if the email already exists in the database.
	 * @param email
	 * @return
	 */
	public boolean existsByEmail (String email);

	/**
	 * Checks if the username already exists in the database. 
	 * @param username
	 * @return
	 */
	public boolean existsByUsername (String username);
}
