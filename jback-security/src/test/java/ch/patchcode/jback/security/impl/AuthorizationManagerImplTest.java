package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.ConfirmationResult;
import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.VerificationCode;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.security.NoPendingRegistrationException;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import ch.patchcode.jback.security.authentications.TemporaryAuthentication;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.secBaseImpl.PendingRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class AuthorizationManagerImplTest {

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

    @Test
    void createAuthorizationFor() {

        // arrange
        Person person = new Person.Builder().buildPartial();
        List<VerificationMean> means = emptyList();
        when(personalAuthenticationRepository.create(any()))
                .thenAnswer((Answer<PersonalAuthentication>) invocation -> {
                    var draft = (PersonalAuthentication.Draft) invocation.getArguments()[0];
                    return PersonalAuthentication.of(draft.getHolder(), draft.getMeans());
                });

        // act
        var auth = manager.createAuthorizationFor(person, means);

        // assert
        assertAll(
                () -> assertEquals(person, auth.getHolder()),
                () -> assertIterableEquals(means, auth.getMeans())
        );

        verify(personalAuthenticationRepository, times(1)).create(eq(new PersonalAuthentication.Draft.Builder()
                .setHolder(person)
                .addAllMeans(means)
                .build()));
    }
}
