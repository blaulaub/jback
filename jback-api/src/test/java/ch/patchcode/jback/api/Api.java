package ch.patchcode.jback.api;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.presentation.Perspective;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
public class Api {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @Autowired
    public Api(MockMvc mvc, ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    public Call getSession() throws Exception {

        return new Call(

                // call
                mvc.perform(get("/api/v1/session")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")),

                // expect
                asList(
                        status().isOk(),
                        jsonPath("$.perspective").exists()
                )
        );

    }

    public Call postRegistration(InitialRegistrationData registration) throws Exception {

        return new Call(

                // call
                mvc.perform(post("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(mapper.writeValueAsString(registration))),

                // expect
                asList(
                        status().isOk(),
                        jsonPath("$.pendingRegistrationId").exists()));
    }

    public static final class Call {

        private final ResultActions result;
        private final List<ResultMatcher> expectations;

        public Call(ResultActions result, List<ResultMatcher> expectations) {
            this.result = result;
            this.expectations = expectations;
        }

        public ResultActions andReturn() {
            return result;
        }

        public ResultActions andAssumeGoodAndReturn() {
            try {
                for (ResultMatcher x : expectations) {
                    x.match(result.andReturn());
                }
            } catch (Exception e) {
                assumeTrue(false);
            }
            return result;
        }
    }
}
