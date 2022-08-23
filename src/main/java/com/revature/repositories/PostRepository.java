package com.revature.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.Post;
import com.revature.models.User;

public interface PostRepository extends JpaRepository<Post, Long>{
	public Set<Post> findByUsers(User user);



	@Query(value = "SELECT * FROM POSTS WHERE ID NOT IN (SELECT COMMENTS_ID FROM POSTS_COMMENTS) order by id", nativeQuery = true)
	public List<Post> findNonCommentPosts();
}
