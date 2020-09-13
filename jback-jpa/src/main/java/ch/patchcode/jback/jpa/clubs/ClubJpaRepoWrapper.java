package ch.patchcode.jback.jpa.clubs;

import ch.patchcode.jback.core.clubs.ClubRepository;
import ch.patchcode.jback.core.clubs.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClubJpaRepoWrapper implements ClubRepository {

    private final ClubJpaRepository clubJpaRepository;

    @Autowired
    public ClubJpaRepoWrapper(ClubJpaRepository clubJpaRepository) {
        this.clubJpaRepository = clubJpaRepository;
    }

    @Override
    public Optional<Club> findOne(UUID id) {

        return clubJpaRepository.findById(id).map(ClubJpa::toDomain);
    }
}
