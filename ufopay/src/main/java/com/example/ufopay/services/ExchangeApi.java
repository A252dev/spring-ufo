package com.example.ufopay.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ExchangeApi {

    public Map<String, Double> getData(String sourceCurrency, Double summa) {

        Map<String, Double> map = new HashMap<>();

        switch (sourceCurrency) {
            case "USD":
                map.put("EUR", summa * 0.92);
                map.put("RUB", summa * 88.4);
                return map;
            case "EUR":
                map.put("USD", summa * 1.09);
                map.put("RUB", summa * 96.52);
                return map;
            case "RUB":
                map.put("USD", summa * 0.01);
                map.put("EUR", summa * 0.01);
                return map;
            default:
                return null;
        }

    }

}
