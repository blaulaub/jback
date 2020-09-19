package ch.patchcode.jback.presentation.clubs;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Club extends ch.patchcode.jback.core.clubs.Club<VerificationMean> {

    @Override
    UUID getId();

    @Override
    String getName();

    @Override
    Optional<Person<VerificationMean>> getContact();

    @Override
    Optional<URI> getUrl();

    static Club fromDomain(ch.patchcode.jback.core.clubs.Club<VerificationMean> club) {

        return new Builder()
                .setId(club.getId())
                .setName(club.getName())
                .setContact(club.getContact())
                .setUrl(club.getUrl())
                .build();
    }

    class Builder extends Club_Builder {
    }
}