package ch.patchcode.jback.security.verificationCodes.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FourDigitCodeProviderTest {

    private FourDigitCodeProvider provider;

    @BeforeEach
    void setUp() {

        provider = new FourDigitCodeProvider(new Random());
    }

    @Test
    void generateRandomCode_severalTimes_allMatchPattern() {

        for (int i = 0; i < 100; i++) {
            assertTrue(provider.generateRandomCode().matches("\\d{4}"));
        }
    }
}
