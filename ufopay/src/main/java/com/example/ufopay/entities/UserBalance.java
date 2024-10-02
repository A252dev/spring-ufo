package com.example.ufopay.entities;

import java.math.BigDecimal;

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

    // Australian dollar
    @Column(name = "AUD", nullable = false)
    private Double AUD = 0.0;

    // Brazilian real
    @Column(name = "BRL", nullable = false)
    private Double BRL = 0.0;

    // Canadian dollar
    @Column(name = "CAD", nullable = false)
    private Double CAD = 0.0;

    // Chinese Renmenbi
    @Column(name = "CNY", nullable = false)
    private Double CNY = 0.0;

    // Czech koruna
    @Column(name = "CZK", nullable = false)
    private Double CZK = 0.0;

    // Danish krone
    @Column(name = "DKK", nullable = false)
    private Double DKK = 0.0;

    // Euro
    @Column(name = "EUR", nullable = false)
    private Double EUR = 0.0;

    // Hong Kong dollar
    @Column(name = "HKD", nullable = false)
    private Double HKD = 0.0;

    // Hungarian forint
    @Column(name = "HUF", nullable = false)
    private Double HUF = 0.0;

    // Israeli new shekel
    @Column(name = "ILS", nullable = false)
    private Double ILS = 0.0;

    // Japanese yen
    @Column(name = "JPY", nullable = false)
    private Double JPY = 0.0;

    // Malaysian ringgit
    @Column(name = "MYR", nullable = false)
    private Double MYR = 0.0;

    // Mexican peso
    @Column(name = "MXN", nullable = false)
    private Double MXN = 0.0;

    // New Taiwan dollar
    @Column(name = "TWD", nullable = false)
    private Double TWD = 0.0;

    // New Zealand dollar
    @Column(name = "NZD", nullable = false)
    private Double NZD = 0.0;

    // Norwegian krone
    @Column(name = "NOK", nullable = false)
    private Double NOK = 0.0;

    // Philippine peso
    @Column(name = "PHP", nullable = false)
    private Double PHP = 0.0;

    // Polish złoty
    @Column(name = "PLN", nullable = false)
    private Double PLN = 0.0;

    // Pound sterling
    @Column(name = "GBP", nullable = false)
    private Double GBP = 0.0;

    // Singapore dollar
    @Column(name = "SGD", nullable = false)
    private Double SGD = 0.0;

    // Swedish krona
    @Column(name = "SEK", nullable = false)
    private Double SEK = 0.0;

    // Swiss franc
    @Column(name = "CHF", nullable = false)
    private Double CHF = 0.0;

    // Thai baht
    @Column(name = "THB", nullable = false)
    private Double THB = 0.0;

    // United States dollar
    @Column(name = "USD", nullable = false)
    private Double USD = 0.0;

    public UserBalance(Integer userId, Double AUD, Double BRL, Double CAD, Double CNY, Double CZK,
            Double DKK, Double EUR, Double HKD, Double HUF, Double ILS, Double JPY, Double MYR, Double MXN, Double TWD,
            Double NZD, Double NOK, Double PHP, Double PLN, Double GBP, Double SGD, Double SEK, Double CHF, Double THB,
            Double USD) {
        this.userId = userId;
        this.AUD = AUD;
        this.BRL = BRL;
        this.CAD = CAD;
        this.CNY = CNY;
        this.CZK = CZK;
        this.DKK = DKK;
        this.EUR = EUR;
        this.HKD = HKD;
        this.HUF = HUF;
        this.ILS = ILS;
        this.JPY = JPY;
        this.MYR = MYR;
        this.MXN = MXN;
        this.TWD = TWD;
        this.NZD = NZD;
        this.NOK = NOK;
        this.PHP = PHP;
        this.PLN = PLN;
        this.GBP = GBP;
        this.SGD = SGD;
        this.SEK = SEK;
        this.CHF = CHF;
        this.THB = THB;
        this.USD = USD;
    }

}
