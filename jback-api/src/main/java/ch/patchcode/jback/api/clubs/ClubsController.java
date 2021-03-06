package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.core.clubs.ClubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/clubs")
@Api(tags = "Clubs")
public class ClubsController {

    private final ClubService clubService;

    @Autowired
    public ClubsController(ClubService clubService) {

        this.clubService = clubService;
    }

    @GetMapping
    public List<Club> getClubs(@RequestParam String pattern) {

        return clubService.find(pattern).stream().map(Club::fromDomain).collect(toList());
    }

    @PostMapping
    public Club postNewClub(
            @RequestBody @ApiParam Club.Draft draft
    ) {

        return Club.fromDomain(clubService.create(draft.toDomain()));
    }

    @GetMapping("{id}")
    public Club getClubById(@PathVariable("id") UUID id) throws NotFoundException {

        return clubService.getClub(id).map(Club::fromDomain).orElseThrow(NotFoundException::new);
    }
}
