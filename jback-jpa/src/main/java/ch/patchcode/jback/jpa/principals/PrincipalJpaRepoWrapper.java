package ch.patchcode.jback.jpa.principals;

import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.jpa.verificationMeans.VerificationMeanJpa;
import ch.patchcode.jback.jpa.verificationMeans.VerificationMeanJpaRepository;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

@Service
public class PrincipalJpaRepoWrapper implements PersonalAuthenticationRepository {

    private final PrincipalJpaRepository principalJpaRepository;
    private final VerificationMeanJpaRepository verificationMeanJpaRepository;

    @Autowired
    public PrincipalJpaRepoWrapper(
            PrincipalJpaRepository principalJpaRepository,
            VerificationMeanJpaRepository verificationMeanJpaRepository
    ) {

        this.principalJpaRepository = principalJpaRepository;
        this.verificationMeanJpaRepository = verificationMeanJpaRepository;
    }

    @Override
    public PersonalAuthentication create(PersonalAuthentication personalAuthentication) {

        PrincipalJpa principal = persist(personalAuthentication);
        List<VerificationMean> means = toList(persist(principal, personalAuthentication.getMeans().stream()));
        return new PersonalAuthentication(principal.getSelf().toDomain(), means);
    }

    private Stream<VerificationMeanJpa> persist(PrincipalJpa principal, Stream<VerificationMean> means) {

        return means
                .map(it -> VerificationMeanJpa.fromDomain(principal, it))
                .map(verificationMeanJpaRepository::save);
    }

    private PrincipalJpa persist(PersonalAuthentication personalAuthentication) {

        var draft = new PrincipalJpa();
        draft.setSelf(PersonJpa.fromDomain(personalAuthentication.getHolder()));
        draft.setAuthorities(emptyList());
        draft.setClients(emptyList());
        return principalJpaRepository.save(draft);
    }

    private List<VerificationMean> toList(Stream<VerificationMeanJpa> persisted) {

        return persisted
                .map(VerificationMeanJpa::toDomain)
                .collect(Collectors.toList());
    }
}
