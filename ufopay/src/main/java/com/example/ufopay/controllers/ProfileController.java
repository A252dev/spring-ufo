package com.example.ufopay.controllers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ufopay.dto.ConvertRequest;
import com.example.ufopay.dto.ConvertResponse;
import com.example.ufopay.dto.ExchangeDto;
import com.example.ufopay.dto.ProfileDto;
import com.example.ufopay.dto.TransferRequest;
import com.example.ufopay.dto.TransferResponse;
import com.example.ufopay.services.ExchangeApi;
import com.example.ufopay.services.UserService;
import lombok.RequiredArgsConstructor;

// Auth for access here
@RequestMapping("/profile")
@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final UserService userService;
    private final ExchangeApi exchangeApi;

    // Show content with information for the Angular
    @GetMapping("/test")
    public ResponseEntity<ProfileDto> index() {
        if (userService.getUserInfo() != null) {
            return ResponseEntity.ok(new ProfileDto(userService.getUserInfo()));

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    // Make a transfer
    @PostMapping("/transfer")
    public TransferResponse tranfer(@RequestBody TransferRequest transferDto) {
        return userService.transfer(transferDto);
    }

    @PostMapping("/convert")
    public ConvertResponse convertation(@RequestBody ConvertRequest convertRequest) {
        return userService.convert(convertRequest);
    }

    @GetMapping("/exchange")
    public Object exchange(@RequestBody ExchangeDto baseCurrency) throws IOException {
        Map<String, Object> currencies = exchangeApi.getData(baseCurrency);
        Object list = currencies.get("conversion_rates");

        Field[] fields = list.getClass().getDeclaredFields();

        Map<String, Object> data = new HashMap<>();

        for (Field field: fields) {
            field.setAccessible(true);
            // data.put(field.getName(), field.get(list));
        }

        return fields.toString();
    }

}
