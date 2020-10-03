package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.core.entities.Person;
import ch.patchcode.jback.security.AuthorizationManager;
import ch.patchcode.jback.security.LoginData;
import ch.patchcode.jback.securityEntities.Principal;
import ch.patchcode.jback.security.TryLoginResult;
import ch.patchcode.jback.securityEntities.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.PersonalAuthenticationRepository;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationService;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.securityEntities.PendingRegistration;
import ch.patchcode.jback.securityEntities.VerificationMean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

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
    public PendingRegistration.Id setupRegistration(InitialRegistrationData initialRegistrationData) {

        return registrationService.setupRegistration(initialRegistrationData);
    }

    @Override
    public void addClient(Principal principal, Person person) {

        // TODO implement
        throw new RuntimeException("not implemented");
    }

    @Override
    public PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean> means) {

        var personalAuthentication = new PersonalAuthentication.Draft.Builder()
                .setHolder(person)
                .addAllMeans(means)
                .build();
        return personalAuthenticationRepository.create(personalAuthentication);
    }

    @Override
    public TryLoginResult tryLogin(LoginData data) {

        var userIdentification = data.getUserIdentification();

        var auth = personalAuthenticationService.findByUserIdentification(userIdentification);
        if (auth.isPresent()) {

            // TODO where is the case that returns SUCCESS?
            return TryLoginResult.NEED_CONFIRMATION_CODE;
        } else {

            return TryLoginResult.UNKNOWN_USER;
        }

    }
}
