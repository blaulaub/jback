package ch.patchcode.jback.jpa.entities.spring;

import ch.patchcode.jback.jpa.entities.PersonJpa;
import ch.patchcode.jback.jpa.entities.PersonalAuthenticationJpa;
import ch.patchcode.jback.security.entities.PendingRegistration;
import ch.patchcode.jback.security.entities.VerificationMean;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class SomeData {

    public static PendingRegistration.Draft somePendingRegistrationDraft(VerificationMean verificationMean) {

        return new PendingRegistration.Draft.Builder()
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setExpiresAt(Instant.now().plus(Duration.ofMinutes(5)))
                .setVerificationCode("1234")
                .setVerificationMean(verificationMean)
                .build();
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
