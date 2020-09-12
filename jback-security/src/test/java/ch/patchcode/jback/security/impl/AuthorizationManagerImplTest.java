package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.secBase.*;
import ch.patchcode.jback.security.NoPendingRegistrationException;
import ch.patchcode.jback.security.authentications.TemporaryAuthentication;
import ch.patchcode.jback.security.registration.RegistrationService;
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
import static org.mockito.Mockito.*;
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
    void setupRegistration_calls_registrationService() {

        // arrange
        InitialRegistrationData data = new InitialRegistrationData.Builder().buildPartial();

        var expectedId = PendingRegistration.Id.of(UUID.randomUUID());
        when(registrationService.setupRegistration(eq(data))).thenReturn(expectedId);

        // act
        var actualId = manager.setupRegistration(data);

        // assert
        assertEquals(expectedId, actualId);
        verify(registrationService, times(1)).setupRegistration(eq(data));
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
        assertNotNull(auth);
        assertTrue(auth instanceof TemporaryAuthentication);

        var tempAuth = (TemporaryAuthentication) auth;
        assertEquals(pendingRegistration.getFirstName(), tempAuth.getFirstName());
        assertEquals(pendingRegistration.getLastName(), tempAuth.getLastName());
    }
}