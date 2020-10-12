package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.security.AuthorizationManager;
import ch.patchcode.jback.security.LoginData;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationService;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.securityEntities.*;
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

        var principal = auth.map(it ->
                // switch statement written in visitor pattern
                data.getVerificationMean().accept(new VerificationMean.Draft.Visitor<Principal>() {

                    @Override
                    public Principal visit(VerificationByConsole.Draft consoleDraft) {

                        return getPrincipalForConsoleFromAuth(consoleDraft, it);
                    }

                    @Override
                    public Principal visit(VerificationByEmail.Draft emailDraft) {
                        return getPrincipalForEmailFromAuth(emailDraft, it);
                    }

                    @Override
                    public Principal visit(VerificationBySms.Draft smsDraft) {
                        return principalForSmsFromAuth(smsDraft, it);
                    }

                    @Override
                    public Principal visit(VerificationByPassword.Draft passwordDraft) {
                        return principalForPasswordFromAuth(passwordDraft, it);
                    }
                }));

        return principal.orElse(null);
    }

    private Principal getPrincipalForConsoleFromAuth(VerificationByConsole.Draft consoleDraft, PersonalAuthentication auth) {

        throw new RuntimeException("not implemented");
    }

    private Principal getPrincipalForEmailFromAuth(VerificationByEmail.Draft emailDraft, PersonalAuthentication auth) {

        throw new RuntimeException("not implemented");
    }

    private Principal principalForSmsFromAuth(VerificationBySms.Draft smsDraft, PersonalAuthentication auth) {

        throw new RuntimeException("not implemented");
    }

    private Principal principalForPasswordFromAuth(VerificationByPassword.Draft passwordDraft, PersonalAuthentication auth) {

        var matches = auth.getMeans().stream()
                .filter(it -> it instanceof VerificationByPassword)
                .map(VerificationByPassword.class::cast)
                .map(VerificationByPassword::getPassword)
                .anyMatch(it -> it.equals(passwordDraft.getPassword()));

        return matches ? auth : null;
    }
}
