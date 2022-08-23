package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.auth.JwtConfig;
import com.revature.dtos.UserDTO;
import com.revature.models.User;
import com.revature.exceptions.FailedAuthenticationException;

import io.jsonwebtoken.Jwts;

/**
 * @author Colby Tang
 */
@SpringBootTest
class TokenServiceTest {
    @Autowired
	private JwtConfig jwtConfig;
	@Autowired
	private TokenService tokenService;
	
    /**
     * Creates a successful token.
     */
	@Test
	void createTokenSuccess() {
		UserDTO user = new UserDTO();
        user.setId(1l);
        user.setUsername("username");
		String jws = tokenService.createToken(user);
		System.out.println(jws);
		assertDoesNotThrow(() -> 
			Jwts.parserBuilder().setSigningKey(jwtConfig.getSigningKey()).build().parseClaimsJws(jws)
		);
	}
	
    /**
     * Tries to create a token from a null user.
     */
	@Test
	void createTokenNullUser() {
		assertEquals("", tokenService.createToken(null));
	}
	
    /**
     * Tries to create a token from invalid user configuration.
     */
	@Test
	void createTokenInvalidUser() {
		assertEquals("", tokenService.createToken(new UserDTO()));
	}
	
    /**
     * Validates the token successfully.
     */
	@Test
	void validateTokenSuccess() {
        long now = System.currentTimeMillis();
        User mockUser = new User();
        mockUser.setId(1l);
        mockUser.setUsername("colbytang");
        mockUser.setPassword("pass");
        mockUser.setEmail("ctang@email.com");
		String validToken = Jwts.builder()
                .setId(String.valueOf(mockUser.getId()))
                .setSubject(mockUser.getUsername())
                .setIssuer("revasphere")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration()))
                .signWith(jwtConfig.getSigningKey())
                .compact();
		
		assertDoesNotThrow(() -> {
			tokenService.validateToken(validToken);
		});
	}

    /**
     * Should fail to validate an expired token.
     */
	@Test
	void validateExpiredToken() {
        long now = System.currentTimeMillis();
        User mockUser = new User();
        mockUser.setId(1l);
        mockUser.setUsername("colbytang");
        mockUser.setPassword("pass");
        mockUser.setEmail("ctang@email.com");
		String validToken = Jwts.builder()
                .setId(String.valueOf(mockUser.getId()))
                .setSubject(mockUser.getUsername())
                .setIssuer("revasphere")
                .setIssuedAt(new Date(now))
                // Set expiration to before now.
                .setExpiration(new Date(now - 10l))
                .signWith(jwtConfig.getSigningKey())
                .compact();
		
            assertThrows(FailedAuthenticationException.class, () -> {
			tokenService.validateToken(validToken);
		});
	}
	
    /**
     * Should fail authentication on an invalid token.
     */
	@Test
	void validateTokenInvalidToken() {
		assertThrows(FailedAuthenticationException.class, () -> {
			tokenService.validateToken("aaaaa");
		});
	}

    @Test
    void getDefaultExpiration() {
        assertEquals(24*60*60*1000,tokenService.getDefaultExpiration());
    }
}
