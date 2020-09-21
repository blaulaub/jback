package ch.patchcode.jback.jpa.entities.spring;

import ch.patchcode.jback.jpa.entities.PersonalAuthenticationJpa;
import ch.patchcode.jback.jpa.entities.VerificationMeanJpa;
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

    private final PersonalAuthenticationJpaRepository personalAuthenticationJpaRepository;
    private final VerificationMeanJpaRepository repo;

    private PersonalAuthenticationJpa principal;

    @Autowired
    public VerificationMeanJpaRepositoryTest(
            VerificationMeanJpaRepository verificationMeanRepository,
            PersonalAuthenticationJpaRepository personalAuthenticationJpaRepository) {

        this.repo = verificationMeanRepository;
        this.personalAuthenticationJpaRepository = personalAuthenticationJpaRepository;
    }

    @BeforeEach
    void setUp() {

        principal = personalAuthenticationJpaRepository.save(new PersonalAuthenticationJpa());
    }

    @Test
    @Transactional
    void consoleVerification_save_and_findById() {

        // arrange
        var mean = consoleVerification();

        // act
        var id = repo.save(mean).getId();
        var verificationMean = repo.findById(id);

        // assert
        assertTrue(verificationMean.isPresent());
        assertAll(
                () -> assertEquals(principal, verificationMean.get().getPersonalAuthentication()),
                () -> assertTrue(verificationMean.get() instanceof VerificationMeanJpa.ConsoleVerification)
        );
    }

    @Test
    @Transactional
    void smsVerification_save_and_findById() {

        // arrange
        var mean = smsVerificationOf("+491806672255");

        // act
        var id = repo.save(mean).getId();
        var verificationMean = repo.findById(id);

        // assert
        assertTrue(verificationMean.isPresent());
        assertAll(
                () -> assertEquals(principal, verificationMean.get().getPersonalAuthentication()),
                () -> assertTrue(verificationMean.get() instanceof VerificationMeanJpa.SmsVerification)
        );
        assertEquals(mean.getPhoneNumber(), ((VerificationMeanJpa.SmsVerification) verificationMean.get()).getPhoneNumber());
    }

    @Test
    @Transactional
    void emailVerification_save_and_findById() {

        // arrange
        var mean = emailVerificationOf("admin@google.com");

        // act
        var id = repo.save(mean).getId();
        var verificationMean = repo.findById(id);

        // assert
        assertTrue(verificationMean.isPresent());
        assertAll(
                () -> assertEquals(principal, verificationMean.get().getPersonalAuthentication()),
                () -> assertTrue(verificationMean.get() instanceof VerificationMeanJpa.EmailVerification)
        );
        assertEquals(mean.getEmail(), ((VerificationMeanJpa.EmailVerification) verificationMean.get()).getEmail());
    }

    private VerificationMeanJpa.ConsoleVerification consoleVerification() {
        var consoleVerification = new VerificationMeanJpa.ConsoleVerification();
        consoleVerification.setPersonalAuthentication(principal);
        return consoleVerification;
    }

    private VerificationMeanJpa.SmsVerification smsVerificationOf(String phoneNumber) {
        var smsVerification = new VerificationMeanJpa.SmsVerification();
        smsVerification.setPhoneNumber(phoneNumber);
        smsVerification.setPersonalAuthentication(principal);
        return smsVerification;
    }

    private VerificationMeanJpa.EmailVerification emailVerificationOf(String email) {
        var emailVerification = new VerificationMeanJpa.EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setPersonalAuthentication(principal);
        return emailVerification;
    }
}
