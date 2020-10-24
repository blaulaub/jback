package ch.patchcode.jback.testsInfra;

import ch.patchcode.jback.api.clubs.Club;
import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.api.persons.PersonWithPasswordDraft;
import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.verification.VerificationByConsole;

import java.util.Random;

import static java.util.Arrays.asList;

public abstract class Some {

    private final static Random RND = new Random();

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

    public static PersonWithPasswordDraft personWithPasswordDraft() {
        return PersonWithPasswordDraft.of(
                "Tom",
                "Sawyer",
                asList("Technoparkstrasse 1", "8051 Zürich"),
                String.format("tsawyer_%08X", RND.nextInt()),
                "asdf"
        );
    }

    public static Club.Draft minimalisticClubDraft() {
        return new Club.Draft.Builder()
                .setName("Screaming Seagulls")
                .build();
    }
}
