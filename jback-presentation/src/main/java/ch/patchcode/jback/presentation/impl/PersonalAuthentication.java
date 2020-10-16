package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.securityEntities.Principal;

import java.util.Optional;

public class PersonalAuthentication
        extends SpringAuthentication<ch.patchcode.jback.securityEntities.PersonalAuthentication> {

    public PersonalAuthentication(ch.patchcode.jback.securityEntities.PersonalAuthentication principal) {

        super(principal);
    }

    public static PersonalAuthentication fromDomain(ch.patchcode.jback.securityEntities.PersonalAuthentication auth) {

        return new PersonalAuthentication(auth);
    }

    public static Optional<PersonalAuthentication> fromDomain(Principal principal) {

        return Optional.of(principal)
                .filter(ch.patchcode.jback.securityEntities.PersonalAuthentication.class::isInstance)
                .map(ch.patchcode.jback.securityEntities.PersonalAuthentication.class::cast)
                .map(PersonalAuthentication::fromDomain);
    }
}
