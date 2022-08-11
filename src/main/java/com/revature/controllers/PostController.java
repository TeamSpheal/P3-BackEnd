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
import com.revature.dtos.PostDTO;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.PostService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PostController {

	private final PostService postService;
	private final UserRepository userRepo;

    public PostController(PostService postService, UserRepository userRepo) {
        this.postService = postService;
        this.userRepo = userRepo;
    }
    
    @Authorized
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(this.postService.getAll());
    }
    
    @Authorized
    @PutMapping
    public ResponseEntity<PostDTO> upsertPost(@RequestBody PostDTO postDto) {
        Post post = new Post(postDto);
        PostDTO dto = new PostDTO(this.postService.upsert(post));
    	return ResponseEntity.ok(dto);
    }
    
    @PutMapping("/like/{userId}")
    public ResponseEntity<Post> likePost(@RequestBody PostDTO postDto, @PathVariable("userId") long userId) {
    	Optional<User> user = userRepo.findById(userId);
    	if(user.isPresent()) {
            Post post = new Post(postDto);
        	Set<User> users = post.getUsers();
        	users.add(user.get());
        	post.setUsers(users);
        	return ResponseEntity.ok(post);
    	}
		return ResponseEntity.badRequest().build();
    }
}
