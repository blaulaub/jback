package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.PendingRegistration;
import org.junit.jupiter.api.Test;

class ConsoleVerificationServiceImplTest {

    private final ConsoleVerificationServiceImpl service = new ConsoleVerificationServiceImpl();

    @Test
    void sendOut_smokeTest() {

        // arrange
        PendingRegistration registration = new PendingRegistration.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationCode("0815")
                .buildPartial();

        // act
        service.sendOut(registration);

        // assert - none (this is a smoke test)
    }
}
