package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.coreEntities.ClubMembershipApplication;
import ch.patchcode.jback.coreEntities.ClubMembershipApplicationRepository;
import ch.patchcode.jback.jpa.entities.ClubJpaRepository;
import ch.patchcode.jback.jpa.entities.ClubMembershipApplicationJpa;
import ch.patchcode.jback.jpa.entities.ClubMembershipApplicationJpaRepository;
import ch.patchcode.jback.jpa.entities.PersonJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClubMembershipApplicationRepoWrapper implements ClubMembershipApplicationRepository {

    private final ClubMembershipApplicationJpaRepository clubMembershipApplicationJpaRepository;
    private final PersonJpaRepository personJpaRepository;
    private final ClubJpaRepository clubJpaRepository;

    @Autowired
    public ClubMembershipApplicationRepoWrapper(
            ClubMembershipApplicationJpaRepository clubMembershipApplicationJpaRepository,
            PersonJpaRepository personJpaRepository,
            ClubJpaRepository clubJpaRepository
    ) {

        this.clubMembershipApplicationJpaRepository = clubMembershipApplicationJpaRepository;
        this.personJpaRepository = personJpaRepository;
        this.clubJpaRepository = clubJpaRepository;
    }

    @Override
    public Optional<ClubMembershipApplication> findById(UUID id) {

        return clubMembershipApplicationJpaRepository.findById(id).map(ClubMembershipApplicationJpa::toDomain);
    }

    @Override
    public ClubMembershipApplication create(ClubMembershipApplication.Draft draft) {

        var application = new ClubMembershipApplicationJpa();
        application.setPerson(personJpaRepository.toJpaIfConsistent(draft.getPerson()));
        application.setClub(clubJpaRepository.toJpaIfConsistent(draft.getClub()));
        return clubMembershipApplicationJpaRepository.save(application).toDomain();
    }
}
