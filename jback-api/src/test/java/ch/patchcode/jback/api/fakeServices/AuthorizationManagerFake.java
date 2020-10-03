package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.entities.Person;
import ch.patchcode.jback.presentation.AuthorizationManager;
import ch.patchcode.jback.presentation.LoginData;
import ch.patchcode.jback.presentation.TryLoginResult;
import ch.patchcode.jback.presentation.impl.PersonalAuthentication;
import ch.patchcode.jback.security.entities.Principal;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.security.entities.PendingRegistration;
import ch.patchcode.jback.security.secBaseImpl.VerificationCode;
import ch.patchcode.jback.security.entities.VerificationMean;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("presentation.authorizationManager")
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
    public PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean> means) {

        // do nothing
        return null;
    }

    @Override
    public void addClient(Principal principal, Person person) {

        // do nothing
    }

    @Override
    public TryLoginResult tryLogin(LoginData data) {

        // do nothing special - reject
        return TryLoginResult.UNKNOWN_USER;
    }
}
