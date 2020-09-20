package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.security.AuthorizationManager;
import ch.patchcode.jback.security.LoginData;
import ch.patchcode.jback.security.Principal;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.security.secBaseImpl.PendingRegistration;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class AuthorizationManagerImpl implements AuthorizationManager {

    private final static Logger LOG = LoggerFactory.getLogger(AuthorizationManagerImpl.class);

    private final RegistrationService registrationService;
    private final PersonalAuthenticationRepository personalAuthenticationRepository;

    @Inject
    public AuthorizationManagerImpl(
            RegistrationService registrationService,
            PersonalAuthenticationRepository personalAuthenticationRepository
    ) {

        this.registrationService = registrationService;
        this.personalAuthenticationRepository = personalAuthenticationRepository;
    }

    @Override
    public PendingRegistration.Id setupRegistration(InitialRegistrationData initialRegistrationData) {

        return registrationService.setupRegistration(initialRegistrationData);
    }

    @Override
    public void addClient(Principal principal, Person<VerificationMean> person) {

        // TODO implement
        throw new RuntimeException("not implemented");
    }

    @Override
    public PersonalAuthentication createAuthorizationFor(Person<VerificationMean> person, Iterable<VerificationMean> means) {

        var personalAuthentication = new PersonalAuthentication.Draft.Builder()
                .setHolder(person)
                .addAllMeans(means)
                .build();
        return personalAuthenticationRepository.create(personalAuthentication);
    }

    @Override
    public void tryLogin(LoginData data) {

        // TODO implement
        throw new RuntimeException("not implemented");
    }
}
