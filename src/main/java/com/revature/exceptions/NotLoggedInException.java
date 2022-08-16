package com.revature.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
/*
 * Exception to be thrown by the AuthAspect
 * Will be handled by a Spring Exception Handler to return a 401
 */
@ResponseStatus (value = HttpStatus.UNAUTHORIZED, reason = "Not logged in!")
public class NotLoggedInException extends RuntimeException {

    public NotLoggedInException() {
    }

    public NotLoggedInException(String message) {
        super(message);
    }

    public NotLoggedInException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotLoggedInException(Throwable cause) {
        super(cause);
    }

    public NotLoggedInException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
