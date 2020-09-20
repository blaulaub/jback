package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.jpa.util.SomeData;
import ch.patchcode.jback.security.secBaseImpl.PendingRegistration;
import ch.patchcode.jback.security.entities.VerificationMean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationJpaTest {

    @Test
    void verificationByConsole_fromDomain_works() {

        // arrange
        PendingRegistration.Draft pending = SomeData.somePendingRegistrationDraft(new VerificationMean.VerificationByConsole());

        // act
        var registration = RegistrationJpa.fromDomain(pending);

        // assert
        assertTrue(registration instanceof RegistrationJpa.ConsoleRegistrationJpa);
        assertAll(
                () -> assertEquals(pending.getFirstName(), registration.getFirstName()),
                () -> assertEquals(pending.getLastName(), registration.getLastName()),
                () -> assertEquals(pending.getVerificationCode(), registration.getVerificationCode()),
                () -> assertEquals(pending.getExpiresAt().toEpochMilli(), registration.getExpiresAt())
        );
    }

    @Test
    void verificationBySms_fromDomain_works() {

        // arrange
        var pending = SomeData.somePendingRegistrationDraft(
                new VerificationMean.VerificationBySms.Builder().setPhoneNumber("+41234567890").build()
        );

        // act
        var registration = RegistrationJpa.fromDomain(pending);

        // assert
        assertTrue(registration instanceof RegistrationJpa.SmsRegistrationJpa);
        assertAll(
                () -> assertEquals(pending.getFirstName(), registration.getFirstName()),
                () -> assertEquals(pending.getLastName(), registration.getLastName()),
                () -> assertEquals(pending.getVerificationCode(), registration.getVerificationCode()),
                () -> assertEquals(pending.getExpiresAt().toEpochMilli(), registration.getExpiresAt()),
                () -> assertEquals("+41234567890", ((RegistrationJpa.SmsRegistrationJpa) registration).getPhoneNumber())
        );
    }

    @Test
    void verificationByEmail_fromDomain_works() {

        // arrange
        var pending = SomeData.somePendingRegistrationDraft(
                new VerificationMean.VerificationByEmail.Builder().setEmailAddress("admin@google.com").build()
        );

        // act
        var registration = RegistrationJpa.fromDomain(pending);

        // assert
        assertTrue(registration instanceof RegistrationJpa.EmailRegistrationJpa);
        assertAll(
                () -> assertEquals(pending.getFirstName(), registration.getFirstName()),
                () -> assertEquals(pending.getLastName(), registration.getLastName()),
                () -> assertEquals(pending.getVerificationCode(), registration.getVerificationCode()),
                () -> assertEquals(pending.getExpiresAt().toEpochMilli(), registration.getExpiresAt()),
                () -> assertEquals("admin@google.com", ((RegistrationJpa.EmailRegistrationJpa) registration).getEmail())
        );
    }
}
