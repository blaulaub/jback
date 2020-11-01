package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.coreEntities.ClubMembershipApplication;

import java.util.List;
import java.util.UUID;

public interface ClubMembershipApplicationService {

    List<ClubMembershipApplication> getApplications(UUID afterId, int size);

    void fileNewApplication(ClubMembershipApplication.Draft draft);
}
