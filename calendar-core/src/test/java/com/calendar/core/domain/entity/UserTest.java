package com.calendar.core.domain.entity;

import com.calendar.core.util.Encryptor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final Encryptor alwaysMatchEncryptor = new Encryptor() {
        @Override
        public String encrypt(String origin) {
            return origin;
        }

        @Override
        public boolean isMatch(String origin, String hashed) {
            return true;
        }
    };

    @Test
    void isMatchTest() {
        final User me = new User("name", "email", "pw", LocalDate.now());
        assertTrue(me.isMatch(alwaysMatchEncryptor, "aaaa"));
    }
}