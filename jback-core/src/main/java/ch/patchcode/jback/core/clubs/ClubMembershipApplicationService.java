package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.coreEntities.ClubMembershipApplication;

public interface ClubMembershipApplicationService {

    void fileNewApplication(ClubMembershipApplication.Draft draft);
}
