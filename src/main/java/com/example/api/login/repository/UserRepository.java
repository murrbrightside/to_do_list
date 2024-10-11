package com.example.api.login.repository;


import com.example.api.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserRepository extends JpaRepository<User, Long> {


    boolean existsByUsername(String username);
}
