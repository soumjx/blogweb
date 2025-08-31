package com.blogify.api.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;


import com.blogify.api.domain.dto.UserDto;
import com.blogify.api.domain.dto.UserRegistrationRequest;
import com.blogify.api.domain.entity.User;
import com.blogify.api.domain.repository.UserRepository;
import com.blogify.api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(UserRegistrationRequest registrationRequest) {
        // Check if user already exists
        userRepository.findByEmail(registrationRequest.getEmail()).ifPresent(user -> {
            throw new IllegalStateException("User with this email already exists");
        });
        userRepository.findByUsername(registrationRequest.getUsername()).ifPresent(user -> {
            throw new IllegalStateException("User with this username already exists");
        });

        // 2. Create a new User entity
        User newUser = new User();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setEmail(registrationRequest.getEmail());

        //updated it to hashed password eccoder from simple storing
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));


        // 3. Save the new user to the database
        User savedUser = userRepository.save(newUser);

        // 4. Map the saved user to a DTO and return it
        return new UserDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }
}