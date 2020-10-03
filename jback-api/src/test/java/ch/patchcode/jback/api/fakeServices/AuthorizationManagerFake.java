package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.presentation.AuthorizationManager;
import ch.patchcode.jback.presentation.LoginData;
import ch.patchcode.jback.presentation.TryLoginResult;
import ch.patchcode.jback.presentation.impl.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.Principal;
import ch.patchcode.jback.security.secBaseImpl.InitialRegistrationData;
import ch.patchcode.jback.security.secBaseImpl.VerificationCode;
import ch.patchcode.jback.securityEntities.VerificationMean;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("presentation.authorizationManager")
public class AuthorizationManagerFake implements AuthorizationManager {

    @Override
    public UUID setupRegistration(InitialRegistrationData data) {

        return UUID.randomUUID();
    }

    @Override
    public void authenticate(UUID registrationId, VerificationCode verificationCode) {

        // do nothing
    }

    @Override
    public PersonalAuthentication createAuthorizationFor(Person person, Iterable<VerificationMean.Draft> means) {

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
