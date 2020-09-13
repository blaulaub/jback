package ch.patchcode.jback.jpa.principals;

import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.jpa.persons.PersonJpaRepository;
import ch.patchcode.jback.jpa.verificationMeans.VerificationMeanJpa;
import ch.patchcode.jback.jpa.verificationMeans.VerificationMeanJpaRepository;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class PrincipalJpaRepoWrapper implements PersonalAuthenticationRepository {

    private final PrincipalJpaRepository principalJpaRepository;
    private final VerificationMeanJpaRepository verificationMeanJpaRepository;
    private final PersonJpaRepository personJpaRepository;

    @Autowired
    public PrincipalJpaRepoWrapper(
            PrincipalJpaRepository principalJpaRepository,
            VerificationMeanJpaRepository verificationMeanJpaRepository,
            PersonJpaRepository personJpaRepository) {

        this.principalJpaRepository = principalJpaRepository;
        this.verificationMeanJpaRepository = verificationMeanJpaRepository;
        this.personJpaRepository = personJpaRepository;
    }

    // TODO create should be based on a draft, not on the domain entitiy
    @Override
    public PersonalAuthentication create(PersonalAuthentication personalAuthentication) {

        var principal = persist(personalAuthentication);
        var means = persist(principal, personalAuthentication.getMeans());
        return PersonalAuthentication.of(principal.getSelf().toDomain(), toDomain(means));
    }

    private List<VerificationMeanJpa> persist(PrincipalJpa principal, List<VerificationMean> means) {

        return verificationMeanJpaRepository.saveAll(means.stream()
                .map(it -> VerificationMeanJpa.fromDomain(principal, it))
                .collect(toList()));
    }

    private List<VerificationMean> toDomain(List<VerificationMeanJpa> persisted) {
        return persisted.stream().map(VerificationMeanJpa::toDomain).collect(toList());
    }

    private PrincipalJpa persist(PersonalAuthentication personalAuthentication) {

        var draft = new PrincipalJpa();
        draft.setSelf(personJpaRepository.findById(personalAuthentication.getHolder().getId()).orElseThrow(EntityNotFoundException::new));
        draft.setAuthorities(emptyList());
        draft.setClients(emptyList());
        return principalJpaRepository.save(draft);
    }
}
