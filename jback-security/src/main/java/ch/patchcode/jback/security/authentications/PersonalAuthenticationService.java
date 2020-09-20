package ch.patchcode.jback.security.authentications;

import java.util.Optional;

public interface PersonalAuthenticationService {

    Optional<PersonalAuthentication> findByUserIdentification(String userIdentification);
}
