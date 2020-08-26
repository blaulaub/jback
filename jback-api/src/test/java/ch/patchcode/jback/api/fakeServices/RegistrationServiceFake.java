package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.registration.InitialRegistrationData;
import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.core.registration.RegistrationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationServiceFake implements RegistrationService {

    @Override
    public PendingRegistration.Id process(InitialRegistrationData toDomain) {

        return PendingRegistration.Id.of(UUID.randomUUID());
    }
}
