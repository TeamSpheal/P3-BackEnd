package com.revature.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.auth.JwtConfig;
import com.revature.exceptions.FailedAuthenticationException;
import com.revature.exceptions.TokenExpirationException;
import com.revature.dtos.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class TokenServiceImpl implements TokenService {
    private JwtConfig jwtConfig;

    public TokenServiceImpl (JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * 
     */
    @Override
    public String createToken(UserDTO user) {
        String jws = "";

        if (user != null && user.getUsername() != null) {
            long now = System.currentTimeMillis();

            jws = Jwts.builder()
                .setId(String.valueOf(user.getId()))
                .setSubject(user.getUsername())
                .setIssuer(jwtConfig.getIssuerName())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration()))
                .signWith(jwtConfig.getSigningKey())
                .compact();
        }

        return jws;
    }

    @Override
    public int getDefaultExpiration() {
        return jwtConfig.getExpiration();
    }

    @Override
    public Optional<UserDTO> validateToken(String token)
            throws FailedAuthenticationException, TokenExpirationException {
        try {
            Claims jwtClaims = Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
            long now = System.currentTimeMillis();
            if (jwtClaims.getExpiration().before(new Date(now))) {
                throw new TokenExpirationException();
            }

            UserDTO userDTO = parseUser(jwtClaims);

            return Optional.of(userDTO);
        } catch (JwtException e) {
            throw new FailedAuthenticationException();
        }
    }

    private UserDTO parseUser(Claims claims) {
		Long id = Long.parseLong(claims.getId());
		String username = claims.getSubject();
		
		return new UserDTO(id, username);
	}
}
