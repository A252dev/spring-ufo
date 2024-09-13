package com.example.ufopay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ufopay.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // UserDto findByEmail(String email);

    Optional<User> findByEmail(String email);

    User findByUserId(Integer id);

}
