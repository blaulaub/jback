package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.jpa.entities.*;
import ch.patchcode.jback.securityEntities.authentications.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.authentications.PersonalAuthenticationRepository;
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
    private final VerificationByPasswordRepository verificationByPasswordRepository;

    @Autowired
    public PersonalAuthenticationJpaRepoWrapper(
            PersonalAuthenticationJpaRepository personalAuthenticationJpaRepository,
            PersonJpaRepository personJpaRepository,
            VerificationMeanJpaRepository verificationMeanJpaRepository,
            VerificationByPasswordRepository verificationByPasswordRepository) {

        this.personalAuthenticationJpaRepository = personalAuthenticationJpaRepository;
        this.personJpaRepository = personJpaRepository;
        this.verificationMeanJpaRepository = verificationMeanJpaRepository;
        this.verificationByPasswordRepository = verificationByPasswordRepository;
    }

    @Override
    public PersonalAuthentication create(PersonalAuthentication.Draft draft) {

        var principal = persist(draft);
        return principal.toDomain();
    }

    @Override
    public Optional<PersonalAuthentication> findByUserIdentification(String userIdentification) {

        var byUsername = verificationByPasswordRepository.findByUsername(userIdentification);

        return byUsername.stream().findFirst()
                .map(VerificationMeanJpa::getPersonalAuthentication)
                .map(PersonalAuthenticationJpa::toDomain);
    }

    private PersonalAuthenticationJpa persist(PersonalAuthentication.Draft draft) {

        PersonJpa self = personJpaRepository.findById(draft.getHolder().getId()).orElseThrow(EntityNotFoundException::new);

        var entity = new PersonalAuthenticationJpa();
        entity.setSelf(self);
        entity.setAuthorities(emptyList());
        entity.setClients(emptyList());
        PersonalAuthenticationJpa result = personalAuthenticationJpaRepository.save(entity);

        List<VerificationMeanJpa> means = draft.getMeans().stream().map(it -> VerificationMeanJpa.fromDomain(result, it)).collect(toList());
        result.setVerificationMeans(verificationMeanJpaRepository.saveAll(means));

        return result;
    }
}
