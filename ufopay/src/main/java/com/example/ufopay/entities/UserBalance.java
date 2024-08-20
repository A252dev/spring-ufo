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
@Table(name = "users_balance")
public class UserBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "userId", nullable = false)
    private Integer userId;
    @Column(name = "balance_usd", nullable = false)
    private Double balance_usd = 0.0;
    @Column(name = "balance_eur", nullable = false)
    private Double balance_eur = 0.0;
    @Column(name = "balance_rub", nullable = false)
    private Double balance_rub = 0.0;

    public UserBalance(Integer userId, Double balance_usd, Double balance_eur, Double balance_rub) {
        this.userId = userId;
        this.balance_usd = balance_usd;
        this.balance_eur = balance_eur;
        this.balance_rub = balance_rub;
    }

}
