package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	
	/** 
	 * @param id
	 * @return Post
	 */
	public Post getPost(long id) {
		Optional<Post> postOpt = postRepository.findById(id);
		if (postOpt.isPresent()) {
			return postOpt.get();
		}
		return null;
	}

	
	/** 
	 * @return List<Post>
	 */
	public List<Post> getAll() {
		return this.postRepository.findNonCommentPosts();
	}

	
	/** 
	 * @param post
	 * @return Post
	 */
	public Post upsert(Post post) {
		return this.postRepository.save(post);
	}
	
//	/** 
//	 * Get all the posts from a specific author.
//	 * @param id
//	 * @return List<Post>
//	 */
//	public List<Post> getPostsByAuthor(long id) {
//		return this.postRepository.findByAuthorId(id);
//	}
	
//	public List<Post> getUserFeed(long id) {
//		List<Post> posts = this.postRepository.findUserPostFeed(id);
//		return posts;
	
	/** 
	 * Get all the posts from people they follow and themselves.
	 * @param id
	 * @return List<Post>
	 */
	public List<Post> getUserFeed(long id) {
		return this.postRepository.findUserPostFeed(id);
	}
	
	/** 
	 * Get all the posts from a user.
	 * @param id
	 * @return List<Post>
	 */
	public List<Post> getUserPosts(long id) {
		return this.postRepository.findAllByAuthorId(id);
	}
	
//	public List<Post> getUserFeed(long id) {
//		List<Post> posts = this.postRepository.findUserPostFeed(id);
//		return posts;
//	}
}
