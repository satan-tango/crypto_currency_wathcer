package com.crypto.currency.cryptowatcher.DAO;

import com.crypto.currency.cryptowatcher.entity.CryptoEntity;
import com.crypto.currency.cryptowatcher.repository.CryptoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CryptoDao {

    private final CryptoRepository cryptoRepository;

    public List<CryptoEntity> findAllCrypto() {
        return cryptoRepository.findAll();
    }

    public CryptoEntity findByCode(String code) {
        return cryptoRepository.findByCode(code);
    }
}
