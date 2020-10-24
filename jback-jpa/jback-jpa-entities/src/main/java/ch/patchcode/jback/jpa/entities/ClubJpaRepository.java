package ch.patchcode.jback.jpa.entities;

import ch.patchcode.jback.coreEntities.Club;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubJpaRepository {

    <S extends ClubJpa> S save(S s);

    Optional<ClubJpa> findById(UUID uuid);

    List<ClubJpa> findAllLimitedTo(int count);

    List<ClubJpa> findAllByNameContainingLimitedTo(String pattern, int count);

    default ClubJpa toJpaIfConsistent(Club club) {

        var clubJpa = this.findById(club.getId());
        if (clubJpa.isEmpty()) {
            throw new RuntimeException("club not found");
        }
        if (clubJpa.map(ClubJpa::toDomain)
                .filter(it -> it.equals(club))
                .isEmpty()) {
            throw new RuntimeException("club mismatch (outdated?)");
        }
        return clubJpa.get();
    }
}
