package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.exceptions.BadRequestException;
import ch.patchcode.jback.api.exceptions.ForbiddenException;
import ch.patchcode.jback.core.clubs.ClubMembershipApplicationService;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/clubs/{clubId}/membership-applications")
@Api(tags = "Clubs")
public class ClubMembershipApplicationsController {

    private final ClubMembershipApplicationService clubMembershipApplicationService;

    @Autowired
    public ClubMembershipApplicationsController(ClubMembershipApplicationService clubMembershipApplicationService) {

        this.clubMembershipApplicationService = clubMembershipApplicationService;
    }

    @GetMapping
    public List<ClubMembershipApplication> getApplications(
            @PathVariable UUID clubId,
            @RequestParam(name = "after", required = false) UUID afterApplicationId,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {

        return clubMembershipApplicationService.getApplications(clubId, afterApplicationId, size).stream()
                .map(ClubMembershipApplication::fromDomain)
                .collect(toList());
    }

    @PostMapping
    public void postNewApplication(
            @PathVariable UUID clubId,
            @RequestBody ClubMembershipApplication.Draft application
    ) throws ForbiddenException, BadRequestException {

        if (!clubId.equals(application.getClub().getId())) {
            throw new BadRequestException();
        }

        if (!principalFromRequest().getPersons().contains(application.getPerson().toDomain())) {
            throw new ForbiddenException();
        }

        this.clubMembershipApplicationService.fileNewApplication(application.toDomain());
    }

    private Principal principalFromRequest() {

        var context = SecurityContextHolder.getContext();
        return (Principal) context.getAuthentication();
    }
}
