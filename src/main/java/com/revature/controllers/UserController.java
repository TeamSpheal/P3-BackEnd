package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.annotations.Authorized;
import com.revature.dtos.UserDTO;
import com.revature.dtos.UserMiniDTO;
import com.revature.dtos.UserPassDTO;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.RecordNotFoundException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.ImageService;
import com.revature.services.ResetPWService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ImageService imageService;
    private final ResetPWService resetPWService;
    private ObjectMapper objMapper = new ObjectMapper();

    @Autowired
    Environment env;

    public UserController(UserService userService, ResetPWService resetPWService, ImageService imageService) {
        this.userService = userService;
        this.resetPWService = resetPWService;
        this.imageService = imageService;
    }

    /**
     * Get the user by id. Returns a 404 if the user cannot be found.
     * 
     * @param id
     * @return
     */
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
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

    // Add follower to the logged in user
    @PostMapping("/{userId}/follower/{targetId}")
    public ResponseEntity<Void> addFollower(@PathVariable("userId") Long userId,
            @PathVariable("targetId") Long targetId) {
        // check if id's are the same
        if (!userId.equals(targetId)) {
            try {
                userService.addFollower(userId, targetId);
                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (RecordNotFoundException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
    }

    /**
     * Gets a list of users from the list of user ids. Returns an empty list if none
     * of the
     * users can be found.
     * 
     * @param id
     * @return
     */
    @Authorized
    @GetMapping("/mini")
    public ResponseEntity<List<UserMiniDTO>> getUsersMiniList(@RequestBody List<Long> userIds) {
        // Finding a user by id from the repository returns an optional user.
        List<User> usersList = userService.findAllUsersFromList(userIds);

        // Convert user list into user mini dto list
        List<UserMiniDTO> usersDTOList = new ArrayList<>();
        usersList.stream().forEach(n -> usersDTOList.add(new UserMiniDTO(n)));

        // Send a 200 with the users.
        return ResponseEntity.ok(usersDTOList);
    }

    /**
     * Update a user's information based on a given user object
     * 
     * @param updatedUser
     * @return a UserMiniDTO object
     * @throws EmailAlreadyExistsException
     * @throws UsernameAlreadyExistsException
     */
    @Authorized
    @PostMapping("/update/profile")
    public ResponseEntity<UserMiniDTO> updateUser(@RequestBody UserDTO updatedUser)
            throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        // Pass object to service layer
        User result = userService.update(updatedUser);

        // Assuming an exception is not thrown, remove unnecessary data and return it
        // with a status of 200
        UserMiniDTO bodyDTO = new UserMiniDTO(result);
        return ResponseEntity.ok(bodyDTO);
    }

    /**
     * Update a user's password based on a given user object
     * 
     * @param updatedUser
     * @return a UserMiniDTO object
     * @throws EmailAlreadyExistsException
     * @throws UsernameAlreadyExistsException
     */
    @PostMapping("/update/password")
    public ResponseEntity<UserMiniDTO> updatePW(@RequestBody UserPassDTO updatedUser)
            throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
                
        // Convert UserPassDTO into a real User
        User inputUser = new User(updatedUser);
        // Pass object to service layer
        User result = userService.save(inputUser);

        // Assuming an exception is not thrown, remove unnecessary data and return it
        // with a status of 200
        UserMiniDTO bodyDTO = new UserMiniDTO(result);
        return ResponseEntity.ok(bodyDTO);
    }

    /**
     * Provide the user with a token to reset their password if the email provided
     * exists
     * 
     * @param email
     * @return
     * @throws JsonProcessingException 
     */
    @PostMapping("/resetPW")
    public ResponseEntity<String> getResetPWToken(@RequestBody String email) throws JsonProcessingException {
        String resetToken = null;
        UserDTO resetUser = null;
        if (userService.doesEmailAlreadyExist(email)) {
            resetToken = resetPWService.generateResetToken(email);
            resetUser = new UserDTO(userService.findByEmail(email));
            return ResponseEntity.status(200).header("ResetToken", resetToken).body(objMapper.writeValueAsString(resetUser));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email provided does not have an account");
        }
    }

    /**
     * Takes in a multipart file (usually an image)
     * and uploads using an image service. It will
     * upload locally for the dev profile and
     * upload to S3 for the test profile.
     * @param multipartFile
     * @return
     * @throws IOException
     * @author Colby Tang
     */
    @PostMapping(path="/image-upload", consumes="multipart/form-data", produces="application/json")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        try {
            String url = imageService.uploadMultipartFile(multipartFile);

            // Workaround for front end since it tries to parse response as a JSON
            Map<String, String> urlMap = new HashMap<>();
            urlMap.put("url", url);
            
            return ResponseEntity.ok(urlMap);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
