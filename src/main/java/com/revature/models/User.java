package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private int id;
    @NonNull private  String email;
    @NonNull private  String password;
    @NonNull private  String firstName;
    @NonNull private  String lastName;
    @ManyToMany
  	@JoinTable(
  	  name = "user_follower", 
  	  joinColumns = @JoinColumn(name = "fallower",referencedColumnName = "id"), 
  	  inverseJoinColumns = @JoinColumn(name = "id",referencedColumnName = "id"))
  	private Set<User> following;
}
