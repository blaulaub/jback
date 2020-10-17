package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.core.clubs.ClubMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs/{id}/members")
public class ClubMembersController {

    private final ClubMemberService clubMemberService;

    @Autowired
    public ClubMembersController(
            ClubMemberService clubMemberService
    ) {

        this.clubMemberService = clubMemberService;
    }

    @PutMapping
    public void putMember(
            @PathVariable UUID id,
            @RequestBody Person newMember
    ) {

        clubMemberService.addPerson(id, newMember.toDomain());
    }
}
