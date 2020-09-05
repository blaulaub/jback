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

    private final ToRegistrationConverter toRegistrationConverter = new ToRegistrationConverter();
    private final ToPendingRegistrationConverter toPendingRegistrationConverter = new ToPendingRegistrationConverter();

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
    public Optional<PendingRegistration> findById(UUID id) {

        return registrationJpaRepository.findById(id).map(toPendingRegistrationConverter::convert);
    }

    @Override
    public void removeById(UUID id) {

        registrationJpaRepository.deleteById(id);
    }
}
