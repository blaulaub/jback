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

    Optional<Person> getPerson(UUID id);

    /**
     * Creates a new {@link Person}.
     */
    Person create(Person.Draft draft);

    /**
     * Creates a new {@link Person} under the given principal.
     */
    Person createClient(Person.Draft draft, TPrincipal principal);
}
