package ch.patchcode.jback.core.registration;

import java.util.UUID;

public interface PendingRegistrationRepository {

    PendingRegistration.Id save(PendingRegistration pendingRegistration);

    PendingRegistration findById(UUID id);
}
