package ch.patchcode.jback.jpa.clubs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClubJpaRepository extends JpaRepository<ClubJpa, UUID> {

    @Override
    <S extends ClubJpa> S save(S s);

    @Override
    Optional<ClubJpa> findById(UUID uuid);
}
