package ch.patchcode.jback.coreEntities;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubMembershipApplicationRepository {

    Optional<ClubMembershipApplication> findById(UUID id);

    List<ClubMembershipApplication> getApplications(UUID afterId, int size);

    ClubMembershipApplication create(ClubMembershipApplication.Draft draft);
}
