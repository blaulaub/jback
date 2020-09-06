package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.core.persons.PersonService;
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

    @Autowired
    public PersonsController(PersonService personService) {

        this.personService = personService;
    }

    @GetMapping("{id}")
    public Person getPersonById(@PathVariable("id") UUID id) throws NotFoundException {

        return personService.getPerson(id).map(Person::fromDomain).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAN_CREATE_PERSON')")
    public Person createPerson(@RequestBody Person.Draft draft) {

        var context = SecurityContextHolder.getContext();

        return fromDomain(personService.create(draft.toDomain()));
    }
}
