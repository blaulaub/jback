package ch.patchcode.jback.api.fakeServices;

import ch.patchcode.jback.core.registration.InitialRegistrationData;
import ch.patchcode.jback.core.registration.RegistrationService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceFake implements RegistrationService {

    @Override
    public void process(InitialRegistrationData toDomain) {
    }
}
