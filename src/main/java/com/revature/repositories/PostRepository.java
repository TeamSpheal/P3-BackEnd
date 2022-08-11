package com.revature.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Post;
import com.revature.models.User;

public interface PostRepository extends JpaRepository<Post, Long>{
	public Set<Post> findByUsers(User user);
}
