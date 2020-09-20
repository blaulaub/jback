package ch.patchcode.jback.jpa.util;

import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.jpa.personalAuthentications.PersonalAuthenticationJpa;
import ch.patchcode.jback.jpa.registration.RegistrationJpa;
import ch.patchcode.jback.security.secBaseImpl.PendingRegistration;
import ch.patchcode.jback.security.entities.VerificationMean;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class SomeData {

    public static PendingRegistration.Draft somePendingRegistrationDraft() {

        return somePendingRegistrationDraft(new VerificationMean.VerificationByConsole());
    }

    public static PendingRegistration.Draft somePendingRegistrationDraft(VerificationMean verificationMean) {

        return new PendingRegistration.Draft.Builder()
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

    public static PersonalAuthenticationJpa principalOf(
            List<PersonJpa> persons,
            List<String> authorities
    ) {
        var principal = new PersonalAuthenticationJpa();
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
