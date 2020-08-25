/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.security;

import com.system.management.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nauman
 */
@Component
public class JwtUtils {
    @Value("${private.key}")
	private String key;
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	public String getUsernameFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	private Claims getAllClaimsFromToken(String token)
	{
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(UserDetails userDetails,User user) {
		Map<String, Object> claims = new HashMap<>();
                System.out.println("JWT Util called");
		return doGenerateToken(claims, userDetails,user);
	}
	
	private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails,User user) {
            System.out.println("Token issued Successfully");
		return Jwts.builder().setSubject(user.getUserName()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100000))
				.setIssuer("System")
                                .setIssuedAt(new Date(System.currentTimeMillis()))
				
				.signWith(SignatureAlgorithm.HS512, key).compact();
                
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
