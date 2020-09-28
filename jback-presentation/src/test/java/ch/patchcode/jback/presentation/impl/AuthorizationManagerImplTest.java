package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.security.registration.ConfirmationResult;
import ch.patchcode.jback.security.entities.PersonalAuthenticationRepository;
import ch.patchcode.jback.security.authentications.TemporaryAuthentication;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.entities.PendingRegistration;
import ch.patchcode.jback.security.secBaseImpl.VerificationCode;
import ch.patchcode.jback.security.entities.VerificationMean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AuthorizationManagerImplTest {

    @Mock
    private ch.patchcode.jback.security.AuthorizationManager authorizationManager;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private PersonalAuthenticationRepository personalAuthenticationRepository;

    @InjectMocks
    private AuthorizationManagerImpl manager;

    @BeforeEach
    void setUp() {

        initMocks(this);
    }

    @Test
    void authenticate_noneExpected_throwsNoPendingRegistrationException() {

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
    void authenticate_withWrongCode_throwsBadCredentialsException() {

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

    @Test
    void authenticate_withGoodCode_authenticates() {

        // arrange
        var registrationId = PendingRegistration.Id.of(UUID.randomUUID());
        var verificationCode = VerificationCode.of("blub");
        var pendingRegistration = new PendingRegistration.Builder()
                .setId(registrationId)
                .setVerificationCode(verificationCode.getVerificationCode())
                .setExpiresAt(Instant.MAX)
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationByConsole())
                .build();
        when(registrationService.getRegistration(eq(registrationId.getId()))).thenReturn(Optional.of(pendingRegistration));
        when(registrationService.confirmRegistration(eq(pendingRegistration), eq(verificationCode.getVerificationCode())))
                .thenReturn(ConfirmationResult.CONFIRMED);

        // act
        manager.authenticate(registrationId, verificationCode);

        // assert
        var auth = SecurityContextHolder.getContext().getAuthentication();
        assertTrue(auth instanceof TemporaryAuthentication);

        var tempAuth = (TemporaryAuthentication) auth;
        assertAll(
                () -> assertEquals(pendingRegistration.getFirstName(), tempAuth.getFirstName()),
                () -> assertEquals(pendingRegistration.getLastName(), tempAuth.getLastName())
        );
    }

}