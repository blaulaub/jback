package ch.patchcode.jback.api.registration;

import ch.patchcode.jback.secBase.SecurityManager;
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
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final static Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final SecurityManager securityManager;

    @Autowired
    public RegistrationController(SecurityManager securityManager) {

        this.securityManager = securityManager;
    }

    @PostMapping
    public PendingRegistrationInfo postInitialRegistration(@RequestBody InitialRegistrationData data) {

        var id = securityManager.setupRegistration(data.toDomain()).getId();
        return PendingRegistrationInfo.of(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> putCompleteRegistration(
            @PathVariable("id") UUID id,
            @RequestBody VerificationCode verificationCode
    ) {

        var result = securityManager.confirmRegistration(id, verificationCode.getVerificationCode());
        switch (result) {
            case CONFIRMED:
                return new ResponseEntity<>(HttpStatus.OK);
            case NOT_FOUND:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            case MISMATCH:
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            default:
                throw new RuntimeException();
        }
    }
}
