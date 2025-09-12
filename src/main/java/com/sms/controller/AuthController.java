package com.sms.controller;

import com.sms.model.Role;
import com.sms.model.User;
import com.sms.repository.RoleRepository;
import com.sms.repository.UserRepository;
import com.sms.request.LoginRequest;
import com.sms.request.RegisterRequest;
import com.sms.response.LoginResponse;
import com.sms.security.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// ✅ Login endpoint
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		UserDetails userDetails = (UserDetails) auth.getPrincipal();

		var roles = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());

		String token = jwtUtil.generateToken(userDetails.getUsername(), roles);

		return new LoginResponse(token, userDetails.getUsername(), roles);
	}

	// ✅ Registration endpoint
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("Username already taken");
		}

		// Fetch existing role from DB
		Role role = roleRepository.findByName(request.getRole())
				.orElseThrow(() -> new RuntimeException("Role not found: " + request.getRole()));

		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setFullName(request.getFullName());
		user.setRole(role);

		userRepository.save(user);

		return ResponseEntity.ok("User registered successfully");
	}
}
