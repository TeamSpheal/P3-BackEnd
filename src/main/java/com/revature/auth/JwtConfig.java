package com.revature.auth;

import java.security.Key;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtConfig {
	// Value allows you to use SpEL, but also
	// it allows you to get properties from the application.properties/yml
	@Value("${jwt.secret}")
	private String salt;
	
	// example of using Value with SpEL (Spring expression language)
	// to calculate the number of milliseconds in a day. Spring
	// will then set this value for us
	@Value("#{24*60*60*1000}")
	private int expiration;
	
	// this algorithm requires a 256-character (2048 bit) key.
	private static final SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
	private Key signingKey;

	private String issuer = "revasphere";
	
	// this annotation means that the method should be
	// executed after dependency injection is complete
	@PostConstruct
	public void createKey() {
		byte[] saltyBytes = DatatypeConverter.parseBase64Binary(salt);
		signingKey = new SecretKeySpec(saltyBytes, sigAlg.getJcaName());
	}
	
	public int getExpiration() {
		return this.expiration;
	}
	
	public SignatureAlgorithm getSigAlg() {
		return sigAlg;
	}
	
	public Key getSigningKey() {
		return this.signingKey;
	}

	public String getIssuerName() {
		return issuer;
	}
}
