package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.jpa.util.SomeData;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationMean;
import org.junit.jupiter.api.Test;

import static ch.patchcode.jback.jpa.util.SomeData.somePendingRegistrationDraft;
import static org.junit.jupiter.api.Assertions.*;

class FromDomainConverterTest {

    private final FromDomainConverter converter = new FromDomainConverter();

    @Test
    void verificationByConsole_fromDomain_works() {

        // arrange
        PendingRegistration.Draft pending = SomeData.somePendingRegistrationDraft(new VerificationMean.VerificationByConsole());

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
    void verificationBySms_fromDomain_works() {

        // arrange
        var pending = SomeData.somePendingRegistrationDraft(
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
    void verificationByEmail_fromDomain_works() {

        // arrange
        var pending = SomeData.somePendingRegistrationDraft(
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
