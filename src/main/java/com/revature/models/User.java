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

import com.revature.dtos.UserMiniDTO;

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
	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String profileImg;
	@ManyToMany
	@JoinTable(name = "follow", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "followed_id"))
	private Set<User> followers;
	@ManyToMany
	@JoinTable(name = "follow", joinColumns = @JoinColumn(name = "followed_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
	private Set<User> following;

	public User(String username, String email, String password, String firstName, String lastName, String profileImg) {
		this.id = 0;
		this.username = username;
		this.email = email;
		this.password = password;
		this.profileImg = profileImg;
		this.firstName = firstName;
		this.lastName = lastName;
		this.followers = new LinkedHashSet<>();
		this.following = new LinkedHashSet<>();
	}

	public User(UserMiniDTO author) {
		this.id = author.getId();
		this.username = author.getUsername();
		this.profileImg = author.getProfileImg();
	}
}
