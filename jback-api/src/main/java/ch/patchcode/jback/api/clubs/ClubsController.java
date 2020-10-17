package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.presentation.clubs.ClubService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs")
public class ClubsController {

    private final ClubService clubService;

    @Autowired
    public ClubsController(
            @Qualifier("presentation.clubService") ClubService clubService
    ) {

        this.clubService = clubService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAN_CREATE_CLUB')")
    public Club postNewClub(
            @RequestBody @ApiParam Club.Draft draft
    ) {

        throw new RuntimeException("not implemented");
    }

    @GetMapping("{id}")
    public Club getClubById(@PathVariable("id") UUID id) throws NotFoundException {

        return clubService.getClub(id).map(Club::from).orElseThrow(NotFoundException::new);
    }
}
