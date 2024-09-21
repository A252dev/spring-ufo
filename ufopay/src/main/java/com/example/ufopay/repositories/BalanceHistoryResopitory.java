package com.example.ufopay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ufopay.entities.BalanceHistory;
import java.util.List;

@Repository
public interface BalanceHistoryResopitory extends JpaRepository<BalanceHistory, Long> {

    List<BalanceHistory> findByUserId(Integer id);

    // List<BalanceHistory> findByUserId(Integer id);

}
