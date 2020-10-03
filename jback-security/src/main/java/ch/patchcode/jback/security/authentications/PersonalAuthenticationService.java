package ch.patchcode.jback.security.authentications;

import ch.patchcode.jback.securityEntities.PersonalAuthentication;

import java.util.Optional;

public interface PersonalAuthenticationService {

    Optional<PersonalAuthentication> findByUserIdentification(String userIdentification);
}
