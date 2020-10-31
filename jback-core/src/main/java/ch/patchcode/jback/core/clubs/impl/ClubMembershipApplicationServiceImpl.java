package ch.patchcode.jback.core.clubs.impl;

import ch.patchcode.jback.core.clubs.ClubMembershipApplicationService;
import ch.patchcode.jback.coreEntities.ClubMembershipApplication;
import ch.patchcode.jback.coreEntities.ClubMembershipApplicationRepository;
import ch.patchcode.jback.coreEntities.Page;

public class ClubMembershipApplicationServiceImpl implements ClubMembershipApplicationService {

    private final ClubMembershipApplicationRepository clubMembershipApplicationRepository;

    public ClubMembershipApplicationServiceImpl(ClubMembershipApplicationRepository clubMembershipApplicationRepository) {
        this.clubMembershipApplicationRepository = clubMembershipApplicationRepository;
    }

    @Override
    public Page<ClubMembershipApplication> getApplications(int page, int size) {

        return clubMembershipApplicationRepository.getApplications(page, size);
    }

    @Override
    public void fileNewApplication(ClubMembershipApplication.Draft draft) {

        clubMembershipApplicationRepository.create(draft);
    }
}
