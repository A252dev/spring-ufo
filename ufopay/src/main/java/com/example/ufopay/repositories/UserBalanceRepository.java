package com.example.ufopay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ufopay.entities.UserBalance;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

    UserBalance findByUserId(Integer id);

}
