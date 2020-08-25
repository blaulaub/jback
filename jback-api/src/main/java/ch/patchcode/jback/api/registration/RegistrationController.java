package ch.patchcode.jback.api.registration;

import ch.patchcode.jback.core.registration.PendingRegistration;
import ch.patchcode.jback.core.registration.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Allows users to self-register.
 */
@RestController
@RequestMapping("registration")
public class RegistrationController {

    private final static Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public PendingRegistrationInfo postInitialRegistration(@RequestBody InitialRegistrationData data) {

        var id = registrationService.process(data.toDomain()).getId();
        return PendingRegistrationInfo.of(id);
    }
}
