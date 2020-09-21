package ch.patchcode.jback.jpa.entities;

import ch.patchcode.jback.security.entities.VerificationMean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerificationMeanJpaTest {

    @Test
    void verificationByConsole_fromDomainToDomain_restoresObject() {

        // arrange
        PersonalAuthenticationJpa principal = new PersonalAuthenticationJpa();
        VerificationMean mean = new VerificationMean.VerificationByConsole();

        // act
        VerificationMeanJpa entity = VerificationMeanJpa.fromDomain(principal, mean);
        var result = entity.toDomain();

        // assert
        assertTrue(result instanceof VerificationMean.VerificationByConsole);
    }

    @Test
    void verificationBySms_fromDomainToDomain_restoresObject() {

        // arrange
        PersonalAuthenticationJpa principal = new PersonalAuthenticationJpa();
        VerificationMean mean = new VerificationMean.VerificationBySms.Builder()
                .setPhoneNumber("+41234")
                .build();

        // act
        VerificationMeanJpa entity = VerificationMeanJpa.fromDomain(principal, mean);
        var result = entity.toDomain();

        // assert
        assertTrue(result instanceof VerificationMean.VerificationBySms);
        assertEquals("+41234", ((VerificationMean.VerificationBySms)result).getPhoneNumber());
    }

    @Test
    void verificationByEmail_fromDomainToDomain_restoresObject() {

        // arrange
        PersonalAuthenticationJpa principal = new PersonalAuthenticationJpa();
        VerificationMean mean = new VerificationMean.VerificationByEmail.Builder()
                .setEmailAddress("otto@home.de")
                .build();

        // act
        VerificationMeanJpa entity = VerificationMeanJpa.fromDomain(principal, mean);
        var result = entity.toDomain();

        // assert
        assertTrue(result instanceof VerificationMean.VerificationByEmail);
        assertEquals("otto@home.de", ((VerificationMean.VerificationByEmail)result).getEmailAddress());
    }
}