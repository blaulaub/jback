package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.security.AuthorizationManager;
import ch.patchcode.jback.security.LoginData;
import ch.patchcode.jback.security.authentications.TemporaryAuthentication;
import ch.patchcode.jback.securityEntities.*;
import ch.patchcode.jback.security.TryLoginResult;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationService;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.UUID;

public class AuthorizationManagerImpl implements AuthorizationManager {

    private final static Logger LOG = LoggerFactory.getLogger(AuthorizationManagerImpl.class);

    private final RegistrationService registrationService;
    private final PersonalAuthenticationService personalAuthenticationService;
    private final PersonalAuthenticationRepository personalAuthenticationRepository;

    @Inject
    public AuthorizationManagerImpl(
            RegistrationService registrationService,
            PersonalAuthenticationService personalAuthenticationService,
            PersonalAuthenticationRepository personalAuthenticationRepository
    ) {

        this.registrationService = registrationService;
        this.personalAuthenticationService = personalAuthenticationService;
        this.personalAuthenticationRepository = personalAuthenticationRepository;
    }

    @Override
    public UUID setupRegistration(InitialRegistrationData initialRegistrationData) {

        return registrationService.setupRegistration(initialRegistrationData);
    }

    @Override
    public void addClient(Principal principal, Person person) {

        // TODO implement
        throw new RuntimeException("not implemented");
    }

    @Override
    public PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean.Draft> means) {

        var personalAuthentication = new PersonalAuthentication.Draft.Builder()
                .setHolder(person)
                .addAllMeans(means)
                .build();
        return personalAuthenticationRepository.create(personalAuthentication);
    }

    @Override
    public Principal tryLogin(LoginData data) {

        var userIdentification = data.getUserIdentification();

        var auth = personalAuthenticationService.findByUserIdentification(userIdentification);

        if (auth.isPresent()) {

            // TODO This is wrong. We still need to check whether auth matches data. If so, and the user is fully
            // TODO identified, returning auth is ok. If further proof is needed (e.g. a code via SMS or Email),
            // TODO some temporary authentication should be returned.
            return auth.get();
        } else {

            return null;
        }

    }
}
