package ch.patchcode.jback.coreRepositories;

import ch.patchcode.jback.coreEntities.ClubMembershipApplication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubMembershipApplicationRepository {

    Optional<ClubMembershipApplication> findById(UUID id);

    List<ClubMembershipApplication> getApplications(UUID clubId, UUID afterApplicationId, int size);

    ClubMembershipApplication create(ClubMembershipApplication.Draft draft);
}
