package ch.patchcode.jback.coreEntities;

import java.util.Optional;
import java.util.UUID;

public interface ClubMembershipApplicationRepository {

    Optional<ClubMembershipApplication> findById(UUID id);

    Page<ClubMembershipApplication> getApplications(int page, int size);

    ClubMembershipApplication create(ClubMembershipApplication.Draft draft);
}
