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
                            getMyBalance().setAUD(new BigDecimal(getMyBalance().getAUD())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setAUD(new BigDecimal(tUserBalance.getAUD())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "BRL":
                        if (getMyBalance().getBRL() <= 0
                                || getMyBalance().getBRL() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setBRL(new BigDecimal(getMyBalance().getBRL())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setBRL(new BigDecimal(tUserBalance.getBRL())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "CAD":
                        if (getMyBalance().getCAD() <= 0
                                || getMyBalance().getCAD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setCAD(new BigDecimal(getMyBalance().getCAD())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setCAD(new BigDecimal(tUserBalance.getCAD())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "CNY":
                        if (getMyBalance().getCNY() <= 0
                                || getMyBalance().getCNY() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setCNY(new BigDecimal(getMyBalance().getCNY())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setCNY(new BigDecimal(tUserBalance.getCNY())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "CZK":
                        if (getMyBalance().getCZK() <= 0
                                || getMyBalance().getCZK() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setCZK(new BigDecimal(getMyBalance().getCZK())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setUSD(new BigDecimal(tUserBalance.getUSD())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "DKK":
                        if (getMyBalance().getDKK() <= 0
                                || getMyBalance().getDKK() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setDKK(new BigDecimal(getMyBalance().getDKK())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setDKK(new BigDecimal(tUserBalance.getDKK())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "EUR":
                        if (getMyBalance().getEUR() <= 0
                                || getMyBalance().getEUR() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setEUR(new BigDecimal(getMyBalance().getEUR())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setEUR(new BigDecimal(tUserBalance.getEUR())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "HKD":
                        if (getMyBalance().getHKD() <= 0
                                || getMyBalance().getHKD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setHKD(new BigDecimal(getMyBalance().getHKD())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setHKD(new BigDecimal(tUserBalance.getHKD())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "HUF":
                        if (getMyBalance().getHUF() <= 0
                                || getMyBalance().getHUF() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setHUF(new BigDecimal(getMyBalance().getHUF())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setHUF(new BigDecimal(tUserBalance.getHUF())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "ILS":
                        if (getMyBalance().getILS() <= 0
                                || getMyBalance().getILS() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setILS(new BigDecimal(getMyBalance().getILS())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setILS(new BigDecimal(tUserBalance.getILS())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "JPY":
                        if (getMyBalance().getJPY() <= 0
                                || getMyBalance().getJPY() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setJPY(new BigDecimal(getMyBalance().getJPY())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setJPY(new BigDecimal(tUserBalance.getJPY())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "MYR":
                        if (getMyBalance().getMYR() <= 0
                                || getMyBalance().getMYR() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setMYR(new BigDecimal(getMyBalance().getMYR())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setMYR(new BigDecimal(tUserBalance.getMYR())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "MXN":
                        if (getMyBalance().getMXN() <= 0
                                || getMyBalance().getMXN() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setMXN(new BigDecimal(getMyBalance().getMXN())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setMXN(new BigDecimal(tUserBalance.getMXN())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "TWD":
                        if (getMyBalance().getTWD() <= 0
                                || getMyBalance().getTWD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setTWD(new BigDecimal(getMyBalance().getTWD())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setTWD(new BigDecimal(tUserBalance.getTWD())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "NZD":
                        if (getMyBalance().getNZD() <= 0
                                || getMyBalance().getNZD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setNZD(new BigDecimal(getMyBalance().getNZD())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setNZD(new BigDecimal(tUserBalance.getNZD())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "NOK":
                        if (getMyBalance().getNOK() <= 0
                                || getMyBalance().getNOK() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setNOK(new BigDecimal(getMyBalance().getNOK())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setNOK(new BigDecimal(tUserBalance.getNOK())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "PHP":
                        if (getMyBalance().getPHP() <= 0
                                || getMyBalance().getPHP() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setPHP(new BigDecimal(getMyBalance().getPHP())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setPHP(new BigDecimal(tUserBalance.getPHP())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "PLN":
                        if (getMyBalance().getPLN() <= 0
                                || getMyBalance().getPLN() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setPLN(new BigDecimal(getMyBalance().getPLN())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setPLN(new BigDecimal(tUserBalance.getPLN())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "GBP":
                        if (getMyBalance().getGBP() <= 0
                                || getMyBalance().getGBP() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setGBP(new BigDecimal(getMyBalance().getGBP())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setGBP(new BigDecimal(tUserBalance.getGBP())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "SGD":
                        if (getMyBalance().getSGD() <= 0
                                || getMyBalance().getSGD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setSGD(new BigDecimal(getMyBalance().getSGD())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setSGD(new BigDecimal(tUserBalance.getSGD())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "SEK":
                        if (getMyBalance().getSEK() <= 0
                                || getMyBalance().getSEK() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setSEK(new BigDecimal(getMyBalance().getSEK())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setSEK(new BigDecimal(tUserBalance.getSEK())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "CHF":
                        if (getMyBalance().getCHF() <= 0
                                || getMyBalance().getCHF() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setCHF(new BigDecimal(getMyBalance().getCHF())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setCHF(new BigDecimal(tUserBalance.getCHF())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "THB":
                        if (getMyBalance().getTHB() <= 0
                                || getMyBalance().getTHB() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setTHB(new BigDecimal(getMyBalance().getTHB())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setTHB(new BigDecimal(tUserBalance.getTHB())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
                            break;
                        }
                    case "USD":
                        if (getMyBalance().getUSD() <= 0
                                || getMyBalance().getUSD() < transferData.summa()) {
                            return new TransferResponse(errorResponse);
                        } else {
                            getMyBalance().setUSD(new BigDecimal(getMyBalance().getUSD())
                                    .subtract(new BigDecimal(transferData.summa())).doubleValue());
                            tUserBalance.setUSD(new BigDecimal(tUserBalance.getUSD())
                                    .add(new BigDecimal(transferData.summa())).doubleValue());
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
                        .setAUD(new BigDecimal(getMyBalance().getAUD())
                                .add(new BigDecimal(Math.floor(AUD * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "BRL":
                Double BRL = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setBRL(new BigDecimal(getMyBalance().getBRL())
                                .add(new BigDecimal(Math.floor(BRL * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "CAD":
                Double CAD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setCAD(new BigDecimal(getMyBalance().getCAD())
                                .add(new BigDecimal(Math.floor(CAD * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "CNY":
                Double CNY = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setCNY(new BigDecimal(getMyBalance().getCNY())
                                .add(new BigDecimal(Math.floor(CNY * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "CZK":
                Double CZK = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setCZK(new BigDecimal(getMyBalance().getCZK())
                                .add(new BigDecimal(Math.floor(CZK * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "DKK":
                Double DKK = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setDKK(new BigDecimal(getMyBalance().getDKK())
                                .add(new BigDecimal(Math.floor(DKK * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "EUR":
                Double EUR = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setEUR(new BigDecimal(getMyBalance().getEUR())
                                .add(new BigDecimal(Math.floor(EUR * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "HKD":
                Double HKD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setHKD(new BigDecimal(getMyBalance().getHKD())
                                .add(new BigDecimal(Math.floor(HKD * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "HUF":
                Double HUF = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setHUF(new BigDecimal(getMyBalance().getHUF())
                                .add(new BigDecimal(Math.floor(HUF * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "ILS":
                Double ILS = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setILS(new BigDecimal(getMyBalance().getILS())
                                .add(new BigDecimal(Math.floor(ILS * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "JPY":
                Double JPY = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setJPY(new BigDecimal(getMyBalance().getJPY())
                                .add(new BigDecimal(Math.floor(JPY * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "MYR":
                Double MYR = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setMYR(new BigDecimal(getMyBalance().getMYR())
                                .add(new BigDecimal(Math.floor(MYR * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "MXN":
                Double MXN = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setMXN(new BigDecimal(getMyBalance().getMXN())
                                .add(new BigDecimal(Math.floor(MXN * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "TWD":
                Double TWD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setTWD(new BigDecimal(getMyBalance().getTWD())
                                .add(new BigDecimal(Math.floor(TWD * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "NZD":
                Double NZD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setNZD(new BigDecimal(getMyBalance().getNZD())
                                .add(new BigDecimal(Math.floor(NZD * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "NOK":
                Double NOK = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setNOK(new BigDecimal(getMyBalance().getNOK())
                                .add(new BigDecimal(Math.floor(NOK * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "PHP":
                Double PHP = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setPHP(new BigDecimal(getMyBalance().getPHP())
                                .add(new BigDecimal(Math.floor(PHP * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "PLN":
                Double PLN = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setPLN(new BigDecimal(getMyBalance().getPLN())
                                .add(new BigDecimal(Math.floor(PLN * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "GBP":
                Double GBP = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setGBP(new BigDecimal(getMyBalance().getGBP())
                                .add(new BigDecimal(Math.floor(GBP * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "SGD":
                Double SGD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setSGD(new BigDecimal(getMyBalance().getSGD())
                                .add(new BigDecimal(Math.floor(SGD * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "SEK":
                Double SEK = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setSEK(new BigDecimal(getMyBalance().getSEK())
                                .add(new BigDecimal(Math.floor(SEK * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "CHF":
                Double CHF = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setCHF(new BigDecimal(getMyBalance().getCHF())
                                .add(new BigDecimal(Math.floor(CHF * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "THB":
                Double THB = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setTHB(new BigDecimal(getMyBalance().getTHB())
                                .add(new BigDecimal(Math.floor(THB * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
                userBalanceRepository.save(getMyBalance());
                break;
            case "USD":
                Double USD = this.getSummaFromBaseCurrency(convertRequest)
                        .getDouble(convertRequest.targetCurrency());

                getMyBalance()
                        .setUSD(new BigDecimal(getMyBalance().getUSD())
                                .add(new BigDecimal(Math.floor(USD * convertRequest.summa() * 100) / 100))
                                .setScale(2, RoundingMode.DOWN).doubleValue());
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
                    getMyBalance().setAUD(new BigDecimal(getMyBalance().getAUD())
                            .subtract(new BigDecimal(convertData.summa())).setScale(2, RoundingMode.DOWN)
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "BRL":
                    getMyBalance().setBRL(new BigDecimal(getMyBalance().getBRL())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "CAD":
                    getMyBalance().setCAD(new BigDecimal(getMyBalance().getCAD())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "CNY":
                    getMyBalance().setCNY(new BigDecimal(getMyBalance().getCNY())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "CZK":
                    getMyBalance().setCZK(new BigDecimal(getMyBalance().getCZK())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "DKK":
                    getMyBalance().setDKK(new BigDecimal(getMyBalance().getDKK())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "EUR":
                    getMyBalance().setEUR(new BigDecimal(getMyBalance().getEUR())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "HKD":
                    getMyBalance().setHKD(new BigDecimal(getMyBalance().getHKD())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "HUF":
                    getMyBalance().setHUF(new BigDecimal(getMyBalance().getHUF())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "ILS":
                    getMyBalance().setILS(new BigDecimal(getMyBalance().getILS())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "JPY":
                    getMyBalance().setJPY(new BigDecimal(getMyBalance().getJPY())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "MYR":
                    getMyBalance().setMYR(new BigDecimal(getMyBalance().getMYR())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "MXN":
                    getMyBalance().setMXN(new BigDecimal(getMyBalance().getMXN())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "TWD":
                    getMyBalance().setTWD(new BigDecimal(getMyBalance().getTWD())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "NZD":
                    getMyBalance().setNZD(new BigDecimal(getMyBalance().getNZD())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "NOK":
                    getMyBalance().setNOK(new BigDecimal(getMyBalance().getNOK())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "PHP":
                    getMyBalance().setPHP(new BigDecimal(getMyBalance().getPHP())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "PLN":
                    getMyBalance().setPLN(new BigDecimal(getMyBalance().getPLN())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "GBP":
                    getMyBalance().setGBP(new BigDecimal(getMyBalance().getGBP())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "SGD":
                    getMyBalance().setSGD(new BigDecimal(getMyBalance().getSGD())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "SEK":
                    getMyBalance().setSEK(new BigDecimal(getMyBalance().getSEK())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "CHF":
                    getMyBalance().setCHF(new BigDecimal(getMyBalance().getCHF())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "THB":
                    getMyBalance().setTHB(new BigDecimal(getMyBalance().getTHB())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "USD":
                    getMyBalance().setUSD(new BigDecimal(getMyBalance().getUSD())
                            .subtract(new BigDecimal(convertData.summa()).setScale(2, RoundingMode.DOWN))
                            .doubleValue());
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
