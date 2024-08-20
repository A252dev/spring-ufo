package com.example.ufopay.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "userId", unique = true)
    private Integer userId;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "secondName", nullable = false)
    private String secondName;
    @Column(name = "birthday", nullable = false)
    private String birthday;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    public User(Integer userId, String firstName, String secondName, String birthday, String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
    }

}
