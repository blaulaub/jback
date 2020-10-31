package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.jpa.entities.RegistrationJpa;
import ch.patchcode.jback.jpa.entitiesSpring.RegistrationJpaRepository;
import ch.patchcode.jback.securityEntities.registration.PendingRegistrationRepository;
import ch.patchcode.jback.securityEntities.registration.PendingRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationJpaRepoWrapper implements PendingRegistrationRepository {

    private final RegistrationJpaRepository registrationJpaRepository;

    @Autowired
    public RegistrationJpaRepoWrapper(RegistrationJpaRepository registrationJpaRepository) {

        this.registrationJpaRepository = registrationJpaRepository;
    }

    @Override
    public PendingRegistration create(PendingRegistration.Draft pendingRegistration) {

        var entity = RegistrationJpa.fromDomain(pendingRegistration);
        var saved = registrationJpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<PendingRegistration> findById(UUID id) {

        return registrationJpaRepository.findById(id).map(RegistrationJpa::toDomain);
    }

    @Override
    public void removeById(UUID id) {

        registrationJpaRepository.deleteById(id);
    }
}
