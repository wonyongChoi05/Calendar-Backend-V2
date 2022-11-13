package com.calendar.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BCryptEncryptTest {

    @Test
    void test() {
        final String origin = "password";
        final Encryptor encryptor = new BCryptEncryptor();

        final String hash = encryptor.encrypt(origin);

        // True
        assertTrue(encryptor.isMatch(origin, hash));

        // False
        final String origin2 = "password2";
        assertFalse(encryptor.isMatch(origin2, hash));
    }

}