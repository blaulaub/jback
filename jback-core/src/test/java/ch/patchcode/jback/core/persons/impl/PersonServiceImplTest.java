package ch.patchcode.jback.core.persons.impl;

import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonRepository;
import ch.patchcode.jback.secBase.VerificationMean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PersonServiceImplTest {

    @Mock
    private PersonRepository<VerificationMean> personRepository;

    @InjectMocks
    private PersonServiceImpl<VerificationMean, ?> service;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void getPerson_thatExists_returnsPerson() {

        // arrange
        var id = UUID.randomUUID();
        var expected = Optional.of(new Person.Builder<>().buildPartial());
        when(personRepository.findById(eq(id))).thenReturn(expected);

        // act
        var actual = service.getPerson(id);

        // assert
        assertEquals(expected, actual);
    }
}