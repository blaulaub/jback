package ch.patchcode.jback.api.registration;

import ch.patchcode.jback.core.registration.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("{id}")
    public ResponseEntity<Void> putCompleteRegistration(
            @PathVariable("id") UUID id,
            @RequestBody VerificationCode verificationCode
    ) {

        var result = registrationService.confirm(id, verificationCode.getVerificationCode());
        switch (result) {
            case SUCCESS:
                return new ResponseEntity<>(HttpStatus.OK);
            case NOT_FOUND:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            case WRONG_CODE:
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            default:
                throw new RuntimeException();
        }
    }
}
