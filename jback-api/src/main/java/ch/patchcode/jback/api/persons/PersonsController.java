package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.core.persons.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

        return personService.getPerson(id).map(Person::from).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Person createPerson(PersonDraft draft) {

        throw new RuntimeException("not implemented");
    }
}
