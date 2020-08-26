package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.core.registration.PendingRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationJpaRepoWrapper implements PendingRegistrationRepository {

    private final RegistrationJpaRepository registrationJpaRepository;

    private final ToRegistrationConverter toRegistrationConverter = new ToRegistrationConverter();

    @Autowired
    public RegistrationJpaRepoWrapper(RegistrationJpaRepository registrationJpaRepository) {

        this.registrationJpaRepository = registrationJpaRepository;
    }

    @Override
    public PendingRegistration.Id save(PendingRegistration pendingRegistration) {

        var entity = toRegistrationConverter.convert(pendingRegistration);
        var saved = registrationJpaRepository.save(entity);
        return PendingRegistration.Id.of(saved.getId());
    }

    @Override
    public PendingRegistration findById(UUID id) {

        throw new RuntimeException("not implemented");
    }
}
