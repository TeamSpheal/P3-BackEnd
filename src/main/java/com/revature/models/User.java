package com.revature.models;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
	@ManyToMany
	@JoinTable(name="liked_posts",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="post_id"))
	private Set<Post> likes;
	@ManyToMany
	@JoinTable(name="follow",
		joinColumns=@JoinColumn(name="follower_id"),
		inverseJoinColumns=@JoinColumn(name="followed_id"))
	private Set<User> followers;
	
	@ManyToMany(mappedBy = "followers")
	private Set<User> following;
	
	public User(String email, String password, String firstName, String lastName) {
		this.id = 0;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.likes = new LinkedHashSet<Post>();
		this.followers = new LinkedHashSet<User>();
		this.following = new LinkedHashSet<User>();
	}

}
