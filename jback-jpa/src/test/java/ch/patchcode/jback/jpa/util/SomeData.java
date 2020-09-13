package ch.patchcode.jback.jpa.util;

import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.jpa.principals.PrincipalJpa;
import ch.patchcode.jback.jpa.registration.RegistrationJpa;
import ch.patchcode.jback.secBase.PendingRegistration;
import ch.patchcode.jback.secBase.VerificationMean;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class SomeData {

    public static PendingRegistration somePendingRegistration() {

        return somePendingRegistration(new VerificationMean.VerificationByConsole());
    }

    public static PendingRegistration somePendingRegistration(VerificationMean verificationMean) {

        return new PendingRegistration.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(5)))
                .setVerificationCode("1234")
                .setVerificationMean(verificationMean)
                .build();
    }

    public static RegistrationJpa someRegistration() {
        
        RegistrationJpa registration = new RegistrationJpa.ConsoleRegistrationJpa();
        registration.setFirstName("Tom");
        registration.setLastName("Sawyer");
        registration.setVerificationCode("1234");
        registration.setExpiresAt(Instant.now().toEpochMilli());
        return registration;
    }

    public static PrincipalJpa principalOf(
            List<PersonJpa> persons,
            List<String> authorities
    ) {
        var principal = new PrincipalJpa();
        principal.setClients(persons);
        principal.setAuthorities(authorities);
        return principal;
    }

    public static PersonJpa personOf(String firstName, String lastName) {
        var person = new PersonJpa();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }
}
