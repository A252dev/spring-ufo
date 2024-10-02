package com.example.ufopay.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ufopay.dto.ConvertResponse;
import com.example.ufopay.dto.ExchangeDto;
import com.example.ufopay.dto.ProfileDto;
import com.example.ufopay.dto.TransferRequest;
import com.example.ufopay.dto.TransferResponse;
import com.example.ufopay.entities.BalanceHistory;
import com.example.ufopay.services.ActionService;
import com.example.ufopay.services.ExchangeApi;
import lombok.RequiredArgsConstructor;

// Auth for access here
@RequestMapping("/profile")
@RequiredArgsConstructor
@RestController
public class ProfileController {

    @Autowired
    private final ActionService actionService;
    @Autowired
    private final ExchangeApi exchangeApi;

    @GetMapping("/index")
    public ResponseEntity<ProfileDto> index() {

        if (actionService.getUserData() != null) {
            return ResponseEntity.ok(new ProfileDto(actionService.getUserInfo()));

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping("/transfer")
    public TransferResponse tranfer(@RequestBody TransferRequest transferDto) {
        return actionService.transfer(transferDto);
    }

    @PostMapping("/convert")
    public ConvertResponse convertation(@RequestBody ExchangeDto convertRequest) throws JSONException, IOException {
        return actionService.convert(convertRequest);
    }

    // get the actual data (info)
    @GetMapping("/exchange")
    public Object exchange(@RequestBody ExchangeDto exchangeData) throws IOException {
        return exchangeApi.getData(exchangeData);
    }

    @GetMapping("/addbalance")
    public TransferResponse addBalance(@RequestBody TransferRequest transferRequest) {
        return actionService.addBalance(new BigDecimal(transferRequest.summa()), transferRequest.currency());
    }

    @GetMapping("/history")
    public List<BalanceHistory> getHistory() {
        return actionService.getHistory(actionService.getMyBalance().getUserId());
    }

}
