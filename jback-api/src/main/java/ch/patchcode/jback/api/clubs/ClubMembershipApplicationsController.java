package ch.patchcode.jback.api.clubs;

import ch.patchcode.jback.api.exceptions.BadRequestException;
import ch.patchcode.jback.api.exceptions.ForbiddenException;
import ch.patchcode.jback.core.clubs.ClubMembershipApplicationService;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs/{id}/membership-applications")
@Api(tags = "Clubs")
public class ClubMembershipApplicationsController {

    private final ClubMembershipApplicationService clubMembershipApplicationService;

    @Autowired
    public ClubMembershipApplicationsController(ClubMembershipApplicationService clubMembershipApplicationService) {

        this.clubMembershipApplicationService = clubMembershipApplicationService;
    }

    @PostMapping
    public void postNewApplication(
            @PathVariable UUID id,
            @RequestBody ClubMembershipApplication.Draft application
    ) throws ForbiddenException, BadRequestException {

        if (!id.equals(application.getClub().getId())) {
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
