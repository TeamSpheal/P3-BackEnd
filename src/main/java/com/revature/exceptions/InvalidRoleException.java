package com.revature.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
/**
 * An exception for the wrong role like if a user tried to do
 * an admin function as a regular user. Should return an
 * unauthorized error response.
 * @author Colby Tang
 */
@ResponseStatus (value = HttpStatus.UNAUTHORIZED, reason = "Invalid or not the required role!")
public class InvalidRoleException extends Exception {
    public InvalidRoleException (String message) {
        super (message);
    }
}
