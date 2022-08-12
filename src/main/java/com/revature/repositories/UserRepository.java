package com.revature.repositories;

<<<<<<< HEAD
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
=======
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
>>>>>>> 3a7000e7357c7afd4693fdb48ded2892c8ac6de9

import com.revature.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Returns a user object based on a given email and password
	 * @param email (String)
	 * @param password (String)
	 * @return a user object
	 */
    Optional<User> findByEmailAndPassword(String email, String password);
<<<<<<< HEAD

    Optional<ArrayList<User>> findAllByFirstNameContaining(String name);
=======
    
    /**
	 * Returns a user object based on a given email
	 * @param email (String)
	 * @return a user object
	 */
    Optional<User> findByEmail(String email);
    
    /**
	 * Returns a user object based on a given username
	 * @param username (String)
	 * @return a user object
	 */
    Optional<User> findByUsername(String username);
>>>>>>> 3a7000e7357c7afd4693fdb48ded2892c8ac6de9
}
