package ch.patchcode.jback.core.verificationCodes.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FourDigitCodeProviderTest {

    final private FourDigitCodeProvider provider = new FourDigitCodeProvider();

    @Test
    void generateRandomCode_severalTimes_allMatchPattern() {

        for (int i = 0; i < 100; i++) {
            assertTrue(provider.generateRandomCode().matches("\\d{4}"));
        }
    }
}
