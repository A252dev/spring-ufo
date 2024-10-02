package com.example.ufopay.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.ufopay.dto.ConvertResponse;
import com.example.ufopay.dto.ExchangeDto;
import com.example.ufopay.dto.TransferRequest;
import com.example.ufopay.dto.TransferResponse;
import com.example.ufopay.entities.BalanceHistory;
import com.example.ufopay.entities.User;
import com.example.ufopay.entities.UserBalance;
import com.example.ufopay.repositories.BalanceHistoryResopitory;
import com.example.ufopay.repositories.UserBalanceRepository;
import com.example.ufopay.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActionService {

    private final UserRepository userRepository;

    private final UserBalanceRepository userBalanceRepository;
    private final BalanceHistoryResopitory balanceHistoryRepository;
    private final ExchangeApi exchangeApi;

    public Authentication getUserData() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserBalance getMyBalance() {
        return userBalanceRepository
                .findByUserId(
                        userRepository.findByEmail(getUserData().getName()).get().getUserId());
    }

    public List<Map> getUserInfo() {

        List<Map> info = new ArrayList();
        Map<String, String> userInfo = new HashMap<>();
        Map<String, Double> userBalances = new HashMap<>();

        userInfo.put("firstName",
                userRepository.findByEmail(getUserData().getName()).get().getFirstName());
        userInfo.put("secondName",
                userRepository.findByEmail(getUserData().getName()).get().getSecondName());
        userInfo.put("email",
                userRepository.findByEmail(getUserData().getName()).get().getEmail());
        userInfo.put("birthday",
                userRepository.findByEmail(getUserData().getName()).get().getBirthday());

        userBalances.put("AUD", getMyBalance().getAUD());
        userBalances.put("BRL", getMyBalance().getBRL());
        userBalances.put("CAD", getMyBalance().getCAD());
        userBalances.put("CNY", getMyBalance().getCNY());
        userBalances.put("CZK", getMyBalance().getCZK());
        userBalances.put("DKK", getMyBalance().getDKK());
        userBalances.put("EUR", getMyBalance().getEUR());
        userBalances.put("HKD", getMyBalance().getHKD());
        userBalances.put("HUF", getMyBalance().getHUF());
        userBalances.put("ILS", getMyBalance().getILS());
        userBalances.put("JPY", getMyBalance().getJPY());
        userBalances.put("MYR", getMyBalance().getMYR());
        userBalances.put("MXN", getMyBalance().getMXN());
        userBalances.put("TWD", getMyBalance().getTWD());
        userBalances.put("NZD", getMyBalance().getNZD());
        userBalances.put("NOK", getMyBalance().getNOK());
        userBalances.put("PHP", getMyBalance().getPHP());
        userBalances.put("PLN", getMyBalance().getPLN());
        userBalances.put("GBP", getMyBalance().getGBP());
        userBalances.put("SGD", getMyBalance().getSGD());
        userBalances.put("SEK", getMyBalance().getSEK());
        userBalances.put("CHF", getMyBalance().getCHF());
        userBalances.put("THB", getMyBalance().getTHB());
        userBalances.put("USD", getMyBalance().getUSD());

        info.add(userInfo);
        info.add(userBalances);

        return info;
    }

    public TransferResponse transfer(TransferRequest transferData) {

        Optional<User> tUser = userRepository.findByEmail(transferData.toEmail());

        if (getUserData().getName() != null) {

            if (tUser.isPresent()) {

                UserBalance tUserBalance = userBalanceRepository.findByUserId(tUser.get().getUserId());

                String errorResponse = "Not enough money";

                switch (transferData.currency()) {
                    case "AUD":
                        if (getMyBalance().getAUD() <= 0
                                || getMyBalance().getAUD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setAUD(getMyBalance().getAUD() - transferData.summa());
                            tUserBalance.setAUD(tUserBalance.getAUD() + transferData.summa());
                            break;
                        }
                    case "BRL":
                        if (getMyBalance().getBRL() <= 0
                                || getMyBalance().getBRL() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setBRL(getMyBalance().getBRL() - transferData.summa());
                            tUserBalance.setBRL(tUserBalance.getBRL() + transferData.summa());
                            break;
                        }
                    case "CAD":
                        if (getMyBalance().getCAD() <= 0
                                || getMyBalance().getCAD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setCAD(getMyBalance().getCAD() - transferData.summa());
                            tUserBalance.setCAD(tUserBalance.getCAD() + transferData.summa());
                            break;
                        }
                    case "CNY":
                        if (getMyBalance().getCNY() <= 0
                                || getMyBalance().getCNY() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setCNY(getMyBalance().getCNY() - transferData.summa());
                            tUserBalance.setCNY(tUserBalance.getCNY() + transferData.summa());
                            break;
                        }
                    case "CZK":
                        if (getMyBalance().getCZK() <= 0
                                || getMyBalance().getCZK() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setCZK(getMyBalance().getCZK() - transferData.summa());
                            tUserBalance.setUSD(tUserBalance.getUSD() + transferData.summa());
                            break;
                        }
                    case "DKK":
                        if (getMyBalance().getDKK() <= 0
                                || getMyBalance().getDKK() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setDKK(getMyBalance().getDKK() - transferData.summa());
                            tUserBalance.setDKK(tUserBalance.getDKK() + transferData.summa());
                            break;
                        }
                    case "EUR":
                        if (getMyBalance().getEUR() <= 0
                                || getMyBalance().getEUR() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setEUR(getMyBalance().getEUR() - transferData.summa());
                            tUserBalance.setEUR(tUserBalance.getEUR() + transferData.summa());
                            break;
                        }
                    case "HKD":
                        if (getMyBalance().getHKD() <= 0
                                || getMyBalance().getHKD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setHKD(getMyBalance().getHKD() - transferData.summa());
                            tUserBalance.setHKD(tUserBalance.getHKD() + transferData.summa());
                            break;
                        }
                    case "HUF":
                        if (getMyBalance().getHUF() <= 0
                                || getMyBalance().getHUF() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setHUF(getMyBalance().getHUF() - transferData.summa());
                            tUserBalance.setHUF(tUserBalance.getHUF() + transferData.summa());
                            break;
                        }
                    case "ILS":
                        if (getMyBalance().getILS() <= 0
                                || getMyBalance().getILS() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setILS(getMyBalance().getILS() - transferData.summa());
                            tUserBalance.setILS(tUserBalance.getILS() + transferData.summa());
                            break;
                        }
                    case "JPY":
                        if (getMyBalance().getJPY() <= 0
                                || getMyBalance().getJPY() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setJPY(getMyBalance().getJPY() - transferData.summa());
                            tUserBalance.setJPY(tUserBalance.getJPY() + transferData.summa());
                            break;
                        }
                    case "MYR":
                        if (getMyBalance().getMYR() <= 0
                                || getMyBalance().getMYR() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setMYR(getMyBalance().getMYR() - transferData.summa());
                            tUserBalance.setMYR(tUserBalance.getMYR() + transferData.summa());
                            break;
                        }
                    case "MXN":
                        if (getMyBalance().getMXN() <= 0
                                || getMyBalance().getMXN() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setMXN(getMyBalance().getMXN() - transferData.summa());
                            tUserBalance.setMXN(tUserBalance.getMXN() + transferData.summa());
                            break;
                        }
                    case "TWD":
                        if (getMyBalance().getTWD() <= 0
                                || getMyBalance().getTWD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setTWD(getMyBalance().getTWD() - transferData.summa());
                            tUserBalance.setTWD(tUserBalance.getTWD() + transferData.summa());
                            break;
                        }
                    case "NZD":
                        if (getMyBalance().getNZD() <= 0
                                || getMyBalance().getNZD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setNZD(getMyBalance().getNZD() - transferData.summa());
                            tUserBalance.setNZD(tUserBalance.getNZD() + transferData.summa());
                            break;
                        }
                    case "NOK":
                        if (getMyBalance().getNOK() <= 0
                                || getMyBalance().getNOK() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setNOK(getMyBalance().getNOK() - transferData.summa());
                            tUserBalance.setNOK(tUserBalance.getNOK() + transferData.summa());
                            break;
                        }
                    case "PHP":
                        if (getMyBalance().getPHP() <= 0
                                || getMyBalance().getPHP() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setPHP(getMyBalance().getPHP() - transferData.summa());
                            tUserBalance.setPHP(tUserBalance.getPHP() + transferData.summa());
                            break;
                        }
                    case "PLN":
                        if (getMyBalance().getPLN() <= 0
                                || getMyBalance().getPLN() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setPLN(getMyBalance().getPLN() - transferData.summa());
                            tUserBalance.setPLN(tUserBalance.getPLN() + transferData.summa());
                            break;
                        }
                    case "GBP":
                        if (getMyBalance().getGBP() <= 0
                                || getMyBalance().getGBP() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setGBP(getMyBalance().getGBP() - transferData.summa());
                            tUserBalance.setGBP(tUserBalance.getGBP() + transferData.summa());
                            break;
                        }
                    case "SGD":
                        if (getMyBalance().getSGD() <= 0
                                || getMyBalance().getSGD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setSGD(getMyBalance().getSGD() - transferData.summa());
                            tUserBalance.setSGD(tUserBalance.getSGD() + transferData.summa());
                            break;
                        }
                    case "SEK":
                        if (getMyBalance().getSEK() <= 0
                                || getMyBalance().getSEK() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setSEK(getMyBalance().getSEK() - transferData.summa());
                            tUserBalance.setSEK(tUserBalance.getSEK() + transferData.summa());
                            break;
                        }
                    case "CHF":
                        if (getMyBalance().getCHF() <= 0
                                || getMyBalance().getCHF() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setCHF(getMyBalance().getCHF() - transferData.summa());
                            tUserBalance.setCHF(tUserBalance.getCHF() + transferData.summa());
                            break;
                        }
                    case "THB":
                        if (getMyBalance().getTHB() <= 0
                                || getMyBalance().getTHB() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setTHB(getMyBalance().getTHB() - transferData.summa());
                            tUserBalance.setTHB(tUserBalance.getTHB() + transferData.summa());
                            break;
                        }
                    case "USD":
                        if (getMyBalance().getUSD() <= 0
                                || getMyBalance().getUSD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setUSD(getMyBalance().getUSD() - transferData.summa());
                            tUserBalance.setUSD(tUserBalance.getUSD() + transferData.summa());
                            break;
                        }
                    default:
                        return new TransferResponse("Currency is not selected!");
                }

                // create and save the transaction to the database
                BalanceHistory transaction = new BalanceHistory(getMyBalance().getUserId(), tUserBalance.getUserId(),
                        transferData.summa(), transferData.currency());
                balanceHistoryRepository.save(transaction);

                // save the balance
                userBalanceRepository.save(tUserBalance);
                userBalanceRepository.save(getMyBalance());

                return new TransferResponse("Balance is transfered!");
            } else {
                return new TransferResponse("User not found!");
            }
        } else {
            return new TransferResponse("You are not authorized!");
        }

    }

    public JSONObject getSummaFromBaseCurrency(ExchangeDto exchangeDto) throws IOException {

        Map<String, Object> currencies = exchangeApi.getData(exchangeDto);
        Object list = currencies.get("conversion_rates");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonString = ow.writeValueAsString(list);

        return new JSONObject(jsonString);
    }

    public ConvertResponse setTargetBalanceCurrency(ExchangeDto convertRequest) throws JSONException, IOException {

        switch (convertRequest.targetCurrency()) {
            case "AUD":
                Double AUD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setAUD(getMyBalance().getAUD() + Math.floor(AUD * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "BRL":
                Double BRL = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setBRL(getMyBalance().getBRL() + Math.floor(BRL * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "CAD":
                Double CAD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setCAD(getMyBalance().getCAD() + Math.floor(CAD * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "CNY":
                Double CNY = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setCNY(getMyBalance().getCNY() + Math.floor(CNY * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "CZK":
                Double CZK = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setCZK(getMyBalance().getCZK() + Math.floor(CZK * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "DKK":
                Double DKK = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setDKK(getMyBalance().getDKK() + Math.floor(DKK * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "EUR":
                Double EUR = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setEUR(getMyBalance().getEUR() + Math.floor(EUR * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "HKD":
                Double HKD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setHKD(getMyBalance().getHKD() + Math.floor(HKD * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "HUF":
                Double HUF = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setHUF(getMyBalance().getHUF() + Math.floor(HUF * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "ILS":
                Double ILS = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setILS(getMyBalance().getILS() + Math.floor(ILS * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "JPY":
                Double JPY = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setJPY(getMyBalance().getJPY() + Math.floor(JPY * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "MYR":
                Double MYR = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setMYR(getMyBalance().getMYR() + Math.floor(MYR * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "MXN":
                Double MXN = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setMXN(getMyBalance().getMXN() + Math.floor(MXN * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "TWD":
                Double TWD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setTWD(getMyBalance().getTWD() + Math.floor(TWD * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "NZD":
                Double NZD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setNZD(getMyBalance().getNZD() + Math.floor(NZD * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "NOK":
                Double NOK = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setNOK(getMyBalance().getNOK() + Math.floor(NOK * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "PHP":
                Double PHP = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setPHP(getMyBalance().getPHP() + Math.floor(PHP * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "PLN":
                Double PLN = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setPLN(getMyBalance().getPLN() + Math.floor(PLN * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "GBP":
                Double GBP = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setGBP(getMyBalance().getGBP() + Math.floor(GBP * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "SGD":
                Double SGD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setSGD(getMyBalance().getSGD() + Math.floor(SGD * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "SEK":
                Double SEK = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setSEK(getMyBalance().getSEK() + Math.floor(SEK * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "CHF":
                Double CHF = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setCHF(getMyBalance().getCHF() + Math.floor(CHF * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "THB":
                Double THB = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setTHB(getMyBalance().getTHB() + Math.floor(THB * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            case "USD":
                Double USD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());
                getMyBalance()
                        .setUSD(getMyBalance().getUSD() + Math.floor(USD * convertRequest.summa() * 100) / 100);
                userBalanceRepository.save(getMyBalance());
                break;
            default:
                return null;
        }
        return null;

    }

    public ConvertResponse convert(ExchangeDto convertData) throws JSONException, IOException {

        if (convertData != null) {
            switch (convertData.baseCurrency()) {
                case "AUD":
                    getMyBalance().setAUD(getMyBalance().getAUD() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "BRL":
                    getMyBalance().setBRL(getMyBalance().getBRL() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "CAD":
                    getMyBalance().setCAD(getMyBalance().getCAD() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "CNY":
                    getMyBalance().setCNY(getMyBalance().getCNY() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "CZK":
                    getMyBalance().setCZK(getMyBalance().getCZK() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "DKK":
                    getMyBalance().setDKK(getMyBalance().getDKK() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "EUR":
                    getMyBalance().setEUR(getMyBalance().getEUR() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "HKD":
                    getMyBalance().setHKD(getMyBalance().getHKD() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "HUF":
                    getMyBalance().setHUF(getMyBalance().getHUF() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "ILS":
                    getMyBalance().setILS(getMyBalance().getILS() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "JPY":
                    getMyBalance().setJPY(getMyBalance().getJPY() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "MYR":
                    getMyBalance().setMYR(getMyBalance().getMYR() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "MXN":
                    getMyBalance().setMXN(getMyBalance().getMXN() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "TWD":
                    getMyBalance().setTWD(getMyBalance().getTWD() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "NZD":
                    getMyBalance().setNZD(getMyBalance().getNZD() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "NOK":
                    getMyBalance().setNOK(getMyBalance().getNOK() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "PHP":
                    getMyBalance().setPHP(getMyBalance().getPHP() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "PLN":
                    getMyBalance().setPLN(getMyBalance().getPLN() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "GBP":
                    getMyBalance().setGBP(getMyBalance().getGBP() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "SGD":
                    getMyBalance().setSGD(getMyBalance().getSGD() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "SEK":
                    getMyBalance().setSEK(getMyBalance().getSEK() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "CHF":
                    getMyBalance().setCHF(getMyBalance().getCHF() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "THB":
                    getMyBalance().setTHB(getMyBalance().getTHB() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "USD":
                    getMyBalance().setUSD(getMyBalance().getUSD() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                default:
                    return new ConvertResponse("Currency is not selected!");
            }
            return new ConvertResponse("Convert from " + convertData.baseCurrency() + " to "
                    + convertData.targetCurrency() + " is successed!");
        } else {
            return new ConvertResponse("Currency for convert is not selected!");
        }

    }

    public TransferResponse addBalance(BigDecimal summa, String currency) {

        switch (currency) {
            case "AUD":
                getMyBalance().setAUD(new BigDecimal(getMyBalance().getAUD()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "BRL":
                getMyBalance().setBRL(new BigDecimal(getMyBalance().getBRL()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "CAD":
                getMyBalance().setCAD(new BigDecimal(getMyBalance().getCAD()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "CNY":
                getMyBalance().setCNY(new BigDecimal(getMyBalance().getCNY()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "CZK":
                getMyBalance().setCZK(new BigDecimal(getMyBalance().getCZK()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "DKK":
                getMyBalance().setDKK(new BigDecimal(getMyBalance().getDKK()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "EUR":
                getMyBalance().setEUR(new BigDecimal(getMyBalance().getEUR()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "HKD":
                getMyBalance().setHKD(new BigDecimal(getMyBalance().getHKD()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "HUF":
                getMyBalance().setHUF(new BigDecimal(getMyBalance().getHUF()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "ILS":
                getMyBalance().setILS(new BigDecimal(getMyBalance().getILS()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "JPY":
                getMyBalance().setJPY(new BigDecimal(getMyBalance().getJPY()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "MYR":
                getMyBalance().setMYR(new BigDecimal(getMyBalance().getMYR()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "MXN":
                getMyBalance().setMXN(new BigDecimal(getMyBalance().getMXN()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "TWD":
                getMyBalance().setTWD(new BigDecimal(getMyBalance().getTWD()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "NZD":
                getMyBalance().setNZD(new BigDecimal(getMyBalance().getNZD()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "NOK":
                getMyBalance().setNOK(new BigDecimal(getMyBalance().getNOK()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "PHP":
                getMyBalance().setPHP(new BigDecimal(getMyBalance().getPHP()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "PLN":
                getMyBalance().setPLN(new BigDecimal(getMyBalance().getPLN()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "GBP":
                getMyBalance().setGBP(new BigDecimal(getMyBalance().getGBP()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "SGD":
                getMyBalance().setSGD(new BigDecimal(getMyBalance().getSGD()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "SEK":
                getMyBalance().setSEK(new BigDecimal(getMyBalance().getSEK()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "CHF":
                getMyBalance().setCHF(new BigDecimal(getMyBalance().getCHF()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "THB":
                getMyBalance().setTHB(new BigDecimal(getMyBalance().getTHB()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "USD":
                getMyBalance().setUSD(new BigDecimal(getMyBalance().getUSD()).add(summa).setScale(2, RoundingMode.DOWN)
                        .doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            default:
                return new TransferResponse("Currency is not selected!");
        }

        return new TransferResponse("Balance for " + currency + " added!");
    }

    public List<BalanceHistory> getHistory(Integer userId) {
        if (balanceHistoryRepository.findByUserId(userId) != null) {
            return balanceHistoryRepository.findByUserId(userId);
        } else {
            return null;
        }
    }

}
