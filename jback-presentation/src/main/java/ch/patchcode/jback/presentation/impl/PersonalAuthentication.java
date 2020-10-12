package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.presentation.Authentication;
import ch.patchcode.jback.securityEntities.Principal;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;

@FreeBuilder
public interface PersonalAuthentication extends
        ch.patchcode.jback.securityEntities.PersonalAuthentication,
        Authentication {

    static PersonalAuthentication fromDomain(ch.patchcode.jback.securityEntities.PersonalAuthentication auth) {
        return new Builder()
                .setHolder(auth.getHolder())
                .addAllMeans(auth.getMeans())
                .build();
    }

    static Optional<PersonalAuthentication> fromDomain(Principal principal) {

        return Optional.of(principal)
                .filter(ch.patchcode.jback.securityEntities.PersonalAuthentication.class::isInstance)
                .map(ch.patchcode.jback.securityEntities.PersonalAuthentication.class::cast)
                .map(PersonalAuthentication::fromDomain);
    }

    class Builder extends PersonalAuthentication_Builder {
    }
}
