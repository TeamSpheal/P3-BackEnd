package com.revature.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Exception for when email is already in the database.
 * HttpStatus.CONFLICT (409)
 * @author Colby Tang
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason="The email you entered already exists")
public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException () {
        super ("Email already exists in the database!");
    }

    public EmailAlreadyExistsException (String email) {
        super ("Email [" + email + "] already exists in the database! ");
    }

    public EmailAlreadyExistsException(String string, EmailAlreadyExistsException e) {
        super (string, e);
    }
}
