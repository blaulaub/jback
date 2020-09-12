package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationCode;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.security.NoPendingRegistrationException;
import ch.patchcode.jback.security.registration.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

class AuthorizationManagerImplTest {

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private AuthorizationManagerImpl manager;

    @BeforeEach
    void setUp() {

        initMocks(this);
    }

    @Test
    void authenticate_unexpected_trowsNoPendingRegistrationException() {

        // arrange
        var registrationId = PendingRegistration.Id.of(UUID.randomUUID());
        var verificationCode = VerificationCode.of("blub");

        // act & assert
        assertThrows(
                NoPendingRegistrationException.class,
                () -> manager.authenticate(registrationId, verificationCode)
        );
    }
}