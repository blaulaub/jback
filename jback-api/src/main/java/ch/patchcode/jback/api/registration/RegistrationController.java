package ch.patchcode.jback.api.registration;

import ch.patchcode.jback.api.verification.VerificationCode;
import ch.patchcode.jback.presentation.AuthenticationManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Allows users to self-register.
 */
@RestController
@RequestMapping("/api/v1/registration")
@Api(tags = "Registration")
public class RegistrationController {

    private final static Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final AuthenticationManager authenticationManager;

    @Autowired
    public RegistrationController(
            AuthenticationManager authenticationManager
    ) {

        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    @ApiOperation(
            httpMethod = "POST",
            value = "submit personal information for registration",
            response = PendingRegistrationInfo.class
    )
    public PendingRegistrationInfo postInitialRegistration(
            @RequestBody @ApiParam InitialRegistrationData data
    ) {

        LOG.debug("processing registration request for {}", data);
        var id = authenticationManager.setupRegistration(data.toDomain());
        return PendingRegistrationInfo.of(id);
    }

    @PutMapping("{id}")
    public void putCompleteRegistration(
            @PathVariable("id") UUID id,
            @RequestBody @ApiParam VerificationCode verificationCode
    ) {

        LOG.debug("processing registration code for {}", id);
        authenticationManager.authenticate(id, verificationCode.toDomain());
    }
}
