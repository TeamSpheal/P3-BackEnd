package com.revature.models;
 

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

 

 


 
@Entity
@Table(name="Follower")
public class Follower {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Follower_ID")

	//private Long follower;
	private Long follower_id;

    @OneToMany 
    @JoinColumn (name ="FollowedID")

    private Long followed_idUser;

   // followed_idUser references user(user_id)in user table
 
	 
	 

	public Follower(Long id, Long UserId) {
		this.follower_id = id;
		this.followed_idUser = UserId;
		 
    }
     public Long getFollowerId() {
		return follower_id;
	}

	public void setFollowerId(Long follower_id) {
		this.follower_id = follower_id;
    }
    public Long getFollowedId() {
		return followed_idUser;
	}

	public void setFollowedId(long followed_id) {
		this.followed_idUser = followed_id;
	}
}


