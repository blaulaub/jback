package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationMean;
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
        assertTrue(registration instanceof RegistrationJpa.ConsoleRegistrationJpa);
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
        assertTrue(registration instanceof RegistrationJpa.SmsRegistrationJpa);
        assertEquals(pending.getFirstName(), registration.getFirstName());
        assertEquals(pending.getLastName(), registration.getLastName());
        assertEquals(pending.getVerificationCode(), registration.getVerificationCode());
        assertEquals(pending.getExpiresAt().toEpochMilli(), registration.getExpiresAt());
        assertEquals("+41234567890", ((RegistrationJpa.SmsRegistrationJpa)registration).getPhoneNumber());
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
        assertTrue(registration instanceof RegistrationJpa.EmailRegistrationJpa);
        assertEquals(pending.getFirstName(), registration.getFirstName());
        assertEquals(pending.getLastName(), registration.getLastName());
        assertEquals(pending.getVerificationCode(), registration.getVerificationCode());
        assertEquals(pending.getExpiresAt().toEpochMilli(), registration.getExpiresAt());
        assertEquals("admin@google.com", ((RegistrationJpa.EmailRegistrationJpa)registration).getEmail());
    }
}
