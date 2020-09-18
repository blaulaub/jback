package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.core.clubs.ClubService;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs")
public class ClubsController {

    private final ClubService<VerificationMean> clubService;

    @Autowired
    public ClubsController(ClubService<VerificationMean> clubService) {

        this.clubService = clubService;
    }

    @GetMapping("{id}")
    public Club getClubById(@PathVariable("id") UUID id) throws NotFoundException {

        return clubService.getClub(id).map(Club::from).orElseThrow(NotFoundException::new);
    }
}
