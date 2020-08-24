package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    void process_viaConsole_invokesConsoleVerification() {

        // arrange
        InitialRegistrationData data = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationByConsole())
                .build();

        // act
        service.process(data);

        // assert
        verify(pendingRegistrationRepository, times(1)).save(any());
        verify(consoleVerificationService, times(1)).sendOut(any());
        verify(emailVerificationService, times(0)).sendOut(any());
        verify(smsVerificationService, times(0)).sendOut(any());
    }

    @Test
    void process_viaSms_invokesEmailVerification() {

        // arrange
        InitialRegistrationData data = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationByEmail.Builder().buildPartial())
                .build();

        // act
        service.process(data);

        // assert
        verify(pendingRegistrationRepository, times(1)).save(any());
        verify(consoleVerificationService, times(0)).sendOut(any());
        verify(emailVerificationService, times(1)).sendOut(any());
        verify(smsVerificationService, times(0)).sendOut(any());
    }

    @Test
    void process_viaSms_invokesSmsVerification() {

        // arrange
        InitialRegistrationData data = new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationBySms.Builder().buildPartial())
                .build();

        // act
        service.process(data);

        // assert
        verify(pendingRegistrationRepository, times(1)).save(any());
        verify(consoleVerificationService, times(0)).sendOut(any());
        verify(emailVerificationService, times(0)).sendOut(any());
        verify(smsVerificationService, times(1)).sendOut(any());
    }
}
