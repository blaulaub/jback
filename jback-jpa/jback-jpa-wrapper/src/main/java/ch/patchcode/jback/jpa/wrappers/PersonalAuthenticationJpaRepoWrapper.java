package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.jpa.entities.*;
import ch.patchcode.jback.securityEntities.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.PersonalAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class PersonalAuthenticationJpaRepoWrapper implements PersonalAuthenticationRepository {

    private final PersonalAuthenticationJpaRepository personalAuthenticationJpaRepository;
    private final PersonJpaRepository personJpaRepository;
    private final VerificationMeanJpaRepository verificationMeanJpaRepository;

    @Autowired
    public PersonalAuthenticationJpaRepoWrapper(
            PersonalAuthenticationJpaRepository personalAuthenticationJpaRepository,
            PersonJpaRepository personJpaRepository,
            VerificationMeanJpaRepository verificationMeanJpaRepository
    ) {

        this.personalAuthenticationJpaRepository = personalAuthenticationJpaRepository;
        this.personJpaRepository = personJpaRepository;
        this.verificationMeanJpaRepository = verificationMeanJpaRepository;
    }

    @Override
    public PersonalAuthentication create(PersonalAuthentication.Draft draft) {

        var principal = persist(draft);
        return principal.toDomain();
    }

    @Override
    public Optional<PersonalAuthentication> findByUserIdentification(String userIdentification) {

        // TODO need to convert and lookup instead
        return Optional.empty();
    }

    private PersonalAuthenticationJpa persist(PersonalAuthentication.Draft draft) {

        PersonJpa self = personJpaRepository.findById(draft.getHolder().getId()).orElseThrow(EntityNotFoundException::new);

        var entity = new PersonalAuthenticationJpa();
        entity.setSelf(self);
        entity.setAuthorities(emptyList());
        entity.setClients(emptyList());
        PersonalAuthenticationJpa result = personalAuthenticationJpaRepository.save(entity);

        List<VerificationMeanJpa> means = draft.getMeans().stream().map(it -> VerificationMeanJpa.fromDomain(entity, it)).collect(toList());
        result.setVerificationMeans(verificationMeanJpaRepository.saveAll(means));

        return result;
    }
}
