package ch.patchcode.jback.core.persons.impl;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonRepository;
import ch.patchcode.jback.core.persons.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person getPerson(UUID id) {
        return personRepository.findOne(id);
    }
}
