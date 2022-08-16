package com.revature.dtos;

import java.util.HashSet;
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
	private Set<PostDTO> comments;
	private UserMiniDTO author;
	private Set<UserMiniDTO> users;
	
	/**
	 * Convert Post into a DTO
	 * @param post
	 */
	public PostDTO(Post post) {
		setId(post.getId());
		setText(post.getText());
		setImageUrl(post.getImageUrl());
		setComments(new HashSet<>());
		for (Post comment : post.getComments()) {
			this.comments.add(new PostDTO(comment));
		}
		setAuthor(new UserMiniDTO(post.getAuthor()));
		setUsers(new HashSet<>());
		for (User user : post.getUsers()) {
			this.users.add(new UserMiniDTO(user));
		}
	}
}