package com.revature.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.UserDTO;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.FailedAuthenticationException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    
    /** 
     * @param loginRequest
     * @param session
     * @return ResponseEntity<UserDTO>
     * @throws FailedAuthenticationException
     */
    @PostMapping(path="/login", produces="application/json")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest, HttpSession session) throws FailedAuthenticationException {
        Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        if(optional.isEmpty()) {
            throw new FailedAuthenticationException("Credentials for the email " + loginRequest.getEmail() + " were invalid, please try again!");
        }
        
        session.setAttribute("user", optional.get());
        UserDTO user = new UserDTO(optional.get());

        // Create a JWT and attach it to the header "Auth" in the response.
        String jws = tokenService.createToken(user);
        return ResponseEntity.status(200).header("Auth", jws).body(user);
    }

    
    /** 
     * @param session
     * @return ResponseEntity<Void>
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }

    
    /** 
     * @param registerRequest
     * @return ResponseEntity<UserDTO>
     * @throws EmailAlreadyExistsException
     * @throws UsernameAlreadyExistsException
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest registerRequest) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        User created = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getProfileImg()
            );
        try {
            User user = authService.register(created);
            UserDTO dto = new UserDTO(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (EmailAlreadyExistsException e) {
            logger.error("ERROR: EmailAlreadyExistsException", e);
            throw e;
        } catch (UsernameAlreadyExistsException e) {
            logger.error("ERROR: UsernameAlreadyExistsException", e);
            throw e;
        }
    }
    
}
