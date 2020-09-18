package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.ApiTestConfiguration;
import ch.patchcode.jback.api.fakeServices.PersonServiceFake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static ch.patchcode.jback.api.util.SomeData.somePersonBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonsController.class)
@ContextConfiguration(classes = {ApiTestConfiguration.class})
class PersonsControllerTest {

    private final MockMvc mvc;
    private final PersonServiceFake personServiceFake;

    @Autowired
    public PersonsControllerTest(MockMvc mvc, PersonServiceFake personServiceFake) {
        this.mvc = mvc;
        this.personServiceFake = personServiceFake;
    }

    @Test
    void getPersonById_forExistingPerson_returnsPerson() throws Exception {

        // arrange
        var id = UUID.randomUUID();
        var person = somePersonBuilder().setId(id).build();
        personServiceFake.putPerson(person);

        // act
        var result = mvc.perform(get("/api/v1/persons/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.firstName").value(person.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(person.getLastName()));
        // TODO this array match is ugly
        result
                .andExpect(jsonPath("$.address[0]").value(person.getAddress().get().getLines().get(0)))
                .andExpect(jsonPath("$.address[1]").value(person.getAddress().get().getLines().get(1)));
    }
}
