package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.jpa.JpaTestConfiguration;
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
        var input = new Registration.ConsoleRegistration();
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
        assertTrue(output.get() instanceof Registration.ConsoleRegistration);
    }

    @Test
    public void save_emailRegistration_and_findById() {

        // arrange
        var expiresAt = Instant.now().toEpochMilli();
        var input = new Registration.EmailRegistration();
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
        assertTrue(output.get() instanceof Registration.EmailRegistration);
        var emailRegistration = output.map(Registration.EmailRegistration.class::cast).get();
        assertEquals("admin@google.com", emailRegistration.getEmail());
    }

    @Test
    public void save_smsRegistration_and_findById() {

        // arrange
        var expiresAt = Instant.now().toEpochMilli();
        var input = new Registration.SmsRegistration();
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
        assertTrue(output.get() instanceof Registration.SmsRegistration);
        var smsRegistration = output.map(Registration.SmsRegistration.class::cast).get();
        assertEquals("+41234567890", smsRegistration.getPhoneNumber());
    }
}
