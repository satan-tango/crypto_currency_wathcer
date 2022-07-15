package com.crypto.currency.cryptowatcher.repository;

import com.crypto.currency.cryptowatcher.entity.CryptoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<CryptoEntity, Long> {
    CryptoEntity findBySymbol(String symbol);

    CryptoEntity findByCode(String code);
}
