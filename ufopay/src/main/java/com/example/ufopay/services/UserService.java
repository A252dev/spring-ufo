package com.example.ufopay.services;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.ufopay.dto.ConvertRequest;
import com.example.ufopay.dto.ConvertResponse;
import com.example.ufopay.dto.LoginRequest;
import com.example.ufopay.dto.LoginResponse;
import com.example.ufopay.dto.SignUpDto;
import com.example.ufopay.dto.TransferRequest;
import com.example.ufopay.dto.TransferResponse;
import com.example.ufopay.entities.User;
import com.example.ufopay.entities.UserBalance;
import com.example.ufopay.exceptions.AppException;
import com.example.ufopay.repositories.UserBalanceRepository;
import com.example.ufopay.repositories.UserRepository;
import com.example.ufopay.services.jwt.UserServiceImpl;
import com.example.ufopay.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    // Подключение того, что нужно
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserBalanceRepository userBalanceRepository;

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userServiceImpl;
    private final JwtUtil jwtUtil;
    private UserDetails userDetails;
    private final ExchangeApi exchangeApi;

    public Integer GenerateUserId() {
        return new Random().nextInt(Integer.MAX_VALUE);
    }

    public UserBalance getMyBalance() {
        return userBalanceRepository
                .findByUserId(userRepository.findByEmail(userDetails.getUsername()).get().getUserId());
    }

    public List<Map> getUserInfo() {

        List<Map> info = new ArrayList();
        Map<String, String> userInfo = new HashMap<>();
        Map<String, Double> userBalances = new HashMap<>();

        userInfo.put("firstName", userRepository.findByEmail(userDetails.getUsername()).get().getFirstName());
        userInfo.put("secondName", userRepository.findByEmail(userDetails.getUsername()).get().getSecondName());
        userInfo.put("email", userRepository.findByEmail(userDetails.getUsername()).get().getEmail());
        userInfo.put("birthday", userRepository.findByEmail(userDetails.getUsername()).get().getBirthday());

        userBalances.put("balance_usd", getMyBalance().getBalance_usd());
        userBalances.put("balance_eur", getMyBalance().getBalance_eur());
        userBalances.put("balance_rub", getMyBalance().getBalance_rub());

        info.add(userInfo);
        info.add(userBalances);

        return info;
    }

    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest credentialsDto) {
        // Ищем человека, если нет то Unkown user
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        // Далее проверяем пароль, если нет то Invalid password
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()),
                user.getPassword())) {

            // Пробуем авторизовать кого-то. Если хрен, то либо пароль не тот, либо другая
            // хрень, которая выводится
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(credentialsDto.getEmail(),
                                credentialsDto.getPassword()));
            } catch (BadCredentialsException e) {
                throw new AppException("Incorrect email or password", HttpStatus.NOT_FOUND);
            } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Создаём детали кого-то (хотя нахуя, они и так выше подключены)

            // UserDetails userDetails;

            // Пробуем подгрузить детали
            try {
                userDetails = userServiceImpl.loadUserByUsername(credentialsDto.getEmail());
            } catch (UsernameNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Создаём токен JWY и пихаем туда ник (почта в моём случае)
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new LoginResponse(jwt));
        } else {
            throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
        }

    }

    public User register(SignUpDto signUpDto) {
        // Пытаемся найти кого-то
        Optional<User> oUser = userRepository.findByEmail(signUpDto.email());

        // Если он есть, то есть
        if (oUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        Integer createdUserId = GenerateUserId();

        // Если нет, то создаём
        User user = new User(createdUserId, signUpDto.firstName(), signUpDto.secondName(), signUpDto.birthday(),
                signUpDto.email(),
                passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));

        UserBalance userBalance = new UserBalance(createdUserId, 0.0, 0.0, 0.0);

        User savedUser = userRepository.save(user);
        userBalanceRepository.save(userBalance);
        return savedUser;
    }

    public TransferResponse transfer(TransferRequest transferData) {

        Optional<User> tUser = userRepository.findByEmail(transferData.toEmail());

        if (userDetails.getUsername() != null) {

            if (tUser.isPresent()) {

                UserBalance tUserBalance = userBalanceRepository.findByUserId(tUser.get().getUserId());

                switch (transferData.currency()) {
                    case "USD":
                        if (getMyBalance().getBalance_usd() <= 0
                                || getMyBalance().getBalance_usd() < transferData.summa()) {
                            return new TransferResponse("Not enough money");
                        } else {
                            getMyBalance().setBalance_usd(getMyBalance().getBalance_usd() - transferData.summa());
                            tUserBalance.setBalance_usd(tUserBalance.getBalance_usd() + transferData.summa());
                            break;
                        }
                    case "RUB":
                        if (getMyBalance().getBalance_rub() <= 0
                                || getMyBalance().getBalance_rub() < transferData.summa()) {
                            return new TransferResponse("Not enough money");
                        } else {
                            getMyBalance().setBalance_rub(getMyBalance().getBalance_rub() - transferData.summa());
                            tUserBalance.setBalance_rub(tUserBalance.getBalance_rub() + transferData.summa());
                            break;
                        }
                    default:
                        return new TransferResponse("Currency is not selected!");
                }

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

    public ConvertResponse setTargetBalanceCurrency(ConvertRequest convertRequest) {

        UserBalance myUserBalance = userBalanceRepository.findByUserId(getMyBalance().getUserId());

        switch (convertRequest.toCurrency()) {
            case "USD":
                // Double usdSumma = exchangeApi.getData(convertRequest.fromCurrency());
                getMyBalance().setBalance_usd(getMyBalance().getBalance_usd() + 10.0);
                userBalanceRepository.save(myUserBalance);
                break;
            case "RUB":
                // Double rubSumma = exchangeApi.getData(convertRequest.fromCurrency(), convertRequest.summa()).get("RUB");
                getMyBalance().setBalance_rub(getMyBalance().getBalance_rub() + 10.0);
                userBalanceRepository.save(myUserBalance);
                break;
            case "EUR":
                // Double eurSumma = exchangeApi.getData(convertRequest.fromCurrency(), convertRequest.summa()).get("EUR");
                getMyBalance().setBalance_eur(getMyBalance().getBalance_eur() + 10.0);
                userBalanceRepository.save(myUserBalance);
                break;
            default:
                return null;
        }
        return null;

    }

    public ConvertResponse convert(ConvertRequest convertData) {

        if (convertData != null) {
            switch (convertData.fromCurrency()) {
                case "USD":
                    getMyBalance().setBalance_usd(getMyBalance().getBalance_usd() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                case "RUB":
                    getMyBalance().setBalance_rub(getMyBalance().getBalance_rub() - convertData.summa());
                    setTargetBalanceCurrency(convertData);
                    break;
                default:
                    return new ConvertResponse("Currency is not selected!");
            }
            return new ConvertResponse("Convert from " + convertData.fromCurrency() + " to "
                    + convertData.toCurrency() + " is successed!");
        } else {
            return new ConvertResponse("Currency for convert is not selected!");
        }

    }

}
