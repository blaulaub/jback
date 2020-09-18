package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.security.Authentication;
import ch.patchcode.jback.security.AuthorizationManager;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.security.secBaseImpl.PendingRegistration;
import ch.patchcode.jback.security.secBaseImpl.VerificationCode;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorizationManagerFake implements AuthorizationManager {

    @Override
    public PendingRegistration.Id setupRegistration(InitialRegistrationData data) {

        return PendingRegistration.Id.of(UUID.randomUUID());
    }

    @Override
    public void authenticate(PendingRegistration.Id registrationId, VerificationCode verificationCode) {

        // do nothing
    }

    @Override
    public void addClient(Authentication principal, Person<VerificationMean> person) {

        // TODO implement when necessary
        throw new RuntimeException("not implemented");
    }

    @Override
    public PersonalAuthentication createAuthorizationFor(Person<VerificationMean> person, Iterable<VerificationMean> means) {

        // TODO implement when necessary
        throw new RuntimeException("not implemented");
    }
}
