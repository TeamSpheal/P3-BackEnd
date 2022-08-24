package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.LinkedHashSet;

import com.revature.models.User;

/**
 * A DTO of User that does not have the password
 * and the following and followers are UserMiniDTOs
 * rather than full Users. Primarily used to pass
 * the user from a request and in a response.
 * 
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

    /**
     * This is for validating a JWT in TokenServiceImpl
     * 
     * @author Colby Tang
     */
    public UserDTO(long id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * This is to convert a user object into a dto
     * 
     * @param user
     */
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.profileImg = user.getProfileImg();

        // Convert each follower into UserMiniDTO
        Set<UserMiniDTO> dtoFollowers = new LinkedHashSet<>();
        for (User follower : user.getFollowers()) {
            dtoFollowers.add(new UserMiniDTO(follower));
        }
        this.followers = dtoFollowers;

        // // Convert each following into UserMiniDTO
        Set<UserMiniDTO> userMiniSetFollowing = new LinkedHashSet<>(); //NOSONAR
        for (User u : user.getFollowing()) { //NOSONAR
            userMiniSetFollowing.add(new UserMiniDTO(u)); //NOSONAR
        } //NOSONAR
        this.following = userMiniSetFollowing; //NOSONAR
    }
}
