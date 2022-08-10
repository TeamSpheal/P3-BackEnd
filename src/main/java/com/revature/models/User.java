package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private @NonNull int id;
    @NonNull private  String email;
    @NonNull private  String password;
    @NonNull private  String firstName;
    @NonNull private  String lastName;
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
