package com.akier.akierpodcasts.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akier.akierpodcasts.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
