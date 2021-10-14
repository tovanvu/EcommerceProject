package com.vti.ultils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenHelper implements Serializable {
	
	Logger log = LoggerFactory.getLogger(TokenHelper.class);

	private static final long serialVersionUID = 1L;

	@Value("${app.name}")
	private String APP_NAME;

	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${jwt.expires_in}")
	private int EXPIRES_IN;

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public String generateToken(String username) {
		String jws = Jwts.builder().setIssuer(APP_NAME).setSubject(username).setIssuedAt(generateCurrentDate())
				.setExpiration(generateExpirationDate()).signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		return jws;
	}

	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private long getCurrentTimeMillis() {
		ZoneId zdt = ZoneId.systemDefault();
		return LocalDateTime.now().atZone(zdt).toEpochSecond() * 1000;
	}

	private Date generateCurrentDate() {
		return new Date(getCurrentTimeMillis());
	}

	public Date generateExpirationDate() {
		return new Date(getCurrentTimeMillis() + this.EXPIRES_IN * 1000);
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.info("Invalid JWT token: {}",ex.getMessage());
		} catch (ExpiredJwtException ex) {
			log.info("Expired JWT token: {}",ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			log.info("Unsupported JWT token: {}",ex.getMessage());
		} catch (IllegalArgumentException ex) {
			log.info("JWT claims string is empty : {}",ex.getMessage());
		}
		return false;
	}
}