package com.gwrocha.libary.config.security;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

	@Value("${libary.security.token_key}")
	private String keyString;
	
	@Value("${libary.security.expiratiion_time}")
	private Long expiratiionTime;
	
	private Key key;
	
	public String generateToken(String username) {
		
		LocalDateTime localDateTimeExpiration = LocalDateTime.now().plus(Duration.of(expiratiionTime, SECONDS));
		Date dateExpirantion = Date.from(localDateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant());
		
		return Jwts.builder()
					.setSubject(username)
					.setIssuedAt(new Date())
					.setExpiration(dateExpirantion)
					.signWith(getKey(), SignatureAlgorithm.HS512)
					.compact();
	}
	
	public String extractUsernameFrom(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	private Key getKey() {
		if(this.key == null)
			key = Keys.hmacShaKeyFor(keyString.getBytes());
		return key;
	}
	
}
