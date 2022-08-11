package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.revature.models.User;


/**
 * UserMiniDTO only contains the id, username, profileImg.
 * It is primarily used for followers and following lists
 * since they do not need anything more.
 * @author Colby Tang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMiniDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String profileImg;

    public UserMiniDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.profileImg = user.getProfileImg();
    }

    public UserMiniDTO(UserDTO user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.profileImg = user.getProfileImg();
    }
}
