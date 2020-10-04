package ch.patchcode.jback.api;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.verification.VerificationByConsole;

public abstract class Some {

    private Some() {
        throw new RuntimeException("I am a utility class");
    }


    public static InitialRegistrationData initialRegistrationData() {
        return InitialRegistrationData.of(
                "Gregor",
                "McGee",
                new VerificationByConsole.Draft.Builder().build()
        );
    }
}
