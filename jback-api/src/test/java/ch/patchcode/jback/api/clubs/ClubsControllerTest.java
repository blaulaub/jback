package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.ApiTestConfiguration;
import ch.patchcode.jback.api.fakeServices.ClubServiceFake;
import ch.patchcode.jback.core.common.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static ch.patchcode.jback.api.util.SomeData.someClubBuilder;
import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClubsController.class)
@ContextConfiguration(classes = {ApiTestConfiguration.class})
class ClubsControllerTest {

    private final MockMvc mvc;
    private final ClubServiceFake clubServiceFake;

    @Autowired
    public ClubsControllerTest(MockMvc mvc, ClubServiceFake clubServiceFake) {
        this.mvc = mvc;
        this.clubServiceFake = clubServiceFake;
    }

    @Test
    void getClubById_forExistingClub_returnsClub() throws Exception {

        // arrange
        var id = UUID.randomUUID();
        var club = someClubBuilder().setId(id).build();
        clubServiceFake.putClub(club);

        // act
        var result = mvc.perform(get("/clubs/{id}", id)
                .characterEncoding("utf-8"));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value(club.getName()))
                .andExpect(jsonPath("$.url").value(club.getUrl().get().toString()))
                .andExpect(jsonPath("$.contact.firstName").value(club.getContact().get().getFirstName()))
                .andExpect(jsonPath("$.contact.lastName").value(club.getContact().get().getLastName()))
                .andExpect(jsonPath("$.contact.address.lines", contains(club.getContact().get().getAddress().map(Address::getLines).get())));
    }
}