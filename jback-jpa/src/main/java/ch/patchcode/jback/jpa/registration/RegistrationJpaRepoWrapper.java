package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.security.registration.PendingRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationJpaRepoWrapper implements PendingRegistrationRepository {

    private final RegistrationJpaRepository registrationJpaRepository;

    private final FromDomainConverter fromDomainConverter = new FromDomainConverter();

    @Autowired
    public RegistrationJpaRepoWrapper(RegistrationJpaRepository registrationJpaRepository) {

        this.registrationJpaRepository = registrationJpaRepository;
    }

    @Override
    public PendingRegistration create(PendingRegistration.Draft pendingRegistration) {

        var entity = fromDomainConverter.convert(pendingRegistration);
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
