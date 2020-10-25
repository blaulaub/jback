package ch.patchcode.jback.securityEntities.authentications;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationByConsole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonalAuthenticationTest {

    @Test
    @DisplayName("holder is included in persons even when not explicitly added")
    void holderIsIncludedInPersonsEvenWhenNotExplicitlyAdded() {

        // arrange
        var holder = somePerson();

        var authentication = new PersonalAuthentication(
                UUID.randomUUID(),
                holder,
                emptyList(),
                singletonList(someVerificationMean()));

        // act
        var persons = authentication.getPersons();

        // assert
        assertEquals(1, persons.size());
        assertTrue(persons.contains(holder));
    }

    @Test
    @DisplayName("holder is included in persons once when explicitly added")
    void holderIsIncludedInPersonsOnceWhenExplicitlyAdded() {

        // arrange
        var holder = somePerson();

        var authentication = new PersonalAuthentication(
                UUID.randomUUID(),
                holder,
                singletonList(holder),
                singletonList(someVerificationMean()));

        // act
        var persons = authentication.getPersons();

        // assert
        assertEquals(1, persons.size());
        assertTrue(persons.contains(holder));
    }

    private Person somePerson() {
        return new Person(UUID.randomUUID(), "Karl", "Valentin", null);
    }

    private static VerificationByConsole someVerificationMean() {
        return new VerificationByConsole(UUID.randomUUID());
    }
}
