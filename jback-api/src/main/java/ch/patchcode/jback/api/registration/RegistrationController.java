package ch.patchcode.jback.api.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Allows users to self-register.
 */
@RestController
@RequestMapping("registration")
public class RegistrationController {

    private final static Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    @PostMapping
    public void putInitialRegistration(@RequestBody InitialRegistrationData data) {
        LOG.info("received registration " + data);
    }
}
