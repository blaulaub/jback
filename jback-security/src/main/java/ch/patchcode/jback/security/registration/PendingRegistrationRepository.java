package ch.patchcode.jback.security.registration;

import ch.patchcode.jback.security.secBaseImpl.PendingRegistration;

import java.util.Optional;
import java.util.UUID;

public interface PendingRegistrationRepository {

    PendingRegistration create(PendingRegistration.Draft pendingRegistration);

    Optional<PendingRegistration> findById(UUID id);

    void removeById(UUID id);
}
