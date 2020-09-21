package ch.patchcode.jback.jpa.persons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonJpaRepository extends JpaRepository<PersonJpa, UUID> {

    @Override
    <S extends PersonJpa> S save(S s);

    @Override
    Optional<PersonJpa> findById(UUID uuid);
}
