package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Post getPost(long id) {
		Optional<Post> postOpt = postRepository.findById(id);
		if (postOpt.isPresent()) {
			return postOpt.get();
		}
		return null;
	}

	public List<Post> getAll() {
		return this.postRepository.findNonCommentPosts();
	}

	public Post upsert(Post post) {
		return this.postRepository.save(post);
	}
	public List<Post> getFollowingPosts(User user) {
		Set<User> following = user.getFollowing();
		List<Post> posts = new ArrayList<Post>();
		for (User u : following) {
			posts.addAll(this.postRepository.findAllByAuthorId(u.getId()));
		}
		return posts;
	}
}
