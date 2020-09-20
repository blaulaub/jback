package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.presentation.Authentication;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface PersonalAuthentication extends
        ch.patchcode.jback.security.entities.PersonalAuthentication,
        Authentication {

    static PersonalAuthentication fromDomain(ch.patchcode.jback.security.entities.PersonalAuthentication auth) {
        return new Builder()
                .setHolder(auth.getHolder())
                .addAllMeans(auth.getMeans())
                .build();
    }

    class Builder extends PersonalAuthentication_Builder {};
}
