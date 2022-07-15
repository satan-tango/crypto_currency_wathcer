package com.crypto.currency.cryptowatcher.DTO;

import com.crypto.currency.cryptowatcher.entity.CryptoEntity;
import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@Data
public class CryptoDTO {

    private String code;

    private String symbol;

    public static CryptoDTO mapToCryptoDTO(CryptoEntity cryptoEntity) {
        CryptoDTO cryptoDTO = CryptoDTO.builder()
                .code(cryptoEntity.getCode())
                .symbol(cryptoEntity.getSymbol())
                .build();
        return cryptoDTO;
    }

    public static CryptoEntity mapToCryptoEntity(CryptoDTO cryptoDTO) {
        CryptoEntity cryptoEntity = new CryptoEntity();
        cryptoEntity.setCode(cryptoDTO.getCode());
        cryptoEntity.setSymbol(cryptoDTO.getSymbol());
        return cryptoEntity;
    }
}
