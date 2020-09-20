package ch.patchcode.jback.core.persons;

import ch.patchcode.jback.core.entities.Person;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Principal;

import java.util.Optional;
import java.util.UUID;

public interface PersonService<
        TVerificationMean extends VerificationMean,
        TPrincipal extends Principal<TVerificationMean>
        > {

    Optional<Person<TVerificationMean>> getPerson(UUID id);

    /**
     * Creates a new {@link Person}, usually with empty {@link Person#getPrincipals()}.
     */
    Person<TVerificationMean> create(Person.Draft<TVerificationMean> draft);

    /**
     * Creates a new {@link Person}, with {@link Person#getPrincipals()} containing the given principal.
     */
    Person<TVerificationMean> createClient(Person.Draft<TVerificationMean> draft, TPrincipal principal);
}
