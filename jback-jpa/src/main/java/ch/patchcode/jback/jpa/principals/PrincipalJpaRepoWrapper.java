package ch.patchcode.jback.jpa.principals;

import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        PrincipalJpa principal = persist(personalAuthentication);

        // TODO instead of emptyList() there should be the list of the true means instead
        List<VerificationMean> means = emptyList();

        return new PersonalAuthentication(
                principal.getSelf().toDomain(),
                means
        );
    }

    private PrincipalJpa persist(PersonalAuthentication personalAuthentication) {

        var draft = new PrincipalJpa();
        draft.setSelf(PersonJpa.fromDomain(personalAuthentication.getHolder()));
        draft.setAuthorities(emptyList());
        draft.setClients(emptyList());
        var principal = principalJpaRepository.save(draft);
        return principal;
    }
}
