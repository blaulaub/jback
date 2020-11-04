package ch.patchcode.jback.core.clubs.impl;

import ch.patchcode.jback.core.clubs.ClubMembershipApplicationNotFoundException;
import ch.patchcode.jback.core.clubs.ClubMembershipApplicationService;
import ch.patchcode.jback.coreEntities.ClubMembershipApplication;
import ch.patchcode.jback.coreRepositories.ClubMembershipApplicationRepository;

import java.util.List;
import java.util.UUID;

public class ClubMembershipApplicationServiceImpl implements ClubMembershipApplicationService {

    private final ClubMembershipApplicationRepository clubMembershipApplicationRepository;

    public ClubMembershipApplicationServiceImpl(ClubMembershipApplicationRepository clubMembershipApplicationRepository) {
        this.clubMembershipApplicationRepository = clubMembershipApplicationRepository;
    }

    @Override
    public List<ClubMembershipApplication> getApplications(UUID clubId, UUID afterApplicationId, int size) {

        return clubMembershipApplicationRepository.getApplications(clubId, afterApplicationId, size);
    }

    @Override
    public ClubMembershipApplication getApplication(UUID clubId, UUID applicationId) throws ClubMembershipApplicationNotFoundException {
        return clubMembershipApplicationRepository
                .findById(applicationId)
                .filter(it -> it.getClub().getId().equals(clubId))
                .orElseThrow(ClubMembershipApplicationNotFoundException::new);
    }

    @Override
    public void fileNewApplication(ClubMembershipApplication.Draft draft) {

        clubMembershipApplicationRepository.create(draft);
    }
}
