package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.core.registration.VerificationMean;
import org.junit.jupiter.api.Test;

import static ch.patchcode.jback.jpa.util.SomeData.somePendingRegistration;
import static org.junit.jupiter.api.Assertions.*;

class ToRegistrationConverterTest {

    private final ToRegistrationConverter converter = new ToRegistrationConverter();

    @Test
    void convert_verificationByConsole_works() {

        // arrange
        PendingRegistration pending = somePendingRegistration(new VerificationMean.VerificationByConsole());

        // act
        var registration = converter.convert(pending);

        // assert
        assertTrue(registration instanceof Registration.ConsoleRegistration);
        assertEquals(pending.getFirstName(), registration.getFirstName());
        assertEquals(pending.getLastName(), registration.getLastName());
        assertEquals(pending.getVerificationCode(), registration.getVerificationCode());
        assertEquals(pending.getExpiresAt().toEpochMilli(), registration.getExpiresAt());
    }

    @Test
    void convert_verificationBySms_works() {

        // arrange
        var pending = somePendingRegistration(
                new VerificationMean.VerificationBySms.Builder().setPhoneNumber("+41234567890").build()
        );

        // act
        var registration = converter.convert(pending);

        // assert
        assertTrue(registration instanceof Registration.SmsRegistration);
        assertEquals(pending.getFirstName(), registration.getFirstName());
        assertEquals(pending.getLastName(), registration.getLastName());
        assertEquals(pending.getVerificationCode(), registration.getVerificationCode());
        assertEquals(pending.getExpiresAt().toEpochMilli(), registration.getExpiresAt());
        assertEquals("+41234567890", ((Registration.SmsRegistration)registration).getPhoneNumber());
    }

    @Test
    void convert_verificationByEmail_works() {

        // arrange
        var pending = somePendingRegistration(
                new VerificationMean.VerificationByEmail.Builder().setEmailAddress("admin@google.com").build()
        );

        // act
        var registration = converter.convert(pending);

        // assert
        assertTrue(registration instanceof Registration.EmailRegistration);
        assertEquals(pending.getFirstName(), registration.getFirstName());
        assertEquals(pending.getLastName(), registration.getLastName());
        assertEquals(pending.getVerificationCode(), registration.getVerificationCode());
        assertEquals(pending.getExpiresAt().toEpochMilli(), registration.getExpiresAt());
        assertEquals("admin@google.com", ((Registration.EmailRegistration)registration).getEmail());
    }
}
