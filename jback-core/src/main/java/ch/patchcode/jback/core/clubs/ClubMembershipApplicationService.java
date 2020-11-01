package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.coreEntities.ClubMembershipApplication;

import java.util.List;
import java.util.UUID;

public interface ClubMembershipApplicationService {

    List<ClubMembershipApplication> getApplications(UUID clubId, UUID afterApplicationId, int size);

    void fileNewApplication(ClubMembershipApplication.Draft draft);
}
