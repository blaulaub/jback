package ch.patchcode.jback.core.persons;

import ch.patchcode.jback.secBase.secModelImpl.Principal;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {

    Optional<Person> getPerson(UUID id);

    /**
     * Creates a new {@link Person}, usually with empty {@link Person#getPrincipals()}.
     */
    Person create(Person.Draft draft);

    /**
     * Creates a new {@link Person}, with {@link Person#getPrincipals()} containing the given principal.
     */
    Person createClient(Person.Draft draft, Principal principal);
}
