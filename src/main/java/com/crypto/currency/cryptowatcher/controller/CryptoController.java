package com.crypto.currency.cryptowatcher.controller;

import com.crypto.currency.cryptowatcher.DAO.CryptoDAO;
import com.crypto.currency.cryptowatcher.DAO.UserDAO;
import com.crypto.currency.cryptowatcher.DTO.CryptoDTO;
import com.crypto.currency.cryptowatcher.entity.CryptoEntity;
import com.crypto.currency.cryptowatcher.entity.UserEntity;
import com.crypto.currency.cryptowatcher.exception.APIRequestException;
import com.crypto.currency.cryptowatcher.service.CryptoCurrencyService;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CryptoController {

    private final CryptoDAO cryptoDAO;

    private final UserDAO userDAO;

    private final CryptoCurrencyService cryptoCurrencyService;

    @GetMapping("/crypto")
    public List<CryptoDTO> showAllCrypto() {
        List<CryptoEntity> cryptoEntities = cryptoDAO.findAllCrypto();
        List<CryptoDTO> cryptoDTOs = cryptoEntities.stream()
                .map(entity -> CryptoDTO.mapToCryptoDTO(entity))
                .collect(Collectors.toList());
        return cryptoDTOs;
    }

    @GetMapping("/crypto/{code}")
    public Map<String, String> showCryptoByCode(@PathVariable("code") String code) {
        List<CryptoEntity> cryptoList = cryptoDAO.findAllCrypto();
        if (cryptoList.stream().anyMatch(crypto -> crypto.getCode().equals(code))) {
            String currentPrice = cryptoCurrencyService.getCurrentCryptoPrice(code);
            CryptoEntity crypto = cryptoDAO.findByCode(code);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("symbol", crypto.getSymbol());
            responseMap.put("code", crypto.getCode());
            responseMap.put("price_usd", currentPrice);
            return responseMap;
        }
        throw new APIRequestException("Crypto with this code does not exist");
    }

    @PostMapping("/user/create")
    public Map<String, String> createUser(@RequestBody() Map<String, String> data) {
        String name = data.get("name");
        String code = data.get("code");
        if (name != null && code != null) {
            List<CryptoEntity> cryptoList = cryptoDAO.findAllCrypto();
            if (cryptoList.stream().anyMatch(crypto -> crypto.getCode().equals(code))) {
                CryptoEntity crypto = cryptoDAO.findByCode(code);
                UserEntity user = new UserEntity();
                String currentPrice = cryptoCurrencyService.getCurrentCryptoPrice(code);
                user.setName(name);
                user.setCrypto(crypto);
                user.setRegistrationPrice(currentPrice);
                userDAO.saveUser(user);
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
