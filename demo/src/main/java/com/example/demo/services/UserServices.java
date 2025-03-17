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
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String username, String email, String password, Role role) {

        String encodedPassword = passwordEncoder.encode(password);

        User user;
        if (role == Role.ADMIN) {
            user = new AdminUser(username, email, encodedPassword);
        } else {
            user = new RegularUser(username, email, encodedPassword);
        }
        return userRepository.save(user);
    }
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> loginUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
    
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
    
            if (passwordEncoder.matches(password, user.getPassword())) {
                return optionalUser;
            }
        }
        return Optional.empty();
    }
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}