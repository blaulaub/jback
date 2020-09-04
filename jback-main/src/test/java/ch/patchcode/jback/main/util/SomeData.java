package ch.patchcode.jback.main.util;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.registration.VerificationMean;

public class SomeData {
    public static InitialRegistrationData someInitialRegistrationData() {
        return new InitialRegistrationData.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setVerificationMean(new VerificationMean.VerificationByConsole.Builder().build())
                .build();
    }
}
