package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.presentation.Authentication;
import ch.patchcode.jback.securityEntities.Principal;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;

@FreeBuilder
public abstract class PersonalAuthentication
        extends ch.patchcode.jback.securityEntities.PersonalAuthentication
        implements Authentication {

    public static PersonalAuthentication fromDomain(ch.patchcode.jback.securityEntities.PersonalAuthentication auth) {
        return new Builder()
                .setHolder(auth.getHolder())
                .addAllMeans(auth.getMeans())
                .build();
    }

    public static Optional<PersonalAuthentication> fromDomain(Principal principal) {

        return Optional.of(principal)
                .filter(ch.patchcode.jback.securityEntities.PersonalAuthentication.class::isInstance)
                .map(ch.patchcode.jback.securityEntities.PersonalAuthentication.class::cast)
                .map(PersonalAuthentication::fromDomain);
    }

    public static class Builder extends PersonalAuthentication_Builder {
    }
}
