package ch.patchcode.jback.jpa.verificationMeans;

import ch.patchcode.jback.jpa.JpaTestConfiguration;
import ch.patchcode.jback.jpa.personalAuthentications.PersonalAuthenticationJpa;
import ch.patchcode.jback.jpa.personalAuthentications.PersonalAuthenticationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
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
    private final PersonalAuthenticationJpaRepository personalAuthenticationJpaRepository;

    private PersonalAuthenticationJpa principal;

    @Autowired
    public VerificationMeanJpaRepositoryTest(
            VerificationMeanJpaRepository verificationMeanRepository,
            PersonalAuthenticationJpaRepository personalAuthenticationJpaRepository) {

        this.verificationMeanRepository = verificationMeanRepository;
        this.personalAuthenticationJpaRepository = personalAuthenticationJpaRepository;
    }

    @BeforeEach
    void setUp() {

        principal = personalAuthenticationJpaRepository.save(new PersonalAuthenticationJpa());
    }

    @Test
    @Transactional
    void save_and_findById() {

        // arrange
        var mean = smsVerificationOf("+491806672255", principal);

        // act
        var id = verificationMeanRepository.save(mean).getId();
        var verificationMean = verificationMeanRepository.findById(id);

        // assert
        assertTrue(verificationMean.isPresent());
        assertAll(
                () -> assertEquals(principal, verificationMean.get().getPersonalAuthentication()),
                () -> assertTrue(mean.getClass().isAssignableFrom(verificationMean.get().getClass()))
        );
        assertEquals(mean.getPhoneNumber(), ((VerificationMeanJpa.SmsVerification) verificationMean.get()).getPhoneNumber());
    }

    private VerificationMeanJpa.SmsVerification smsVerificationOf(String phoneNumber, PersonalAuthenticationJpa principal) {
        var smsVerification = new VerificationMeanJpa.SmsVerification();
        smsVerification.setPhoneNumber(phoneNumber);
        smsVerification.setPersonalAuthentication(principal);
        return smsVerification;
    }
}
