package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.jpa.JpaTestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.mockito.MockitoAnnotations.initMocks;

class RegistrationJpaRepoWrapperTest {

    @Mock
    private RegistrationJpaRepository registrationJpaRepository;

    @InjectMocks
    private RegistrationJpaRepoWrapper wrapper;

    @BeforeEach
    void setUp() {

        initMocks(this);
    }

    @Test
    void save() {

        // arrange
        PendingRegistration pendingRegistration = new PendingRegistration.Builder().buildPartial();

        // act
        var result = wrapper.save(pendingRegistration);

        // assert
        throw new RuntimeException("not implemented");
    }

    @Test
    void findById() {

        // arrange
        var id = UUID.randomUUID();

        // act
        var result = wrapper.findById(id);

        // assert
        throw new RuntimeException("not implemented");
    }


}