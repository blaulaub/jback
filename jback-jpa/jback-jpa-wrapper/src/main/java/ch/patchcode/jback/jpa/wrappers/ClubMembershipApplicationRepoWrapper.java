package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.coreEntities.ClubMembershipApplication;
import ch.patchcode.jback.coreEntities.ClubMembershipApplicationRepository;
import ch.patchcode.jback.jpa.entities.ClubMembershipApplicationJpa;
import ch.patchcode.jback.jpa.entitiesSpring.ClubJpaRepository;
import ch.patchcode.jback.jpa.entitiesSpring.ClubMembershipApplicationJpaRepository;
import ch.patchcode.jback.jpa.entitiesSpring.PersonJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClubMembershipApplicationRepoWrapper implements ClubMembershipApplicationRepository {

    public static final UUID NULL_ID = UUID.nameUUIDFromBytes(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
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
    public List<ClubMembershipApplication> getApplications(UUID afterId, int size) {

        return clubMembershipApplicationJpaRepository.findAllByIdGreaterThan(
                Optional.ofNullable(afterId).orElse(NULL_ID),
                PageRequest.of(0, size)
        ).map(ClubMembershipApplicationJpa::toDomain).getContent();
    }

    @Override
    public ClubMembershipApplication create(ClubMembershipApplication.Draft draft) {

        var application = new ClubMembershipApplicationJpa();
        application.setPerson(personJpaRepository.toJpaIfConsistent(draft.getPerson()));
        application.setClub(clubJpaRepository.toJpaIfConsistent(draft.getClub()));
        return clubMembershipApplicationJpaRepository.save(application).toDomain();
    }
}
