package ch.patchcode.jback.core.persons.impl;

import ch.patchcode.jback.core.persons.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl service;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void getPerson_thatExists_returnsPerson() {

        // arrange
        // TODO is returning null the desired behavior, or throwing an exception?
        when(personRepository.findOne(any())).thenReturn(null);

        // act
        var person = service.getPerson(UUID.randomUUID());

        // assert
        Assertions.assertNull(person);
    }
}