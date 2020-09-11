package ch.patchcode.jback.api.registration;

import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.security.AuthorizationManager;
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

    private final AuthorizationManager authorizationManager;

    @Autowired
    public RegistrationController(AuthorizationManager authorizationManager) {

        this.authorizationManager = authorizationManager;
    }

    @PostMapping
    public PendingRegistrationInfo postInitialRegistration(@RequestBody InitialRegistrationData data) {

        var id = authorizationManager.setupRegistration(data.toDomain()).getId();
        return PendingRegistrationInfo.of(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> putCompleteRegistration(
            @PathVariable("id") UUID id,
            @RequestBody VerificationCode verificationCode
    ) {

        authorizationManager.authenticate(PendingRegistration.Id.of(id), verificationCode.toDomain());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
