package ch.patchcode.jback.securityEntities.authentications;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationByConsole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersonalAuthenticationTest {

    @Test
    @DisplayName("holder is included in persons even when not explicitly added")
    void holderIsIncludedInPersonsEvenWhenNotExplicitlyAdded() {

        // arrange
        var holder = somePerson();

        var authentication = new PersonalAuthentication.Builder()
                .setHolder(holder)
                .addMeans(someVerificationMean())
                .build();

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

        var authentication = new PersonalAuthentication.Builder()
                .setHolder(holder)
                .addPersons(holder)
                .addMeans(someVerificationMean())
                .build();

        // act
        var persons = authentication.getPersons();

        // assert
        assertEquals(1, persons.size());
        assertTrue(persons.contains(holder));
    }

    private Person somePerson() {
        return new Person.Builder()
                .setId(UUID.randomUUID())
                .setFirstName("Karl")
                .setLastName("Valentin")
                .build();
    }

    private static VerificationByConsole someVerificationMean() {
        return new VerificationByConsole.Builder().setId(UUID.randomUUID()).build();
    }
}