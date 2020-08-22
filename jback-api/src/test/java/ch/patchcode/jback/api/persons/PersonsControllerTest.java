package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.ApiTestConfiguration;
import ch.patchcode.jback.api.fakeServices.PersonServiceFake;
import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.core.persons.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonsController.class)
@ContextConfiguration(classes = {ApiTestConfiguration.class})
class PersonsControllerTest {

    private final MockMvc mvc;
    private final PersonServiceFake personServiceFake;

    @Autowired
    public PersonsControllerTest(PersonServiceFake personServiceFake, MockMvc mvc) {
        this.personServiceFake = personServiceFake;
        this.mvc = mvc;
    }

    @Test
    void getPersonById_forExistingPerson_returnsPerson() throws Exception {

        // arrange
        var id = UUID.randomUUID();
        personServiceFake.putPerson(new Person.Builder()
                .setId(id)
                .setFirstName("Tom")
                .setLastName("Sawyer")
                .setAddress(
                        new Address.Builder()
                                .setLines(new String[]{
                                        "Technoparkstrasse 1",
                                        "8051 Zürich"
                                })
                                .build()
                )
                .build());

        // act
        ResultActions result = mvc.perform(get("/persons/{id}", id));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.firstName").value("Tom"))
                .andExpect(jsonPath("$.lastName").value("Sawyer"))
                .andExpect(jsonPath("$.address.lines[0]").value("Technoparkstrasse 1"))
                .andExpect(jsonPath("$.address.lines[1]").value("8051 Zürich"))
        ;
    }
}
