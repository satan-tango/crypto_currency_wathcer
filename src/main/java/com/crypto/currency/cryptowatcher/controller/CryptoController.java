package com.crypto.currency.cryptowatcher.controller;

import com.crypto.currency.cryptowatcher.DAO.CryptoDao;
import com.crypto.currency.cryptowatcher.DAO.UserDao;
import com.crypto.currency.cryptowatcher.DTO.CryptoDto;
import com.crypto.currency.cryptowatcher.entity.CryptoEntity;
import com.crypto.currency.cryptowatcher.entity.UserEntity;
import com.crypto.currency.cryptowatcher.exception.APIRequestException;
import com.crypto.currency.cryptowatcher.service.CryptoCurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CryptoController {

    private final CryptoDao cryptoDao;

    private final UserDao userDao;

    private final CryptoCurrencyService cryptoCurrencyService;

    @GetMapping("api/v1/crypto")
    public List<CryptoDto> showAllCrypto() {
        List<CryptoEntity> cryptoEntities = cryptoDao.findAllCrypto();
        List<CryptoDto> cryptoDtos = cryptoEntities.stream()
                .map(entity -> CryptoDto.mapToCryptoDTO(entity))
                .collect(Collectors.toList());
        return cryptoDtos;
    }

    @GetMapping("api/v1/crypto/{code}")
    public Map<String, String> showCryptoByCode(@PathVariable("code") String code) {
        List<CryptoEntity> cryptoList = cryptoDao.findAllCrypto();
        if (cryptoList.stream().anyMatch(crypto -> crypto.getCode().equals(code))) {
            String currentPrice = cryptoCurrencyService.getCurrentCryptoPrice(code);
            CryptoEntity crypto = cryptoDao.findByCode(code);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("symbol", crypto.getSymbol());
            responseMap.put("code", crypto.getCode());
            responseMap.put("price_usd", currentPrice);
            return responseMap;
        }
        throw new APIRequestException("Crypto with this code does not exist");
    }

    @PostMapping("api/v1/user")
    public Map<String, String> createUser(@RequestBody() Map<String, String> data) {
        String name = data.get("name");
        String code = data.get("code");
        if (name != null && code != null) {
            List<CryptoEntity> cryptoList = cryptoDao.findAllCrypto();
            if (cryptoList.stream().anyMatch(crypto -> crypto.getCode().equals(code))) {
                CryptoEntity crypto = cryptoDao.findByCode(code);
                UserEntity user = new UserEntity();
                String currentPrice = cryptoCurrencyService.getCurrentCryptoPrice(code);
                user.setName(name);
                user.setCrypto(crypto);
                user.setRegistrationPrice(currentPrice);
                userDao.saveUser(user);
                Map<String, String> responseMap = new HashMap<>();
                responseMap.put("status", "ok");
                responseMap.put("description","user successfully registered");
                return responseMap;
            }
            throw new APIRequestException("Crypto with this code does not exist");
        }
        throw new APIRequestException("Incorrect data");
    }
}
