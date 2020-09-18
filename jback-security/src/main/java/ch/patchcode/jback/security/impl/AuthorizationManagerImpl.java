package ch.patchcode.jback.security.impl;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.ConfirmationResult;
import ch.patchcode.jback.secBase.VerificationCode;
import ch.patchcode.jback.secBase.secModelImpl.Principal;
import ch.patchcode.jback.security.AuthorizationManager;
import ch.patchcode.jback.security.NoPendingRegistrationException;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.TemporaryAuthentication;
import ch.patchcode.jback.security.registration.RegistrationService;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.security.secBaseImpl.PendingRegistration;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationManagerImpl implements AuthorizationManager {

    private final static Logger LOG = LoggerFactory.getLogger(AuthorizationManagerImpl.class);

    private final RegistrationService registrationService;
    private final PersonalAuthenticationRepository personalAuthenticationRepository;

    @Autowired
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
    public void authenticate(PendingRegistration.Id registrationId, VerificationCode verificationCode) {

        var pendingRegistration = registrationService.getRegistration(registrationId.getId())
                .orElseThrow(() -> new NoPendingRegistrationException(registrationId));

        var confirmationResult = registrationService.confirmRegistration(
                pendingRegistration,
                verificationCode.getVerificationCode()
        );

        confirmationResult.accept(new ConfirmationResult.Visitor<Void>() {
            @Override
            public Void caseConfirmed() {

                LOG.debug("change security context to TemporaryAuthentication");
                SecurityContextHolder.getContext()
                        .setAuthentication(new TemporaryAuthentication(pendingRegistration));
                return null;
            }

            @Override
            public Void caseNotFound() {
                throw new NoPendingRegistrationException(registrationId);
            }

            @Override
            public Void caseMismatch() {
                throw new BadCredentialsException("Invalid code provided for " + registrationId.getId());
            }
        });
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
}
