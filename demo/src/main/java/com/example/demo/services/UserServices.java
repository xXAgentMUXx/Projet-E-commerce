package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.AdminUser;
import com.example.demo.models.RegularUser;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServices {
    // Injecting UserRepository to interact with the database
    @Autowired
    private UserRepository userRepository;

    // Password encoder for encoding passwords
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String username, String email, String password, Role role) {
        // Encoding the password
        String encodedPassword = passwordEncoder.encode(password);

        // Creating a new user based on the role
        User user;
        if (role == Role.ADMIN) {
            user = new AdminUser(username, email, encodedPassword);
        } else {
            user = new RegularUser(username, email, encodedPassword);
        }
        return userRepository.save(user);
    }
     // Finding the user by email in the repository
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> loginUser(String email, String password) {
        // Finding the user by email
        Optional<User> optionalUser = userRepository.findByEmail(email);
    
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Checking if the provided password matches the encoded password
            if (passwordEncoder.matches(password, user.getPassword())) {
                return optionalUser;
            }
        }
        // If the user does not exist or the password does not match, return empty
        return Optional.empty();
    }
    // Finding the user by ID in the repository
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}