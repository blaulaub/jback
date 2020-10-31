package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.coreEntities.ClubMembershipApplication;
import ch.patchcode.jback.coreEntities.Page;

public interface ClubMembershipApplicationService {

    Page<ClubMembershipApplication> getApplications(int page, int size);

    void fileNewApplication(ClubMembershipApplication.Draft draft);
}
