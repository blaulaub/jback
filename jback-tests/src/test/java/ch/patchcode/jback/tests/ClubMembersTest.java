package ch.patchcode.jback.tests;

import ch.patchcode.jback.api.clubs.Club;
import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.testsInfra.Api;
import ch.patchcode.jback.testsInfra.ApiTestConfiguration;
import ch.patchcode.jback.testsInfra.Some;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;

import static ch.patchcode.jback.testsInfra.Some.meDraft;
import static ch.patchcode.jback.testsInfra.Some.minimalisticClubDraft;

@ApiTestConfiguration.Apply
public class ClubMembersTest {

    private final Api api;

    @Autowired
    public ClubMembersTest(@LocalServerPort int port, ObjectMapper mapper, Environment env) {
        this.api = new Api(port, mapper, env);
    }

    @Test
    @DisplayName("superuser can assign person as club member")
    public void superuserCanAssignPersonAsClubMember() throws Exception {

        // arrange
        Club.Draft draft = minimalisticClubDraft();

        var person = api.workflows.registerAndPostMeToPersons(meDraft()).andAssumeGoodAndReturn(Person.class);
        api.workflows.loginAsSuperuser().andAssumeGoodAndReturn();
        var club = api.postClub(draft).andAssumeGoodAndReturn(Club.class);

        // act
        var result = api.putMember(club.getId(), person).andReturn();

        // assert
        result.expectStatus().isOk();
    }

}
