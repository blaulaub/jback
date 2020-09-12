package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationCode;
import ch.patchcode.jback.security.AuthorizationManager;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
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
    public PersonalAuthentication createAuthorizationFor(Person person) {

        // TODO implement when necessary
        throw new RuntimeException("not implemented");
    }
}
