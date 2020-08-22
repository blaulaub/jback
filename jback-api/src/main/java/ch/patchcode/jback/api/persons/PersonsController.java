package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.core.persons.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("persons")
public class PersonsController {

    private PersonService personService;

    @Autowired
    public PersonsController(PersonService personService) {

        this.personService = personService;
    }

    @GetMapping("{id}")
    public Person getPersonById(@PathVariable("id") UUID id) {

        // TODO null? rather 404
        return Optional.ofNullable(personService.getPerson(id)).map(Person::from).orElse(null);
    }
}
