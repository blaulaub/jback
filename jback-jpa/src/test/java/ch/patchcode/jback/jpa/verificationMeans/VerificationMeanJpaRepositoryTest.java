package ch.patchcode.jback.jpa.verificationMeans;

import ch.patchcode.jback.jpa.JpaTestConfiguration;
import ch.patchcode.jback.jpa.personalAuthentications.PersonalAuthenticationJpa;
import ch.patchcode.jback.jpa.personalAuthentications.PersonalAuthenticationJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaTestConfiguration.class})
class VerificationMeanJpaRepositoryTest {

    private final VerificationMeanJpaRepository verificationMeanRepository;

    // not under test, but we also need these for related entities
    private final PersonalAuthenticationJpaRepository principalRepository;

    @Autowired
    public VerificationMeanJpaRepositoryTest(
            VerificationMeanJpaRepository verificationMeanRepository,
            PersonalAuthenticationJpaRepository principalRepository
    ) {
        this.verificationMeanRepository = verificationMeanRepository;
        this.principalRepository = principalRepository;
    }

    @Test
    @Transactional
    void save_and_findById() {

        // arrange
        var principal = principalRepository.save(new PersonalAuthenticationJpa());
        var smsPhoneNumber = "+491806672255";

        // act
        var id = verificationMeanRepository.save(smsVerificationOf(smsPhoneNumber, principal)).getId();
        var verificationMean = verificationMeanRepository.findById(id);

        // assert
        assertTrue(verificationMean.isPresent());
        assertEquals(principal, verificationMean.get().getPersonalAuthentication());
        assertTrue(verificationMean.get() instanceof VerificationMeanJpa.SmsVerification);
        assertEquals(smsPhoneNumber, ((VerificationMeanJpa.SmsVerification) verificationMean.get()).getPhoneNumber());
    }

    private VerificationMeanJpa.SmsVerification smsVerificationOf(String phoneNumber, PersonalAuthenticationJpa principal) {
        var smsVerification = new VerificationMeanJpa.SmsVerification();
        smsVerification.setPhoneNumber(phoneNumber);
        smsVerification.setPersonalAuthentication(principal);
        return smsVerification;
    }
}
