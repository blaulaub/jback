package ch.patchcode.jback.jpa.entitiesSpring;

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
public interface ClubJpaRepository extends
        JpaRepository<ClubJpa, UUID>,
        ch.patchcode.jback.jpa.entities.ClubJpaRepository {

    @Override
    <S extends ClubJpa> S save(S s);

    @Override
    Optional<ClubJpa> findById(UUID uuid);

    @Override
    Page<ClubJpa> findAll(Pageable pageable);

    Page<ClubJpa> findAllByNameContaining(String pattern, Pageable pageable);

    @Override
    default List<ClubJpa> findAllLimitedTo(int count) {
        return findAll(PageRequest.of(0, count)).get().collect(toList());
    }

    @Override
    default List<ClubJpa> findAllByNameContainingLimitedTo(String pattern, int count) {
        return findAllByNameContaining(pattern, PageRequest.of(0, count)).get().collect(toList());
    }
}
