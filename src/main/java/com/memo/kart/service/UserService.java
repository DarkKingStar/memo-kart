package com.memo.kart.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.kart.model.User;
import com.memo.kart.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
