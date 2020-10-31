package ch.patchcode.jback.jpa.entitiesSpring;

import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.jpa.entities.ClubJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Repository
public interface ClubJpaRepository extends JpaRepository<ClubJpa, UUID> {

    @Override
    <S extends ClubJpa> S save(S s);

    @Override
    Optional<ClubJpa> findById(UUID uuid);

    @Override
    Page<ClubJpa> findAll(Pageable pageable);

    Page<ClubJpa> findAllByNameContaining(String pattern, Pageable pageable);

    default List<ClubJpa> findAllLimitedTo(int count) {
        return findAll(PageRequest.of(0, count)).get().collect(toList());
    }

    default List<ClubJpa> findAllByNameContainingLimitedTo(String pattern, int count) {
        return findAllByNameContaining(pattern, PageRequest.of(0, count)).get().collect(toList());
    }

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
