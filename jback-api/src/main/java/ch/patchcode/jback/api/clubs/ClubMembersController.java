package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.core.clubs.ClubMemberService;
import ch.patchcode.jback.core.clubs.ClubNotFoundException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs/{id}/members")
@Api(tags = "Clubs")
public class ClubMembersController {

    private final ClubMemberService clubMemberService;

    @Autowired
    public ClubMembersController(
            ClubMemberService clubMemberService
    ) {

        this.clubMemberService = clubMemberService;
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAN_ASSIGN_MEMBER')")
    public void putMember(
            @PathVariable UUID id,
            @RequestBody Person person
    ) throws NotFoundException {

        try {

            clubMemberService.addMember(id, person.toDomain());
        } catch (ClubNotFoundException e) {

            throw new NotFoundException();
        }
    }
}
