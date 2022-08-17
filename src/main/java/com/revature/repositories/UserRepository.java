package com.revature.repositories;


import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    // In this method, we are inserting values passed to the addFollower method to the 
    // follow entity. 
    @Transactional
	@Modifying
	@Query(value = "insert into follow(followed_id,follower_id) values (?1,?2)", nativeQuery = true)
	void addFollower( long id, long follower);
    
    
    @Transactional
  	@Modifying
  	@Query(value = "delete  from follow where followed_id = ?1 AND follower_id = ?2", nativeQuery = true)
  	void removeFollower( long followed, long follower);
    
   
  
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


	  Optional<ArrayList<User>> findAllByFirstNameContaining(String name);

}
