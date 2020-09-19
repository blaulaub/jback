package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.security.secBaseImpl.PendingRegistration;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
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
        var data = new InitialRegistrationData.Builder().buildPartial();

        var expectedId = PendingRegistration.Id.of(UUID.randomUUID());
        when(registrationService.setupRegistration(eq(data))).thenReturn(expectedId);

        // act
        var actualId = manager.setupRegistration(data);

        // assert
        assertEquals(expectedId, actualId);
        verify(registrationService, times(1)).setupRegistration(eq(data));
    }

    @Test
    void createAuthorizationFor() {

        // arrange
        var person = new Person.Builder<VerificationMean>().buildPartial();
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
