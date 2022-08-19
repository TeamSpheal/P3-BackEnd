package com.revature.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.dtos.LikeRequest;
import com.revature.dtos.PostDTO;
import com.revature.dtos.UserMiniDTO;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.PostService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/post")
public class PostController {

	private final PostService postService;
	private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }
    
    
    /** 
     * @return ResponseEntity<List<PostDTO>>
     */
    @Authorized
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
    	List<PostDTO> listDto = new ArrayList<>();
    	for(Post post : this.postService.getAll()) {
    		listDto.add(new PostDTO(post));
    	}
    	return ResponseEntity.ok(listDto);
    }
    
    
    /** 
     * @param post
     * @return ResponseEntity<Post>
     */
    @Authorized
    @PutMapping
    public ResponseEntity<Post> upsertPost(@RequestBody PostDTO post) {
        post.setAuthor(new UserMiniDTO(1l, "username", "profileURL"));
        Post newPost = new Post(post);
    	return ResponseEntity.ok(this.postService.upsert(newPost));
    }
    
    
    /** 
     * @param like
     * @return ResponseEntity<PostDTO>
     */
    @PutMapping("/like")
    public ResponseEntity<PostDTO> likePost(@RequestBody LikeRequest like) {
    	User user = userService.getUser(like.getUserId());
    	Post post = postService.getPost(like.getPostId());
    	if (user == null || post == null) {
    		return ResponseEntity.badRequest().build();
    	}
        Set<User> users = post.getUsers();
        users.add(user);
        post.setUsers(users);
        PostDTO postDto = new PostDTO(postService.upsert(post));
        return ResponseEntity.ok(postDto);
    }
    
    
    /** 
     * @param unlike
     * @return ResponseEntity<PostDTO>
     */
    @PutMapping("/unlike")
    public ResponseEntity<PostDTO> unlikePost(@RequestBody LikeRequest unlike) {
    	User user = userService.getUser(unlike.getUserId());
    	Post post = postService.getPost(unlike.getPostId());
    	if (user == null || post == null) {
    		return ResponseEntity.badRequest().build();
    	}
        Set<User> users = post.getUsers();
        users.remove(user);
        post.setUsers(users);
        PostDTO postDto = new PostDTO(postService.upsert(post));
        return ResponseEntity.ok(postDto);
    }
    
    @Authorized
    @GetMapping("/get/{id}")
    public ResponseEntity<Set<PostDTO>> getAllPostsById(@PathVariable long id){
    	User user  =  userService.getUser(id);
    	Set<Post> list = postService.getPostByAuthor(user);
    	Set<PostDTO> hashset = new HashSet<>();
    	for(Post p : list) {
    		hashset.add(new PostDTO(p));
    	}
    	return ResponseEntity.ok(hashset);
    }
}
