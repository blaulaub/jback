package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.InitialRegistrationData;
import ch.patchcode.jback.core.registration.RegistrationService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public void process(InitialRegistrationData toDomain) {
        // - send the user some key to trigger the confirmation step
        // - store the pending request (with timeout)
        throw new RuntimeException("not implemented");
    }
}
