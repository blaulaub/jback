package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.security.Authentication;
import ch.patchcode.jback.security.AuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static ch.patchcode.jback.api.persons.Person.fromDomain;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonsController {

    private final PersonService personService;
    private final AuthorizationManager authorizationManager;

    @Autowired
    public PersonsController(
            PersonService personService,
            AuthorizationManager authorizationManager
    ) {

        this.personService = personService;
        this.authorizationManager = authorizationManager;
    }

    @GetMapping("{id}")
    public Person getPersonById(@PathVariable("id") UUID id) throws NotFoundException {

        return personService.getPerson(id).map(Person::fromDomain).orElseThrow(NotFoundException::new);
    }

    /**
     * Lets an authorized user create a new person.
     *
     * @param draft contains information about the person
     * @return the new person
     */
    @PostMapping
    @PreAuthorize("hasAuthority('CAN_CREATE_PERSON')")
    public Person createPerson(@RequestBody Person.Draft draft) {

        var context = SecurityContextHolder.getContext();
        var callerAuth = (Authentication) context.getAuthentication();

        // looks like a mixed concern: the api has to combine core and security here
        var person = personService.create(draft.toDomain());
        context.setAuthentication(authorizationManager.tryUpgrade(callerAuth, person));

        return fromDomain(person);
    }
}
