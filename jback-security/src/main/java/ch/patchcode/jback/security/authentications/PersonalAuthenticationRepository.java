package ch.patchcode.jback.security.authentications;

import java.util.Optional;

public interface PersonalAuthenticationRepository {

    PersonalAuthentication create(PersonalAuthentication.Draft draft);

    // TODO does not belong here
    Optional<PersonalAuthentication> findByUserIdentification(String userIdentification);
}
