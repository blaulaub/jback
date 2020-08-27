package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.*;
import ch.patchcode.jback.core.registration.VerificationService.ConsoleVerificationService;
import ch.patchcode.jback.core.registration.VerificationService.EmailVerificationService;
import ch.patchcode.jback.core.registration.VerificationService.SmsVerificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static ch.patchcode.jback.core.util.SomeData.somePendingRegistration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class RegistrationServiceImplTest {

    @Mock
    private ConsoleVerificationService consoleVerificationService;

    @Mock
    private EmailVerificationService emailVerificationService;

    @Mock
    private SmsVerificationService smsVerificationService;

    @Mock
    private PendingRegistrationRepository pendingRegistrationRepository;

    @InjectMocks
    private RegistrationServiceImpl service;

    @BeforeEach
    void setUp() {

        initMocks(this);
    }

    @Test
    void beginRegistration_viaConsole_invokesConsoleVerification() {

        // arrange
        var data = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationByConsole())
                .build();
        var expectedId = PendingRegistration.Id.of(UUID.randomUUID());
        when(pendingRegistrationRepository.save(any())).thenReturn(expectedId);

        // act
        var id = service.beginRegistration(data);

        // assert
        assertEquals(expectedId.getId(), id.getId());
        verify(pendingRegistrationRepository, times(1)).save(any());
        verify(consoleVerificationService, times(1)).sendOut(any());
        verify(emailVerificationService, times(0)).sendOut(any());
        verify(smsVerificationService, times(0)).sendOut(any());
    }

    @Test
    void beginRegistration_viaEmail_invokesEmailVerification() {

        // arrange
        InitialRegistrationData data = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationByEmail.Builder().buildPartial())
                .build();
        var expectedId = PendingRegistration.Id.of(UUID.randomUUID());
        when(pendingRegistrationRepository.save(any())).thenReturn(expectedId);

        // act
        var id = service.beginRegistration(data);

        // assert
        assertEquals(expectedId.getId(), id.getId());
        verify(pendingRegistrationRepository, times(1)).save(any());
        verify(consoleVerificationService, times(0)).sendOut(any());
        verify(emailVerificationService, times(1)).sendOut(any());
        verify(smsVerificationService, times(0)).sendOut(any());
    }

    @Test
    void beginRegistration_viaSms_invokesSmsVerification() {

        // arrange
        InitialRegistrationData data = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationBySms.Builder().buildPartial())
                .build();
        var expectedId = PendingRegistration.Id.of(UUID.randomUUID());
        when(pendingRegistrationRepository.save(any())).thenReturn(expectedId);

        // act
        var id = service.beginRegistration(data);

        // assert
        assertEquals(expectedId.getId(), id.getId());
        verify(pendingRegistrationRepository, times(1)).save(any());
        verify(consoleVerificationService, times(0)).sendOut(any());
        verify(emailVerificationService, times(0)).sendOut(any());
        verify(smsVerificationService, times(1)).sendOut(any());
    }

    @Test
    void concludeRegistration_whenNotPending_fails() {

        // arrange
        UUID id = UUID.randomUUID();
        String code = "1234";
        when(pendingRegistrationRepository.findById(eq(id))).thenReturn(Optional.empty());

        // act
        var result = service.concludeRegistration(id, code);

        // assert
        assertEquals(ConfirmationResult.NOT_FOUND, result);
    }
}
