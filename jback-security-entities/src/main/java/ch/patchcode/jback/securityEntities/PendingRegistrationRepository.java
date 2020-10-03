package ch.patchcode.jback.securityEntities;

import java.util.Optional;
import java.util.UUID;

public interface PendingRegistrationRepository {

    PendingRegistration create(PendingRegistration.Draft pendingRegistration);

    Optional<PendingRegistration> findById(UUID id);

    void removeById(UUID id);
}
