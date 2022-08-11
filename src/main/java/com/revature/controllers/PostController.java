package com.revature.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.PostRepository;
import com.revature.services.PostService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PostController {

	private final PostService postService;
	private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }
    
    @Authorized
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(this.postService.getAll());
    }
    
    @Authorized
    @PutMapping
    public ResponseEntity<Post> upsertPost(@RequestBody Post post) {
    	return ResponseEntity.ok(this.postService.upsert(post));
    }
    
    @PutMapping("/like/{postId}/{userId}")
    public ResponseEntity<Post> likePost(@PathVariable long postId, @PathVariable("userId") long userId) {
    	User user = userService.getUser(userId);
    	Post post = postService.getPost(postId);
    	if (user == null || post == null) {
    		return ResponseEntity.badRequest().build();
    	}
        Set<User> users = post.getUsers();
        users.add(user);
        post.setUsers(users);
        return ResponseEntity.ok(post);
    }
    
    @PutMapping("/unlike/{postId}/{userId}")
    public ResponseEntity<Post> unlikePost(@PathVariable("postId") long postId, @PathVariable("userId") long userId) {
    	User user = userService.getUser(userId);
    	Post post = postService.getPost(postId);
    	if (user == null || post == null) {
    		return ResponseEntity.badRequest().build();
    	}
        Set<User> users = post.getUsers();
        users.remove(user);
        post.setUsers(users);
        return ResponseEntity.ok(post);
    }
}
