package ch.patchcode.jback.api.util;

import ch.patchcode.jback.core.clubs.Club;
import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.core.persons.Person;

import java.net.URI;
import java.util.UUID;

public class SomeData {

    public static Club.Builder someClubBuilder() throws Exception {
        return Club.Builder.from(someClub());
    }

    public static Club someClub() throws Exception {
        return new Club.Builder()
                .setId(UUID.randomUUID())
                .setName("Flying Eagles")
                .setUrl(new URI("http://www.wikipedia.org"))
                .setContact(somePerson())
                .build();
    }

    public static Person.Builder somePersonBuilder() {
        return Person.Builder.from(somePerson());
    }

    public static Person somePerson() {
        return new Person.Builder()
                .setId(UUID.randomUUID())
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setAddress(someAddress())
                .build();
    }

    public static Address someAddress() {
        return new Address.Builder()
                .addLines(
                        "Technoparkstrasse 1",
                        "8051 Zürich"
                )
                .build();
    }
}
