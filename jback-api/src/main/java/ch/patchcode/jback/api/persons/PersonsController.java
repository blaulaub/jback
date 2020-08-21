package ch.patchcode.jback.api.persons;

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

        throw new RuntimeException("not implemented");
    }
}
