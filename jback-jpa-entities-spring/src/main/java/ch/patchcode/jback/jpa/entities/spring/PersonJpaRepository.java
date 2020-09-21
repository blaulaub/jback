package ch.patchcode.jback.jpa.entities.spring;

import ch.patchcode.jback.jpa.entities.PersonJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonJpaRepository extends
        JpaRepository<PersonJpa, UUID>,
        ch.patchcode.jback.jpa.entities.PersonJpaRepository {

    @Override
    <S extends PersonJpa> S save(S s);

    @Override
    Optional<PersonJpa> findById(UUID uuid);
}
