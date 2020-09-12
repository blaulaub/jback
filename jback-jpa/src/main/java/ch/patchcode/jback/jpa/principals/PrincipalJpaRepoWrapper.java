package ch.patchcode.jback.jpa.principals;

import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import org.springframework.stereotype.Service;

@Service
public class PrincipalJpaRepoWrapper implements PersonalAuthenticationRepository {

    @Override
    public PersonalAuthentication save(PersonalAuthentication personalAuthentication) {

        // TODO implement and test
        return personalAuthentication;
    }
}
