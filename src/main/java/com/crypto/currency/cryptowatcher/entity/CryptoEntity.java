package com.crypto.currency.cryptowatcher.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "crypto")
public class CryptoEntity {

    @Id
    @Column(name = "crypto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cryptoId;

    @Column(name = "code")
    private String code;

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(mappedBy = "crypto", fetch = FetchType.EAGER)
    private List<UserEntity> users;
}
