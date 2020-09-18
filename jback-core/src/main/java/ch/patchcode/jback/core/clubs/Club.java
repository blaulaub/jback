package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.secBase.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@FreeBuilder
public interface Club<TVerificationMean extends VerificationMean> {

    UUID getId();

    String getName();

    Optional<Person<TVerificationMean>> getContact();

    Optional<URI> getUrl();

    class Builder<TVerificationMean extends VerificationMean> extends Club_Builder<TVerificationMean> {
    }

    @FreeBuilder
    interface Draft<TVerificationMean extends VerificationMean> {

        String getName();

        Optional<Person<TVerificationMean>> getContact();

        Optional<URI> getUrl();

        class Builder<TVerificationMean extends VerificationMean> extends Club_Draft_Builder<TVerificationMean> {
        }
    }
}
