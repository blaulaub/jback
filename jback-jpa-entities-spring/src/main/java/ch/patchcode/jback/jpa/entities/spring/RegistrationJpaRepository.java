package ch.patchcode.jback.jpa.entities.spring;

import ch.patchcode.jback.jpa.entities.RegistrationJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationJpaRepository extends
        JpaRepository<RegistrationJpa, UUID>,
        ch.patchcode.jback.jpa.entities.RegistrationJpaRepository {

    @Override
    <S extends RegistrationJpa> S save(S s);

    @Override
    Optional<RegistrationJpa> findById(UUID uuid);
}
