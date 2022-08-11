package com.revature.exceptions;

/**
 * Exception for when email is already in the database.
 * @author Colby Tang
 */
public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException () {
        super ("Email already exists in the database!");
    }

    public EmailAlreadyExistsException (String email) {
        super ("Email [" + email + "] already exists in the database! ");
    }
}
