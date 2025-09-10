package com.sms.controller;


import com.sms.request.AuthRequest;
import com.sms.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService; // our CustomUserDetailsService

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            UserDetails ud = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(ud);
            Map<String, Object> res = new HashMap<>();
            res.put("token", token);
            res.put("type", "Bearer");
            res.put("expiresInMs", Long.parseLong(Optional.ofNullable(System.getProperty("jwt.expiration-ms")).orElse("3600000")));
            return ResponseEntity.ok(res);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }
}