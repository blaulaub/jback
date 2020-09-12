package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.secBase.ConfirmationResult;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationCode;
import ch.patchcode.jback.security.NoPendingRegistrationException;
import ch.patchcode.jback.security.registration.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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
    void authenticate_unexpected_throwsNoPendingRegistrationException() {

        // arrange
        var registrationId = PendingRegistration.Id.of(UUID.randomUUID());
        var verificationCode = VerificationCode.of("blub");

        // act & assert
        assertThrows(
                NoPendingRegistrationException.class,
                () -> manager.authenticate(registrationId, verificationCode)
        );
    }

    @Test
    void authenticate_expectedWithWrongCode_throwsBadCredentialsException() {

        // arrange
        var registrationId = PendingRegistration.Id.of(UUID.randomUUID());
        var verificationCode = VerificationCode.of("blub");
        var pendingRegistration = new PendingRegistration.Builder().buildPartial();
        when(registrationService.getRegistration(eq(registrationId.getId()))).thenReturn(Optional.of(pendingRegistration));
        when(registrationService.confirmRegistration(any(PendingRegistration.class), any())).thenReturn(ConfirmationResult.MISMATCH);

        // act & assert
        assertThrows(
                BadCredentialsException.class,
                () -> manager.authenticate(registrationId, verificationCode)
        );
    }
}