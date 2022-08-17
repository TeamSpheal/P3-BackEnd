package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.revature.dtos.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

	// Since we don't have follow entity, we can't create repository for it.
	// The alternative way is to use @Query annotation to specify the raw SQL.
	// In this method, we are inserting values passed to the addFollower method to
	// the
	// follow entity.
	@Transactional
	@Modifying
	@Query(value = "insert into follow(followed_id,follower_id) values (?1,?2)", nativeQuery = true)
	void addFollower(long id, long follower);

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
	Optional<List<UserDTO>> findAllByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithOrderByFirstName(String firstName, String lastName);

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
