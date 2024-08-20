package com.example.ufopay.dto;

public record TransferRequest(String toEmail, Double summa, String currency) {
}
