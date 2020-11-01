package ch.patchcode.jback.core.clubs.impl;

import ch.patchcode.jback.core.clubs.ClubMembershipApplicationService;
import ch.patchcode.jback.coreEntities.ClubMembershipApplication;
import ch.patchcode.jback.coreEntities.ClubMembershipApplicationRepository;

import java.util.List;
import java.util.UUID;

public class ClubMembershipApplicationServiceImpl implements ClubMembershipApplicationService {

    private final ClubMembershipApplicationRepository clubMembershipApplicationRepository;

    public ClubMembershipApplicationServiceImpl(ClubMembershipApplicationRepository clubMembershipApplicationRepository) {
        this.clubMembershipApplicationRepository = clubMembershipApplicationRepository;
    }

    @Override
    public List<ClubMembershipApplication> getApplications(UUID afterId, int size) {

        return clubMembershipApplicationRepository.getApplications(afterId, size);
    }

    @Override
    public void fileNewApplication(ClubMembershipApplication.Draft draft) {

        clubMembershipApplicationRepository.create(draft);
    }
}
