package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.core.registration.VerificationMean;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ToRegistrationConverterTest {

    private final ToRegistrationConverter converter = new ToRegistrationConverter();

    @Test
    void convert_verificationByConsole_works() {

        // arrange
        Instant expiresAt = Instant.parse("2020-08-24T05:32:00Z");
        PendingRegistration pending = new PendingRegistration.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationCode("1234")
                .setExpiresAt(expiresAt)
                .setVerificationMean(new VerificationMean.VerificationByConsole())
                .build();

        // act
        var registration = converter.convert(pending);

        // assert
        assertTrue(registration instanceof ConsoleRegistration);
        assertEquals("Tom", registration.getFirstName());
        assertEquals("Sawyer", registration.getLastName());
        assertEquals("1234", registration.getVerificationCode());
        assertEquals(expiresAt.toEpochMilli(), registration.getExpiresAt());
    }

    @Test
    void convert_verificationBySms_works() {

        // arrange
        Instant expiresAt = Instant.parse("2020-08-24T05:41:00Z");
        PendingRegistration pending = new PendingRegistration.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationCode("1234")
                .setExpiresAt(expiresAt)
                .setVerificationMean(new VerificationMean.VerificationBySms.Builder().setPhoneNumber("+41234567890").build())
                .build();

        // act
        var registration = converter.convert(pending);

        // assert
        assertTrue(registration instanceof SmsRegistration);
        assertEquals("Tom", registration.getFirstName());
        assertEquals("Sawyer", registration.getLastName());
        assertEquals("1234", registration.getVerificationCode());
        assertEquals(expiresAt.toEpochMilli(), registration.getExpiresAt());
        assertEquals("+41234567890", ((SmsRegistration)registration).getPhoneNumber());
    }

    @Test
    void convert_verificationByEmail_works() {

        // arrange
        Instant expiresAt = Instant.parse("2020-08-24T05:41:00Z");
        PendingRegistration pending = new PendingRegistration.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationCode("1234")
                .setExpiresAt(expiresAt)
                .setVerificationMean(new VerificationMean.VerificationByEmail.Builder().setEmailAddress("admin@google.com").build())
                .build();

        // act
        var registration = converter.convert(pending);

        // assert
        assertTrue(registration instanceof EmailRegistration);
        assertEquals("Tom", registration.getFirstName());
        assertEquals("Sawyer", registration.getLastName());
        assertEquals("1234", registration.getVerificationCode());
        assertEquals(expiresAt.toEpochMilli(), registration.getExpiresAt());
        assertEquals("admin@google.com", ((EmailRegistration)registration).getEmail());
    }

}