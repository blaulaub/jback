package ch.patchcode.jback.jpa.personalAuthentications;

import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.jpa.persons.PersonJpaRepository;
import ch.patchcode.jback.jpa.verificationMeans.VerificationMeanJpa;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class PersonalAuthenticationJpaRepoWrapper implements PersonalAuthenticationRepository {

    private final PersonalAuthenticationJpaRepository personalAuthenticationJpaRepository;
    private final PersonJpaRepository personJpaRepository;

    @Autowired
    public PersonalAuthenticationJpaRepoWrapper(
            PersonalAuthenticationJpaRepository personalAuthenticationJpaRepository,
            PersonJpaRepository personJpaRepository) {

        this.personalAuthenticationJpaRepository = personalAuthenticationJpaRepository;
        this.personJpaRepository = personJpaRepository;
    }

    @Override
    public PersonalAuthentication create(PersonalAuthentication.Draft draft) {

        var principal = persist(draft);
        return principal.toDomain();
    }

    private PersonalAuthenticationJpa persist(PersonalAuthentication.Draft draft) {

        PersonJpa self = personJpaRepository.findById(draft.getHolder().getId()).orElseThrow(EntityNotFoundException::new);

        var entity = new PersonalAuthenticationJpa();
        entity.setSelf(self);
        entity.setVerificationMeans(draft.getMeans().stream().map(it -> VerificationMeanJpa.fromDomain(entity, it)).collect(toList()));
        entity.setAuthorities(emptyList());
        entity.setClients(emptyList());
        return personalAuthenticationJpaRepository.save(entity);
    }
}
