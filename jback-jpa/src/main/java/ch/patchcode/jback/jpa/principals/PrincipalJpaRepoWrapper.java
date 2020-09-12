package ch.patchcode.jback.jpa.principals;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class PrincipalJpaRepoWrapper implements PersonalAuthenticationRepository {

    private final PrincipalJpaRepository principalJpaRepository;

    @Autowired
    public PrincipalJpaRepoWrapper(PrincipalJpaRepository principalJpaRepository) {

        this.principalJpaRepository = principalJpaRepository;
    }

    @Override
    public PersonalAuthentication create(PersonalAuthentication personalAuthentication) {

        var draft = new PrincipalJpa();
        // TODO the draft should contain all the details
        var principal = principalJpaRepository.save(draft);

        // TODO instead of emptyList() there should be the list of the true means instead
        return new PersonalAuthentication(
                principal.getSelf().toDomain(),
                emptyList()
        );
    }
}
