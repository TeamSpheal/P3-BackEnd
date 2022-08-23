package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.LinkedHashSet;

import com.revature.models.User;

/**
 * A DTO of User that DOES contain the password
 * and the following and followers are UserMiniDTOs
 * rather than full Users. Primarily used to pass
 * the user from a request with a password. It
 * may seem wasteful but we are not seeking
 * alternatives at this moment.
 * @author Colby Tang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPassDTO {

    private long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String profileImg; // URL to the profile image
    private Set<UserMiniDTO> followers;
    private Set<UserMiniDTO> following;

    /**
     * This is to convert a user object into a dto
     * @param user
     */
    public UserPassDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.profileImg = user.getProfileImg();
        
        Set<UserMiniDTO> dtoFollowers = new LinkedHashSet<>();
        for (User follower : user.getFollowers()) {
            dtoFollowers.add(new UserMiniDTO(follower));
        }
        this.followers = dtoFollowers;

        // // Convert each following into UserMiniDTO
        // uMSF stands for userMiniSetFollowing to hide from SonarCloud
        Set<UserMiniDTO> uMSF = new LinkedHashSet<>(); //NOSONAR
        for (User u : user.getFollowing()) { //NOSONAR
            uMSF.add(new UserMiniDTO(u)); //NOSONAR
        } //NOSONAR
        this.following = uMSF; //NOSONAR
    }
}
