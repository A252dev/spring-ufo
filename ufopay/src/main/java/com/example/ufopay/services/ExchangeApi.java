package com.example.ufopay.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.ufopay.dto.ExchangeDto;

@Service
public class ExchangeApi {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public Map<String, Object> getData(ExchangeDto exchangeDto) throws IOException {

        InputStream is = new URL("https://v6.exchangerate-api.com/v6/ce3ba77a89e6e5e13dcbaf76/latest/" + exchangeDto.baseCurrency()).openStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json.toMap();
        } finally {
            is.close();
        }

        // Map<String, Double> map = new HashMap<>();

        // switch (sourceCurrency) {
        // case "USD":
        // map.put("EUR", summa * 0.92);
        // map.put("RUB", summa * 88.4);
        // return map;
        // case "EUR":
        // map.put("USD", summa * 1.09);
        // map.put("RUB", summa * 96.52);
        // return map;
        // case "RUB":
        // map.put("USD", summa * 0.01);
        // map.put("EUR", summa * 0.01);
        // return map;
        // default:
        // return null;
        // }

    }

}
