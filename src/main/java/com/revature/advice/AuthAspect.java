package com.revature.advice;

import com.revature.annotations.AuthRestriction;
import com.revature.annotations.Authorized;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.FailedAuthenticationException;
import com.revature.exceptions.InvalidRoleException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.TokenExpirationException;
import com.revature.models.User;
import com.revature.services.TokenService;
import com.revature.services.TokenServiceImpl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class AuthAspect {
    // This advice will execute around any method annotated with @Authorized
    // If the user is not logged in, an exception will be thrown and handled
    // Otherwise, the original method will be invoked as normal.
    // In order to expand upon this, you just need to add additional
    // values to the AuthRestriction enum.
    // Examples might be "Manager" or "Customer" along with suitable Role
    // values in the User class.
    // Then this method can be expanded to throw exceptions if the user does
    // not have the matching role.
    // Example:
    // User loggedInUser = (User) session.getAttribute("user");
    // Role userRole = loggedInUser.getRole();
    // if(authorized.value().equals(AuthRestriction.Manager) && !Role.Manager.equals(userRole)) {
    //     throw new InvalidRoleException("Must be logged in as a Manager to perform this action");
    // }
    // Then the RestExceptionHandler class can be expanded to include
    // @ExceptionHandler(InvalidRoleException.class)
    // which should return a 403 Forbidden such as:
    // String errorMessage = "Missing required role to perform this action";
    // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);

    // @Around("@annotation(authorized)")
    // public Object authenticate(ProceedingJoinPoint pjp, Authorized authorized) throws Throwable {

    //     HttpSession session = req.getSession(); // Get the session (or create one)

    //     // If the user is not logged in
    //     if(session.getAttribute("user") == null) {
    //         throw new NotLoggedInException("Must be logged in to perform this action");
    //     }

    //     return pjp.proceed(pjp.getArgs()); // Call the originally intended method
    // }

	private TokenService tokenService;

    // Spring will autowire a proxy object for the request
    // It isn't a request object itself, but if there is an active request
    // the proxy will pass method calls to the real request
    private final HttpServletRequest currentReq;

	public AuthAspect(TokenService tokenService, HttpServletRequest req) {
		this.tokenService = tokenService;
		this.currentReq = req;
	}
	
	@Around("methodsWithAuthAnnotation()")
	public Object authenticate(ProceedingJoinPoint joinpoint) throws Throwable {
		Authorized authAnnotation = ((MethodSignature) joinpoint.getSignature())
				.getMethod()
				.getAnnotation(Authorized.class);
		final AuthRestriction authRestriction = authAnnotation.value();
		final boolean requireSelfAction = authAnnotation.requireSelfAction();
		
		final String jws = currentReq.getHeader("Auth");
		final String currentUser = currentReq.getHeader("Username");

		// If user has an empty token header
		if (jws == null || jws.equals("")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Empty token.");
		}

		Optional<UserDTO> userDtoOpt = Optional.empty();
		try {
			userDtoOpt = tokenService.validateToken(jws);
		} catch (FailedAuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
		} catch (TokenExpirationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Expired token.");
		}
		
		if (!userDtoOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user info present.");
		}

		final String username = userDtoOpt.get().getUsername();

		// Username has to be the same as the token
		// In other words, the user has to be doing the request to themselves.
		// This is to prevent users from modifying other users.
		if (requireSelfAction) {
			if (currentUser != null) {
				final boolean doesUserMatchToken = currentUser.equals(username);
				if (!doesUserMatchToken) {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not match the token."); 
				}
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no username in the header!"); 
			}
		}
		
		return joinpoint.proceed();
	}
	
	@Pointcut("@annotation(com.revature.annotations.Authorized)")
	public void methodsWithAuthAnnotation() 
	{ // I don't know why this has to be empty but it do
	}
}
