package ch.patchcode.jback.main.util;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.verification.VerificationByConsole;

public class SomeData {
    public static InitialRegistrationData someInitialRegistrationData() {
        return new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationByConsole.Builder().build())
                .build();
    }
}
