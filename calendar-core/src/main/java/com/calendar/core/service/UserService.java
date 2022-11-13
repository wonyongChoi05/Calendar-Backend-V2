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

    Encryptor bcryptEncryptor;
    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateReq req) {
        userRepository.findByEmail(req.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("cannot find user");
                });
        return userRepository.save(User.builder()
                .name(req.getName())
                .password(bcryptEncryptor.encrypt(req.getPassword()))
                .email(req.getEmail())
                .birthday(req.getBirthday())
                .build());
    }

    @Transactional
    public Optional<User> findPwMatchUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(u -> u.isMatched(bcryptEncryptor, password) ? u : null);
    }
}

