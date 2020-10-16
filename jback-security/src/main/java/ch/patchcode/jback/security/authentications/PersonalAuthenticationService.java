package ch.patchcode.jback.security.authentications;

import ch.patchcode.jback.securityEntities.authentications.PersonalAuthentication;

import java.util.Optional;

public interface PersonalAuthenticationService {

    Optional<PersonalAuthentication> findByUserIdentification(String userIdentification);
}
