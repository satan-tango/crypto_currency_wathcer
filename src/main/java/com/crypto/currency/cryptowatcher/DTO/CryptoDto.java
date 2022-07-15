package com.crypto.currency.cryptowatcher.DTO;

import com.crypto.currency.cryptowatcher.entity.CryptoEntity;
import lombok.*;

@Builder
@Data
public class CryptoDto {

    private String code;

    private String symbol;

    public static CryptoDto mapToCryptoDTO(CryptoEntity cryptoEntity) {
        CryptoDto cryptoDto = CryptoDto.builder()
                .code(cryptoEntity.getCode())
                .symbol(cryptoEntity.getSymbol())
                .build();
        return cryptoDto;
    }

    public static CryptoEntity mapToCryptoEntity(CryptoDto cryptoDto) {
        CryptoEntity cryptoEntity = new CryptoEntity();
        cryptoEntity.setCode(cryptoDto.getCode());
        cryptoEntity.setSymbol(cryptoDto.getSymbol());
        return cryptoEntity;
    }
}
