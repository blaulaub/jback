package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.core.persons.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("persons")
public class PersonsController {

    private PersonService personService;

    @GetMapping("{id}")
    public Person getPerson(@PathVariable("id") UUID id) {

        return Person.from(personService.getPerson(id));
    }
}
