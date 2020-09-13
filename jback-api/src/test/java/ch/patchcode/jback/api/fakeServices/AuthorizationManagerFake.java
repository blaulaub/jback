package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.InitialRegistrationData;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationCode;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Principal;
import ch.patchcode.jback.security.AuthorizationManager;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void addClient(Principal principal, Person person) {

        // TODO implement when necessary
        throw new RuntimeException("not implemented");
    }

    @Override
    public PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean> means) {

        // TODO implement when necessary
        throw new RuntimeException("not implemented");
    }
}
