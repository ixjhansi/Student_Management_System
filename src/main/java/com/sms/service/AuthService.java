package com.sms.service;

import com.sms.model.Role;
import com.sms.model.User;
import com.sms.repository.RoleRepository;
import com.sms.repository.UserRepository;
import com.sms.request.LoginRequest;
import com.sms.request.RegisterRequest;
import com.sms.response.LoginResponse;
import com.sms.security.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// ✅ Login logic
	public ResponseEntity<?> login(LoginRequest request) {
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			UserDetails userDetails = (UserDetails) auth.getPrincipal();

			var roles = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());

			String token = jwtUtil.generateToken(userDetails.getUsername(), roles);

			return ResponseEntity.ok(new LoginResponse(token, userDetails.getUsername(), roles));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid username or password: " + ex.getMessage());
		}
	}

	// ✅ Registration logic
	public ResponseEntity<?> register(RegisterRequest request) {
		try {
			if (userRepository.findByUsername(request.getUsername()).isPresent()) {
				return ResponseEntity.badRequest().body("Username already taken");
			}

			Role role = roleRepository.findByName(request.getRole())
					.orElseThrow(() -> new IllegalArgumentException("Role not found: " + request.getRole()));

			User user = new User();
			user.setUsername(request.getUsername());
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			user.setFullName(request.getFullName());
			user.setRole(role);

			userRepository.save(user);

			return ResponseEntity.ok("User registered successfully");

		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Something went wrong: " + ex.getMessage());
		}
	}
}
