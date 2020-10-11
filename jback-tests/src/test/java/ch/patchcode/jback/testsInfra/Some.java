package ch.patchcode.jback.testsInfra;

import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.verification.VerificationByConsole;

import static java.util.Arrays.asList;

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

    public static Person.Draft personDraft() {
        return Person.Draft.of(
                "Tom",
                "Sawyer",
                asList("Technoparkstrasse 1", "8051 Zürich")
        );
    }
}
