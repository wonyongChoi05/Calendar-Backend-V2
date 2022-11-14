package com.calendar.core.service;

import com.calendar.core.domain.entity.User;
import com.calendar.core.domain.entity.repository.UserRepository;
import com.calendar.core.dto.UserCreateReq;
import com.calendar.core.util.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Encryptor encryptor;

    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateReq userCreateReq) {
        if (userRepository.findByEmail(userCreateReq.getEmail()).isPresent()) {
            throw new RuntimeException("user already existed");
        }

        return userRepository.save(new User(
                userCreateReq.getName(),
                userCreateReq.getEmail(),
                encryptor.encrypt(userCreateReq.getPassword()),
                userCreateReq.getBirthday()
        ));
    }

    @Transactional
    public Optional<User> findPwMatchUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.isMatch(encryptor, password) ? user : null);
    }
}
