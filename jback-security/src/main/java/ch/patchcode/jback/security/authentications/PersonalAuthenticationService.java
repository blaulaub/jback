package ch.patchcode.jback.security.authentications;

import ch.patchcode.jback.securityEntities.authentications.Principal;

import java.util.Optional;

public interface PersonalAuthenticationService {

    Optional<Principal> findByUserIdentification(String userIdentification);
}
