package com.revature.models;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.revature.dtos.UserMiniDTO;
import com.revature.dtos.UserPassDTO;

@Entity
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

	@ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
	private Set<User> followers;

    @JoinTable(name = "follow",
            joinColumns = {
				@JoinColumn(name = "target_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {
				@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)})
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<User> following;

	public User() {
		this.id = 0l;
		this.username = "";
		this.email = "";
		this.password = "";
		this.profileImg = "";
		this.firstName = "";
		this.lastName = "";

		this.followers = new LinkedHashSet<>();
		this.following = new LinkedHashSet<>();
	}

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

	public User(UserMiniDTO dto) {
		this.id = dto.getId();
		this.username = dto.getUsername();
		this.profileImg = dto.getProfileImg();
	}

	/**
	 * Convert a UserPassDTO into a User
	 * @author Colby Tang
	 * @param dto
	 */
	public User(UserPassDTO dto) {
		this.id = dto.getId();
		this.username = dto.getUsername();
		this.email = dto.getEmail();
		this.password = dto.getPassword();
		this.profileImg = dto.getProfileImg();
		this.firstName = dto.getFirstName();
		this.lastName = dto.getLastName();

		this.followers = new LinkedHashSet<>();
		for (UserMiniDTO user : dto.getFollowers()) {
			followers.add(new User(user));
		}

		this.following = new LinkedHashSet<>();
		for (UserMiniDTO user : dto.getFollowing()) {
			following.add(new User(user));
		}
	}

	public void followUser (User user) {
		this.following.add(user);
		user.getFollowers().add(this);
	}

	public void unFollowUser (User user) {
		if (following.contains(user)) {
			this.following.remove(user);
		}
		if (user.getFollowers().contains(this)) {
			user.getFollowers().remove(this);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}
}
