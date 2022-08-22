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
	
	@Query(value = "select * from posts where author_id in (select target_id from follow where user_id=?1 union select 1)order by created_date", nativeQuery=true)
	List<Post> findUserPostFeed(long user);
	
	List<Post> findByAuthorId(long id);
}
