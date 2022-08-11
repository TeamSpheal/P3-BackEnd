package com.revature.dtos;

import java.util.LinkedHashSet;
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
		setComments(post.getComments());
		UserMiniDTO authorDto = new UserMiniDTO(post.getAuthor());
		setAuthor(authorDto);
	    Set<UserMiniDTO> dtoLikes = new LinkedHashSet<>();
	    for(User like : post.getUsers() ) {
	    	dtoLikes.add(new UserMiniDTO(like));
	    }
	    setUsers(dtoLikes);
	}
}