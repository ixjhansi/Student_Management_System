package com.sms.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
	private final Key key;
	private final long expirationMs;

	public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expirationMs}") long expirationMs) {
		// secret length must be sufficient for HS256
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.expirationMs = expirationMs;
	}

	public String generateToken(String username, Collection<String> roles) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", roles);
		long now = System.currentTimeMillis();
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(now))
				.setExpiration(new Date(now + expirationMs)).signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public boolean validateToken(String token) {
		try {
			getClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			return false;
		} catch (ExpiredJwtException ex) {
			return false;
		}
	}

	public String getUsernameFromToken(String token) {
		return getClaimsJws(token).getBody().getSubject();
	}

	@SuppressWarnings("unchecked")
	public List<String> getRolesFromToken(String token) {
		Claims claims = getClaimsJws(token).getBody();
		Object rolesObj = claims.get("roles");
		if (rolesObj instanceof Collection) {
			return ((Collection<?>) rolesObj).stream().map(Object::toString).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	private Jws<Claims> getClaimsJws(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
	}
}