package ch.patchcode.jback.core.entities;

import ch.patchcode.jback.secBase.VerificationMean;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository<TVerificationMean extends VerificationMean> {

    Optional<Person<TVerificationMean>> findById(UUID id);

    Person<TVerificationMean> create(Person.Draft<TVerificationMean> draft);
}
