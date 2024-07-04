package com.akier.akierpodcasts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akier.akierpodcasts.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
