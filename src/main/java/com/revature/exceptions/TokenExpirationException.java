package com.revature.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Exception for when a java web token expires. Should return a
 * 401 UNAUTHORIZED when thrown in a controller.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "The token is expired!")
public class TokenExpirationException extends Exception {

}
