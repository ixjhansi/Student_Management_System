package com.sms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		
		

		// -----------------------------
		// ✅ Skip Swagger & auth endpoints
		// -----------------------------
		// Use getServletPath() instead of getRequestURI()
	    String path = request.getServletPath();

	    // Skip public endpoints
	    if (path.startsWith("/v3/api-docs") ||
	        path.startsWith("/swagger-ui") ||
	        path.startsWith("/swagger-resources") ||
	        path.startsWith("/webjars") ||
	        path.startsWith("/api/auth")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

		// -----------------------------
		// Normal JWT authentication logic
		// -----------------------------
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);

			if (jwtUtil.validateToken(token)) {
				String username = jwtUtil.getUsernameFromToken(token);
				List<SimpleGrantedAuthority> authorities = jwtUtil.getRolesFromToken(token).stream()
						.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
						authorities);

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}
