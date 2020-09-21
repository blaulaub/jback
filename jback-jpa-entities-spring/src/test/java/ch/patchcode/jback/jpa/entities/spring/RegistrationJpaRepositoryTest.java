package ch.patchcode.jback.jpa.entities.spring;

import ch.patchcode.jback.jpa.entities.RegistrationJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaTestConfiguration.class})
class RegistrationJpaRepositoryTest {

    private final RegistrationJpaRepository registrationJpaRepository;

    @Autowired
    public RegistrationJpaRepositoryTest(RegistrationJpaRepository registrationJpaRepository) {

        this.registrationJpaRepository = registrationJpaRepository;
    }

    @Test
    public void save_consoleRegistration_and_findById() {

        // arrange
        var expiresAt = Instant.now().toEpochMilli();
        var input = new RegistrationJpa.ConsoleRegistrationJpa();
        input.setFirstName("Tom");
        input.setLastName("Sawyer");
        input.setVerificationCode("5555");
        input.setExpiresAt(expiresAt);

        // act
        var id = registrationJpaRepository.save(input).getId();
        var output = registrationJpaRepository.findById(id);

        // assert
        assertTrue(output.isPresent());
        assertEquals("Tom", output.get().getFirstName());
        assertEquals("Sawyer", output.get().getLastName());
        assertEquals(expiresAt, output.get().getExpiresAt());
        assertTrue(output.get() instanceof RegistrationJpa.ConsoleRegistrationJpa);
    }

    @Test
    public void save_emailRegistration_and_findById() {

        // arrange
        var expiresAt = Instant.now().toEpochMilli();
        var input = new RegistrationJpa.EmailRegistrationJpa();
        input.setFirstName("Tom");
        input.setLastName("Sawyer");
        input.setVerificationCode("5555");
        input.setExpiresAt(expiresAt);
        input.setEmail("admin@google.com");

        // act
        var id = registrationJpaRepository.save(input).getId();
        var output = registrationJpaRepository.findById(id);

        // assert
        assertTrue(output.isPresent());
        assertEquals("Tom", output.get().getFirstName());
        assertEquals("Sawyer", output.get().getLastName());
        assertEquals(expiresAt, output.get().getExpiresAt());
        assertTrue(output.get() instanceof RegistrationJpa.EmailRegistrationJpa);
        var emailRegistration = output.map(RegistrationJpa.EmailRegistrationJpa.class::cast).get();
        assertEquals("admin@google.com", emailRegistration.getEmail());
    }

    @Test
    public void save_smsRegistration_and_findById() {

        // arrange
        var expiresAt = Instant.now().toEpochMilli();
        var input = new RegistrationJpa.SmsRegistrationJpa();
        input.setFirstName("Tom");
        input.setLastName("Sawyer");
        input.setVerificationCode("5555");
        input.setExpiresAt(expiresAt);
        input.setPhoneNumber("+41234567890");

        // act
        var id = registrationJpaRepository.save(input).getId();
        var output = registrationJpaRepository.findById(id);

        // assert
        assertTrue(output.isPresent());
        assertEquals("Tom", output.get().getFirstName());
        assertEquals("Sawyer", output.get().getLastName());
        assertEquals(expiresAt, output.get().getExpiresAt());
        assertTrue(output.get() instanceof RegistrationJpa.SmsRegistrationJpa);
        var smsRegistration = output.map(RegistrationJpa.SmsRegistrationJpa.class::cast).get();
        assertEquals("+41234567890", smsRegistration.getPhoneNumber());
    }
}
