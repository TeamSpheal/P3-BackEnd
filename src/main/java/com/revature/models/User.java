package com.revature.models;

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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private int id;
    @NonNull private  String email;
    @NonNull private  String password;
    @NonNull private  String firstName;
    @NonNull private  String lastName;

    public User(int id, @NonNull String email, @NonNull String password, @NonNull String firstName,
			@NonNull String lastName) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	// The user followers
    @ManyToMany
  	@JoinTable(
  	  name = "user_follower", 
  	  joinColumns = @JoinColumn(name = "fallower",referencedColumnName = "id"), 
  	  inverseJoinColumns = @JoinColumn(name = "id",referencedColumnName = "id"))
  	private Set<User> followers;
  	  
    // The user following others
    @ManyToMany(mappedBy = "followers")
    private Set<User> following;
    
    
    
}
