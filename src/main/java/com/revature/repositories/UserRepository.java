package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import com.revature.dtos.UserDTO;
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
	 * Returns a List of UserDTO objects
	 * based on the input string. (FirstName
	 *
	 * @param firstName (String), lastName (String)
	 * @return List of UserDTO objects.
	 */
	Optional<List<UserDTO>> findAllByFirstNameStartingWithOrLastNameStartingWithOrderByFirstName(
			String firstName, String lastName);

	/**
	 * Checks if the email already exists in the database.
	 * 
	 * @param email
	 * @return
	 */
	public boolean existsByEmail(String email);

	/**
	 * Checks if the username already exists in the database.
	 * 
	 * @param username
	 * @return
	 */
	public boolean existsByUsername(String username);

}
