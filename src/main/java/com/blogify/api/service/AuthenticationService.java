package com.blogify.api.service;

import com.blogify.api.domain.dto.AuthenticationRequest;
import com.blogify.api.domain.dto.AuthenticationResponse;
import com.blogify.api.domain.repository.UserRepository;
import com.blogify.api.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest request) {
        // 1. Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. If authentication is successful, find the user
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(); // Should not happen if authentication passed

        // 3. Generate a JWT token
        var jwtToken = jwtService.generateToken(user);

        // 4. Return the token in the response
        return new AuthenticationResponse(jwtToken);
    }
}