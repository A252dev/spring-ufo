package com.example.ufopay.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ufopay.dto.ConvertRequest;
import com.example.ufopay.dto.ConvertResponse;
import com.example.ufopay.dto.ProfileDto;
import com.example.ufopay.dto.TransferRequest;
import com.example.ufopay.dto.TransferResponse;
import com.example.ufopay.exceptions.AppException;
import com.example.ufopay.services.ExchangeApi;
import com.example.ufopay.services.UserService;
import lombok.RequiredArgsConstructor;

// Здесь нужно быть авторизованным, чтобы доступ сюда иметь
@RequestMapping("/profile")
@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final UserService userService;
    private final ExchangeApi exchangeApi;

    // Отдаём контент со всей информацией, которая в Angular выводится на экран
    @GetMapping("/test")
    public ResponseEntity<ProfileDto> index() {
        if (userService.getUserInfo() != null) {
            return ResponseEntity.ok(new ProfileDto(userService.getUserInfo()));

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    // Тут делается перевод
    @PostMapping("/transfer")
    public TransferResponse tranfer(@RequestBody TransferRequest transferDto) {
        return userService.transfer(transferDto);
    }

    @PostMapping("/convert")
    public ConvertResponse convertation(@RequestBody ConvertRequest convertRequest) {
        return userService.convert(convertRequest);
    }

    @GetMapping("/getdata")
    public Map<String, Double> getData() {

        return exchangeApi.getData("USD", 10.0);
        // return IntStream.range(0, data.length())
        // .mapToObj(index -> (JSONObject)
        // data.get(index)).collect(Collectors.toList());
    }

}
