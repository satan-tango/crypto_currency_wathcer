package com.crypto.currency.cryptowatcher.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Users")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name")
    private String name;


    @Column(name = "registration_price")
    private String registrationPrice;

    @ManyToOne
    @JoinColumn(name = "crypto_id")
    private CryptoEntity crypto;
}
