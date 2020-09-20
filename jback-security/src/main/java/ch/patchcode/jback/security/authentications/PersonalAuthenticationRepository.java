package ch.patchcode.jback.security.authentications;

import java.util.Optional;

public interface PersonalAuthenticationRepository {

    PersonalAuthentication create(PersonalAuthentication.Draft draft);

    Optional<PersonalAuthentication> findByUserIdentification(String userIdentification);
}
