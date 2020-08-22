package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.ApiTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonsController.class)
@ContextConfiguration(classes = {ApiTestConfiguration.class})
//@AutoConfigureMockMvc
class PersonsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void test() throws Exception {
        var id = UUID.randomUUID();
        mvc.perform(get("/persons/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }
}