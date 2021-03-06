package ch.patchcode.jback.tests;

import ch.patchcode.jback.api.clubs.Club;
import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.api.session.LoginData;
import ch.patchcode.jback.api.session.LoginResponse;
import ch.patchcode.jback.api.verification.VerificationByUsernameAndPassword;
import ch.patchcode.jback.presentation.Perspective;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;

import static ch.patchcode.jback.testsInfra.ConstantVerificationCodeProvider.VERIFICATION_CODE;
import static ch.patchcode.jback.testsInfra.Some.*;
import static org.hamcrest.Matchers.equalTo;

@ApiTestConfiguration.Apply
class SessionTest {

    private final Api api;

    @Autowired
    public SessionTest(@LocalServerPort int port, ObjectMapper mapper, Environment env) {
        this.api = new Api(port, mapper, env);
    }

    @Test
    @DisplayName("the initial session perspective is GUEST")
    void initialPerspectiveIsGuest() {

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.GUEST.toString()));
    }

    @Test
    @DisplayName("after putting wrong verification perspective remains GUEST")
    void afterPuttingWrongVerificationSessionRemainsGuest() throws Exception {

        // arrange
        final String wrongCode = VERIFICATION_CODE + 1;
        api.workflows.registerWithCode(initialRegistrationData(), wrongCode);

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.GUEST.toString()));
    }

    @Test
    @DisplayName("after putting correct verification perspective becomes ENROLLING")
    void afterPuttingCorrectVerificationSessionBecomesEnrolling() throws Exception {

        // arrange
        api.workflows.registerWithCode(initialRegistrationData(), VERIFICATION_CODE).andAssumeGoodAndReturn();

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.ENROLLING.toString()));
    }

    @Test
    @DisplayName("after logout from ENROLLING perspective becomes GUEST")
    void afterLogoutFromEnrollingPerspectiveBecomesGuest() throws Exception {

        // arrange
        api.workflows.registerWithCode(initialRegistrationData(), VERIFICATION_CODE).andAssumeGoodAndReturn();
        api.postLogout().andAssumeGoodAndReturn();

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.GUEST.toString()));
    }

    @Test
    @DisplayName("after posting me perspective becomes MEMBER")
    void afterPostingMePerspectiveBecomesMember() throws Exception {

        // arrange
        api.workflows.registerAndPostMeToPersons(personWithPasswordDraft()).andAssumeGoodAndReturn();

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.MEMBER.toString()));
    }

    @Test
    @DisplayName("can login with username and password after registering me")
    void canLoginWithUsernameAndPasswordAfterRegisteringMe() throws Exception {

        // arrange
        var registrationData = personWithPasswordDraft();
        api.workflows.registerAndPostMeToPersons(registrationData).andAssumeGoodAndReturn();
        api.postLogout();
        var loginData = new LoginData.Builder()
                .setUserIdentification(registrationData.getUsername())
                .setVerificationMean(VerificationByUsernameAndPassword.Draft.create(
                        registrationData.getUsername(),
                        registrationData.getPassword()))
                .build();

        // act
        var result = api.postLogin(loginData).andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.kind").value(equalTo(LoginResponse.Kind.SUCCESS.toString()));
    }

    @Test
    @DisplayName("can login with superuser credentials")
    void canLoginWithSuperuserCredentials() throws Exception {

        // act
        var result = api.workflows.loginAsSuperuser().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.kind").value(equalTo(LoginResponse.Kind.SUCCESS.toString()));
    }

    @Test
    @DisplayName("superuser perspective is MEMBER")
    void superuserPerspectiveIsMember() throws Exception {

        // arrange
        api.workflows.loginAsSuperuser().andAssumeGoodAndReturn();

        // act
        var result = api.getSession().andReturn();

        // assert
        result
                .expectStatus().isOk()
                .expectBody().jsonPath("$.perspective").value(equalTo(Perspective.MEMBER.toString()));
    }

    @Test
    @DisplayName("after assigning to club person gets member role for the club")
    void afterAssigningToClubPersonGetsMemberRoleForTheClub() throws Exception {

        // arrange

        // - arrange basic data
        var registrationData = personWithPasswordDraft();
        Club.Draft draft = minimalisticClubDraft();

        // - arrange person, club, and membership
        var person = api.workflows.registerAndPostMeToPersons(registrationData).andAssumeGoodAndReturn(Person.class);
        api.workflows.loginAsSuperuser().andAssumeGoodAndReturn();
        var club = api.postClub(draft).andAssumeGoodAndReturn(Club.class);
        api.putMember(club.getId(), person).andAssumeGoodAndReturn();

        // - arrange login as (member) person
        api.workflows.loginAsMeBy(registrationData).andAssumeGoodAndReturn();

        // act
        var result = api.getRoles().andReturn();

        // assert

        result
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.roles").exists()
                .jsonPath("$.roles").isArray()
                .jsonPath("$.roles[0]").exists()
                .jsonPath("$.roles[0].type").isEqualTo("member")
                .jsonPath("$.roles[0].person.id").isEqualTo(person.getId().toString())
                .jsonPath("$.roles[0].person.firstName").isEqualTo(person.getFirstName())
                .jsonPath("$.roles[0].person.lastName").isEqualTo(person.getLastName())
                .jsonPath("$.roles[0].club.id").isEqualTo(club.getId().toString())
                .jsonPath("$.roles[0].club.name").isEqualTo(club.getName())
                .jsonPath("$.roles[1]").doesNotExist();
    }
}
