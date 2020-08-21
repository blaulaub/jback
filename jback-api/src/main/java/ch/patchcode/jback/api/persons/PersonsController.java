package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.common.Address;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("persons")
public class PersonsController {

    @GetMapping("{id}")
    public Person getPerson(@PathVariable("id") UUID id) {

        return new Person.Builder()
                .setId(id)
                .setFirstName("Max")
                .setLastName("Mustermann")
                .setAddress(
                        new Address.Builder()
                                .setLines(new String[] {"Technoparkstrasse 1", "8051 Zürich"})
                                .build()
                ).build();
    }
}
