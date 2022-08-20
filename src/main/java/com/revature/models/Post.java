package com.revature.models;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.revature.dtos.PostDTO;
import com.revature.dtos.UserMiniDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String text;
	private String imageUrl;
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Post> comments;
	@ManyToOne
	private User author;
	@ManyToMany
	@JoinTable(name = "liked_posts", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users;
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Timestamp createdDate = new Timestamp(System.currentTimeMillis());

	public Post(PostDTO dto) {
		this.id = dto.getId();
		this.text = dto.getText();
		this.imageUrl = dto.getImageUrl();
		this.comments = new HashSet<>();
		for(PostDTO postDto: dto.getComments()) {
			this.comments.add(new Post(postDto));	
		}
		this.author = new User(dto.getAuthor());
		this.users = new HashSet<>();
		for (UserMiniDTO miniUser : dto.getUsers()) {
			this.users.add(new User(miniUser));
		}
		this.createdDate = dto.getCreatedDate();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(author, other.author) && Objects.equals(comments, other.comments) && id == other.id
				&& Objects.equals(imageUrl, other.imageUrl) && Objects.equals(text, other.text)
				&& Objects.equals(users, other.users);
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, comments, id, imageUrl, text, users);
	}
}
