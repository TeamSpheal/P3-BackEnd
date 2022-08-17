package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.LinkedHashSet;

import javax.persistence.*;

import com.revature.models.User;


/**
 * A DTO of User that does not have the password
 * and the following and followers are UserMiniDTOs
 * rather than full Users. Primarily used to pass
 * the user from a request and in a response.
 * @author Colby Tang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String profileImg; // URL to the profile image
    private Set<UserMiniDTO> followers;
    private Set<UserMiniDTO> following;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.profileImg = user.getProfileImg();
        
    //     Set<UserMiniDTO> dtoFollowers = new LinkedHashSet<>();
    //     //for (User follower : user.getFollowers()) {
    //         dtoFollowers.add(new UserMiniDTO(follower));
    //     }
    //     this.followers = dtoFollowers;

    //     Set<UserMiniDTO> dtoFollowing = new LinkedHashSet<>();
    //     for (User followering : user.getFollowing()) {
    //         dtoFollowers.add(new UserMiniDTO(followering));
    //     }
    //     this.following = dtoFollowing;
     }
}
