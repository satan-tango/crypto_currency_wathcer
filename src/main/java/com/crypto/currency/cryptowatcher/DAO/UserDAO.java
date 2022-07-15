package com.crypto.currency.cryptowatcher.DAO;

import com.crypto.currency.cryptowatcher.entity.UserEntity;
import com.crypto.currency.cryptowatcher.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDAO {

    private final UserRepository userRepository;

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }
}
