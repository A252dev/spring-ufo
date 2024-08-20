package com.example.ufopay.dto;

public record ConvertRequest(String fromCurrency, String toCurrency, Double summa) {
}
