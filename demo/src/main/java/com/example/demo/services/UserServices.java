package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.models.user;
import com.example.demo.repository.UserRepository;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public user registerUser(user user) {
        return userRepository.save(user);
    }

    public Optional<user> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<user> loginUser(String email, String password) {
        Optional<user> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            return optionalUser;
        }
        return Optional.empty();
    }
}