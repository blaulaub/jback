package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.jpa.util.SomeData;
import ch.patchcode.jback.security.registration.PendingRegistration;
import ch.patchcode.jback.security.registration.VerificationMean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static ch.patchcode.jback.jpa.util.SomeData.somePendingRegistration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
        PendingRegistration pendingRegistration = somePendingRegistration(new VerificationMean.VerificationByConsole());
        var id = UUID.randomUUID();
        var registration = mock(Registration.class);
        when(registration.getId()).thenReturn(id);
        when(registrationJpaRepository.save(any())).thenReturn(registration);

        // act
        var result = wrapper.save(pendingRegistration);

        // assert
        verify(registrationJpaRepository, times(1)).save(any());
        assertEquals(id, result.getId());
    }

    @Test
    void findById() {

        // arrange
        var id = UUID.randomUUID();
        Registration registration = SomeData.someRegistration();
        when(registrationJpaRepository.findById(any())).thenReturn(Optional.of(registration));

        // act
        var result = wrapper.findById(id);

        // assert
        assertTrue(result.isPresent());
        assertEquals(registration.getFirstName(), result.get().getFirstName());
        assertEquals(registration.getLastName(), result.get().getLastName());
        assertEquals(registration.getVerificationCode(), result.get().getVerificationCode());
        assertEquals(registration.getExpiresAt(), result.get().getExpiresAt().toEpochMilli());
    }
}