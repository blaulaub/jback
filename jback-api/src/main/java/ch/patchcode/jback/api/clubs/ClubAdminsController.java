package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.exceptions.ForbiddenException;
import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.api.persons.Person;
import ch.patchcode.jback.core.NotAllowedException;
import ch.patchcode.jback.core.clubs.ClubMemberService;
import ch.patchcode.jback.core.clubs.ClubNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs/{id}/admins")
public class ClubAdminsController {

    private final ClubMemberService clubMemberService;

    @Autowired
    public ClubAdminsController(
            ClubMemberService clubMemberService
    ) {

        this.clubMemberService = clubMemberService;
    }

    @PutMapping
    public void putAdmin(
            @PathVariable UUID id,
            @RequestBody Person person
    ) throws NotFoundException, ForbiddenException {

        try {

            clubMemberService.addAdmin(id, person.toDomain());

        } catch (ClubNotFoundException e) {

            throw new NotFoundException();

        } catch (NotAllowedException e) {

            throw new ForbiddenException();
        }
    }
}
