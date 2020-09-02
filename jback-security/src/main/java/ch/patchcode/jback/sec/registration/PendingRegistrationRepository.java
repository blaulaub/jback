package ch.patchcode.jback.sec.registration;

import java.util.Optional;
import java.util.UUID;

public interface PendingRegistrationRepository {

    PendingRegistration.Id save(PendingRegistration pendingRegistration);

    Optional<PendingRegistration> findById(UUID id);

    void removeById(UUID id);
}
