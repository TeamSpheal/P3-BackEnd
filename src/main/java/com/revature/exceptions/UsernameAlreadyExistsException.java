package com.revature.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that throws when the same username already exists in the database!
 * HttpStatus.CONFLICT (409)
 * @author Colby Tang
 */

@ResponseStatus(value = HttpStatus.CONFLICT, reason="The username you entered already exists")
public class UsernameAlreadyExistsException extends Exception {

    public UsernameAlreadyExistsException () {
        super ("Username already exists in the database!");
    }

    public UsernameAlreadyExistsException (String username) {
        super ("Username [" + username + "] already exists in the database! ");
    }

    public UsernameAlreadyExistsException(String string, UsernameAlreadyExistsException e) {
        super (string, e);
    }
}
