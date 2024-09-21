package com.example.ufopay.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "balance_history")
public class BalanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "userId", nullable = false)
    private Integer userId;
    @Column(name = "toUserId", nullable = false)
    private Integer toUserId;
    @Column(name = "summa", nullable = false)
    private Double summa;
    @Column(name = "currency", nullable = false)
    private String currency;

    public BalanceHistory(Integer userId, Integer toUserId, Double summa, String currency) {
        this.userId = userId;
        this.toUserId = toUserId;
        this.summa = summa;
        this.currency = currency;
    }

}
