package com.hego.kart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hego.kart.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
}
