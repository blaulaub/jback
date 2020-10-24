package ch.patchcode.jback.testsInfra;

import ch.patchcode.jback.api.clubs.Club;
import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.session.LoginData;
import ch.patchcode.jback.api.verification.VerificationCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Provides Java methods for REST API calls to the JBack backend.
 */
public class Api {

    private final WebTestClient webClient;
    private final ObjectMapper mapper;

    /**
     * A bunch of pre-defined, standard workflows, for your convenience.
     */
    public final ApiWorkflows workflows;

    public Api(int port, ObjectMapper mapper, Environment env) {

        this.webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .filter(new CookieTrackerFilter())
                .build();
        this.mapper = mapper;

        this.workflows = new ApiWorkflows(this, env);
    }

    /**
     * Calls the <tt>/api/v1/session</tt> endpoint.
     */
    public CallResult getSession() {

        return new CallResult(

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

    /**
     * Calls the <tt>/api/v1/registration</tt> endpoint.
     */
    public CallResult postRegistration(InitialRegistrationData registration) throws Exception {

        return new CallResult(

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

    /**
     * Calls the <tt>/api/v1/registration/{id}</tt> endpoint.
     */
    public CallResult putVerificationCode(UUID pendingRegistrationId, VerificationCode verificationCode) throws Exception {

        return new CallResult(

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

    /**
     * Calls the <tt>/api/v1/persons/me</tt> endpoint.
     */
    public CallResult postPersonWithPassword(Person.Draft draft) throws Exception {

        return new CallResult(

                // call
                webClient.post()
                        .uri("/api/v1/persons/with-password")
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

    /**
     * Calls the <tt>/api/v1/clubs</tt> endpoint.
     */
    public CallResult postClub(Club.Draft draft) throws Exception {

        return new CallResult(

                // call
                webClient.post()
                        .uri("/api/v1/clubs")
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

    /**
     * Calls the <tt>/api/v1/clubs/{id}</tt> endpoint.
     */
    public CallResult getClub(UUID clubId) {

        return new CallResult(

                // call
                webClient.get()
                        .uri("/api/v1/clubs/{id}", clubId)
                        .accept(MediaType.APPLICATION_JSON)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .exchange(),

                // expect
                asList(
                        it -> it.expectStatus().isOk(),
                        it -> it.expectBody().jsonPath("$.id").isEqualTo(clubId.toString()),
                        it -> it.expectBody().jsonPath("$.name").exists()
                )
        );
    }

    /**
     * Calls the <tt>/api/v1/clubs/{id}/members</tt> endpoint.
     */
    public CallResult putMember(UUID clubId, Person person) throws Exception {

        return new CallResult(

                // call
                webClient.put().uri("/api/v1/clubs/{id}/members", clubId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .bodyValue(mapper.writeValueAsString(person))
                        .exchange(),

                // expect
                singletonList(it -> it.expectStatus().isOk())
        );
    }

    /**
     * Calls the <tt>/api/v1/clubs/{id}/admins</tt> endpoint.
     */
    public CallResult putAdmin(UUID clubId, Person person) throws Exception {

        return new CallResult(

                // call
                webClient.put().uri("/api/v1/clubs/{id}/admins", clubId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .bodyValue(mapper.writeValueAsString(person))
                        .exchange(),

                // expect
                singletonList(it -> it.expectStatus().isOk())
        );
    }

    /**
     * Calls the <tt>/api/v1/session/login</tt> endpoint.
     */
    public CallResult postLogin(LoginData loginData) throws Exception {

        return new CallResult(

                // call
                webClient.post().uri("/api/v1/session/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .bodyValue(mapper.writeValueAsString(loginData))
                        .exchange(),

                // expect
                singletonList(it -> it.expectStatus().isOk())
        );
    }

    /**
     * Calls the <tt>/api/v1/session/roles</tt> endpoint.
     */
    public CallResult getRoles() {

        return new CallResult(

                // call
                webClient.get()
                        .uri("/api/v1/session/roles")
                        .accept(MediaType.APPLICATION_JSON)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .exchange(),

                // expect
                asList(
                        it -> it.expectStatus().isOk(),
                        it -> it.expectBody().jsonPath("$").isArray()
                )
        );
    }

    /**
     * Calls the <tt>/api/v1/session/roles</tt> endpoint.
     */
    public CallResult getCurrentRole() {

        return new CallResult(

                // call
                webClient.get()
                        .uri("/api/v1/session/currentRole")
                        .accept(MediaType.APPLICATION_JSON)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .exchange(),

                // expect
                asList(
                        it -> it.expectStatus().isOk(),
                        it -> it.expectBody().jsonPath("$.type").exists(),
                        it -> it.expectBody().jsonPath("$.person").exists(),
                        it -> it.expectBody().jsonPath("$.club").exists()
                )
        );
    }

    /**
     * Calls the <tt>/api/v1/session/logout</tt> endpoint.
     */
    public CallResult postLogout() {

        return new CallResult(

                // call
                webClient.post().uri("/api/v1/session/logout")
                        .exchange(),

                // expect
                singletonList(it -> it.expectStatus().isOk())
        );
    }

    /**
     * Represents a REST call result of this {@link Api}.
     */
    public final class CallResult {

        private final WebTestClient.ResponseSpec result;
        private final List<Function<WebTestClient.ResponseSpec, ?>> expectations;

        public CallResult(
                WebTestClient.ResponseSpec result,
                List<Function<WebTestClient.ResponseSpec, ?>> expectations
        ) {
            this.result = result;
            this.expectations = expectations;
        }

        public WebTestClient.ResponseSpec andReturn() {
            return result;
        }

        /**
         * To be used during test arrangement, this makes the positive outcome of the {@link CallResult}
         * a test assumption.
         */
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
