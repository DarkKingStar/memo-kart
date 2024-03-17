package com.memo.kart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.kart.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
}
