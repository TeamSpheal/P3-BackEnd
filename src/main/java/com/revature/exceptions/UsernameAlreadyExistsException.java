package com.revature.exceptions;

/**
 * Exception that throws when the same username already exists in the database!
 * @author Colby Tang
 */
public class UsernameAlreadyExistsException extends Exception {

    public UsernameAlreadyExistsException () {
        super ("Username already exists in the database!");
    }

    public UsernameAlreadyExistsException (String username) {
        super ("Username [" + username + "] already exists in the database! ");
    }
}
