package com.revature.services;

import java.util.Optional;

import com.revature.exceptions.FailedAuthenticationException;
import com.revature.exceptions.TokenExpirationException;
import com.revature.dtos.UserDTO;

public interface TokenService {
    public String createToken(UserDTO user);
	public Optional<UserDTO> validateToken(String token) throws FailedAuthenticationException, TokenExpirationException;
	public int getDefaultExpiration();
}
