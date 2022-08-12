package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.dtos.UserDTO;
import com.revature.dtos.UserMiniDTO;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {
    private final UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    /**
     * Get the user by id. Returns a 404 if the user cannot be found.
     * @param id
     * @return
     */
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser (@PathVariable long id) {
        // Finding a user by id from the repository returns an optional user.
        Optional<User> oUser = userService.findById(id);

        // If user is not null, then send a 200 with the user object.
        if (oUser.isPresent()) {
            UserDTO user = new UserDTO(oUser.get());
            return ResponseEntity.ok(user);
        }

        // Otherwise, send 404
        return ResponseEntity.notFound().build();
    }

    /**
     * Gets a list of users from the list of user ids. Returns an empty list if none of the
     * users can be found.
     * @param id
     * @return
     */
    @Authorized
    @GetMapping("/mini")
    public ResponseEntity<List<UserMiniDTO>> getUsersMiniList (@RequestBody List<Long> userIds) {
        // Finding a user by id from the repository returns an optional user.
        List<User> usersList = userService.findAllUsersFromList(userIds);

        // Convert user list into user mini dto list
        List<UserMiniDTO> usersDTOList = new ArrayList<>();
        usersList.stream().forEach(n -> usersDTOList.add(new UserMiniDTO(n)));

        // Send a 200 with the users.
        return ResponseEntity.ok(usersDTOList);
    }
    
    @Authorized
    @PostMapping("/")
    public ResponseEntity<UserDTO> updateUser (@RequestBody User updatedUser) throws EmailAlreadyExistsException, UsernameAlreadyExistsException{
    	User result = userService.save(updatedUser);
    	UserDTO bodyDTO = new UserDTO(result);
    	return ResponseEntity.ok(bodyDTO);
    }
}
