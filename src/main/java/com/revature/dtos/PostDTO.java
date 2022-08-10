package com.revature.dtos;

import java.util.List;
import java.util.Set;

import com.revature.models.Post;
import com.revature.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
	
	private long id;
	private String text;
	private String imageUrl;
	private List<Post> comments;
	private User author;
	private Set<User> users;
	
	public PostDTO(Post post) {
		setId(post.getId());
		setText(post.getText());
		setImageUrl(post.getImageUrl());
		setComments(post.getComments());
		setAuthor(post.getAuthor());
		setUsers(post.getUsers());
	}
}