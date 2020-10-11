package ch.patchcode.jback.testsInfra;

import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.verification.VerificationCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class Api {

    private final static Logger LOG = LoggerFactory.getLogger(Api.class);

    private final WebTestClient webClient;
    private final ObjectMapper mapper;

    public Api(int port, ObjectMapper mapper) {

        this.webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .filter(new CookieTrackerFilter())
                .build();
        this.mapper = mapper;
    }

    public Call getSession() {

        return new Call(

                // call
                webClient.get()
                        .uri("/api/v1/session")
                        .accept(MediaType.APPLICATION_JSON)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .exchange(),

                // expect
                asList(
                        it -> it.expectStatus().isOk(),
                        it -> it.expectBody().jsonPath("$.perspective").exists()
                )
        );

    }

    public Call postRegistration(InitialRegistrationData registration) throws Exception {

        return new Call(

                // call
                webClient.post()
                        .uri("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .bodyValue(mapper.writeValueAsString(registration))
                        .exchange(),

                // expect
                asList(
                        it -> it.expectStatus().isOk(),
                        it -> it.expectBody().jsonPath("$.pendingRegistrationId").exists()
                )
        );
    }

    public Call putVerificationCode(UUID pendingRegistrationId, VerificationCode verificationCode) throws Exception {

        return new Call(

                // call
                webClient.put().uri("/api/v1/registration/{id}", pendingRegistrationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .bodyValue(mapper.writeValueAsString(verificationCode))
                        .exchange(),

                // expect
                singletonList(it -> it.expectStatus().isOk())
        );
    }

    public Call postPersonMe(Person.Draft draft) throws Exception {

        return new Call(

                // call
                webClient.post()
                        .uri("/api/v1/persons/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .bodyValue(mapper.writeValueAsString(draft))
                        .exchange(),

                // expect
                asList(
                        it -> it.expectStatus().isOk(),
                        it -> it.expectBody().jsonPath("$.id").exists()
                )
        );

    }

    public Call postLogout() {

        return new Call(

                // call
                webClient.post().uri("/api/v1/session/logout")
                        .exchange(),

                // expect
                singletonList(it -> it.expectStatus().isOk())
        );
    }

    public final class Call {

        private final WebTestClient.ResponseSpec result;
        private final List<Function<WebTestClient.ResponseSpec, ?>> expectations;

        public Call(
                WebTestClient.ResponseSpec result,
                List<Function<WebTestClient.ResponseSpec, ?>> expectations
        ) {
            this.result = result;
            this.expectations = expectations;
        }

        public WebTestClient.ResponseSpec andReturn() {
            return result;
        }

        public WebTestClient.ResponseSpec andAssumeGoodAndReturn() {
            try {
                for (Function<WebTestClient.ResponseSpec, ?> x : expectations) {
                    x.apply(result);
                }
            } catch (Exception e) {
                assumeTrue(false);
            }
            return result;
        }

        public <T> T andAssumeGoodAndReturn(Class<T> clazz) throws Exception {

            byte[] body = this.andAssumeGoodAndReturn().returnResult(clazz).getResponseBodyContent();
            return mapper.readValue(body, clazz);
        }
    }
}
