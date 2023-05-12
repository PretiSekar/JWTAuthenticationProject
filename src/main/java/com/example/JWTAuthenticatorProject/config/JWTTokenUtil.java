package com.example.JWTAuthenticatorProject.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7432226534247248616L;
	private static final long JWT_TOKEN_VALIDITY = 5*60*60;
	
	@Value("${jwt.secret}")
	private String secret;
	
	public String generateToken(UserDetails lC) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(lC.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512,secret).compact();
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimssResolver) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claimssResolver.apply(claims);
	}
	
	public String getUsername(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDate(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public Boolean validateToken(String token, UserDetails lC) {
		String username = getUsername(token);
		Date expDate =  getExpirationDate(token);
		return (lC.getUsername().equals(username))? true: false;
	}
	
	
}
