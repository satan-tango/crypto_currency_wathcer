package com.crypto.currency.cryptowatcher.service;

import com.crypto.currency.cryptowatcher.DAO.CryptoDAO;
import com.crypto.currency.cryptowatcher.entity.CryptoEntity;
import com.crypto.currency.cryptowatcher.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;

@EnableScheduling
@Service
@AllArgsConstructor
@Slf4j
public class CryptoCurrencyChangesService {

    private final CryptoDAO cryptoDAO;

    private final CryptoCurrencyService cryptoCurrencyService;


    @Scheduled(fixedRate = 60000)
    private void cryptoCurrencyChangesService() {
        List<CryptoEntity> cryptoList = cryptoDAO.findAllCrypto();
        for (CryptoEntity crypto : cryptoList) {
            List<UserEntity> users = crypto.getUsers();
            if (users != null) {
                String currentPrice = cryptoCurrencyService.getCurrentCryptoPrice(crypto.getCode());
                for (UserEntity user : users) {
                    if (currentPrice == null) {
                        continue;
                    }
                    double changes = Math.abs((Double.parseDouble(currentPrice) - Double.parseDouble(user.getRegistrationPrice())) / Double.parseDouble(user.getRegistrationPrice()) * 100);
                    if (changes > 1) {
                        log.warn("\nWarning:\n" + "name: " + user.getName()
                                + "\n" + "crypto code: " + crypto.getCode() + "\n" +
                                "price changes in %: " + String.format("%.2f", changes));
                    }
                }
            }
        }
    }
}
