package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.jpa.JpaTestConfiguration;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationMean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static ch.patchcode.jback.jpa.util.SomeData.somePendingRegistration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaTestConfiguration.class})
class RegistrationJpaRepoWrapperTest {

    private final RegistrationJpaRepoWrapper wrapper;

    @Autowired
    public RegistrationJpaRepoWrapperTest(RegistrationJpaRepoWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Test
    void save_and_findById() {

        // arrange
        PendingRegistration pending = somePendingRegistration(new VerificationMean.VerificationByConsole());

        // act
        var id = wrapper.save(pending).getId();
        var result = wrapper.findById(id);

        // assert
        assertTrue(result.isPresent());
        assertEquals(pending.getFirstName(), result.get().getFirstName());
        assertEquals(pending.getLastName(), result.get().getLastName());
        assertEquals(pending.getVerificationCode(), result.get().getVerificationCode());
        assertEquals(pending.getExpiresAt().toEpochMilli(), result.get().getExpiresAt().toEpochMilli());
    }
}
