package com.revature.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.Post;
import com.revature.models.User;

public interface PostRepository extends JpaRepository<Post, Long>{
	public Set<Post> findByAuthor(User user);
	
	@Query(value = "SELECT * FROM POSTS WHERE ID NOT IN (SELECT COMMENTS_ID FROM POSTS_COMMENTS) order by id", nativeQuery = true)
	public List<Post> findNonCommentPosts();
	
	List<Post> findAllByAuthorId(long user);
	
	@Query(value = "select * from posts\r\n"
			+ "where author_id in\r\n"
			+ "( select target_id from follow where user_id=1\r\n"
			+ "  union\r\n"
			+ "  select 1\r\n"
			+ ")\r\n"
			+ "order by created_date desc;", nativeQuery=true)
	List<Post> findUserPostFeed(long user);
}
