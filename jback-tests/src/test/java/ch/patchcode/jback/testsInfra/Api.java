package ch.patchcode.jback.testsInfra;

import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.session.LoginData;
import ch.patchcode.jback.api.verification.VerificationCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static ch.patchcode.jback.testsInfra.ConstantVerificationCodeProvider.VERIFICATION_CODE;
import static ch.patchcode.jback.testsInfra.Some.initialRegistrationData;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Provides Java methods for REST API calls to the JBack backend.
 */
public class Api {

    private final WebTestClient webClient;
    private final ObjectMapper mapper;

    public Api(int port, ObjectMapper mapper) {

        this.webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .filter(new CookieTrackerFilter())
                .build();
        this.mapper = mapper;
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
    public CallResult postPersonMe(Person.Draft draft) throws Exception {

        return new CallResult(

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

    /**
     * Calls the <tt>/api/v1/session/login</tt> endpoint.
     * @return
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

    /**
     * A bunch of pre-defined, standard workflows, for your convenience.
     */
    public final Workflows workflows = new Workflows();

    /**
     * A bunch of pre-defined, standard workflows, for your convenience.
     */
    public final class Workflows {

        public CallResult registerWithCode(InitialRegistrationData content, String code) throws Exception {

            var info = postRegistration(content)
                    .andAssumeGoodAndReturn(PendingRegistrationInfo.class);

            return putVerificationCode(
                    info.getPendingRegistrationId(),
                    VerificationCode.of(code)
            );
        }


        public CallResult registerAndPostMeToPersons(Person.MeDraft content) throws Exception {

            InitialRegistrationData initialData = InitialRegistrationData.Builder
                    .from(initialRegistrationData())
                    .setFirstName(content.getFirstName())
                    .setLastName(content.getLastName())
                    .build();

            var info = postRegistration(initialData)
                    .andAssumeGoodAndReturn(PendingRegistrationInfo.class);

            putVerificationCode(
                    info.getPendingRegistrationId(),
                    VerificationCode.of(VERIFICATION_CODE)
            ).andAssumeGoodAndReturn();

            return postPersonMe(content);
        }
    }
}
