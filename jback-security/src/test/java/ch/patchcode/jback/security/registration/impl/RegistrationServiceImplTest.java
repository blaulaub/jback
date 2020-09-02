package ch.patchcode.jback.security.registration.impl;

import ch.patchcode.jback.security.registration.*;
import ch.patchcode.jback.security.registration.VerificationService.ConsoleVerificationService;
import ch.patchcode.jback.security.registration.VerificationService.EmailVerificationService;
import ch.patchcode.jback.security.registration.VerificationService.SmsVerificationService;
import ch.patchcode.jback.security.verificationCodes.VerificationCodeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static ch.patchcode.jback.security.util.SomeData.somePendingRegistration;
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

    @Mock
    private VerificationCodeProvider verificationCodeProvider;

    @InjectMocks
    private RegistrationServiceImpl service;

    @BeforeEach
    void setUp() {

        initMocks(this);

        when(verificationCodeProvider.generateRandomCode()).thenReturn("ab34");
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
        var id = service.setupRegistration(data);

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
        var id = service.setupRegistration(data);

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
        var id = service.setupRegistration(data);

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
        var result = service.confirmRegistration(id, code);

        // assert
        assertEquals(ConfirmationResult.NOT_FOUND, result);
    }

    @Test
    void concludeRegistration_whenExpired_fails() {

        // arrange
        UUID id = UUID.randomUUID();
        String code = "1234";
        PendingRegistration pendingRegistration = PendingRegistration.Builder.from(somePendingRegistration())
                .setVerificationCode(code)
                .setExpiresAt(Instant.now().minus(Duration.ofMinutes(1)))
                .build();
        when(pendingRegistrationRepository.findById(ArgumentMatchers.eq(id))).thenReturn(Optional.of(pendingRegistration));

        // act
        var result = service.confirmRegistration(id, code);

        // assert
        assertEquals(ConfirmationResult.NOT_FOUND, result);
    }

    @Test
    void concludeRegistration_withWrongCode_fails() {

        // arrange
        UUID id = UUID.randomUUID();
        String code = "1234";
        PendingRegistration pendingRegistration = PendingRegistration.Builder.from(somePendingRegistration())
                .setVerificationCode(code)
                .setExpiresAt(Instant.MAX)
                .build();
        when(pendingRegistrationRepository.findById(ArgumentMatchers.eq(id))).thenReturn(Optional.of(pendingRegistration));

        // act
        var result = service.confirmRegistration(id, "abcd");

        // assert
        assertEquals(ConfirmationResult.MISMATCH, result);
    }

    @Test
    void concludeRegistration_withValidCode_succeeds() {

        // arrange
        UUID id = UUID.randomUUID();
        String code = "1234";
        PendingRegistration pendingRegistration = PendingRegistration.Builder.from(somePendingRegistration())
                .setVerificationCode(code)
                .setExpiresAt(Instant.MAX)
                .build();
        when(pendingRegistrationRepository.findById(ArgumentMatchers.eq(id))).thenReturn(Optional.of(pendingRegistration));

        // act
        var result = service.confirmRegistration(id, code);

        // assert
        assertEquals(ConfirmationResult.CONFIRMED, result);
    }
}
