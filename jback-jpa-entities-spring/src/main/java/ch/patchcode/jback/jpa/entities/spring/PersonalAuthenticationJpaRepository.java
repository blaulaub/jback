package ch.patchcode.jback.jpa.entities.spring;

import ch.patchcode.jback.jpa.entities.PersonalAuthenticationJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonalAuthenticationJpaRepository extends
        JpaRepository<PersonalAuthenticationJpa, UUID>,
        ch.patchcode.jback.jpa.entities.PersonalAuthenticationJpaRepository {

    @Override
    <S extends PersonalAuthenticationJpa> S save(S s);

    @Override
    Optional<PersonalAuthenticationJpa> findById(UUID uuid);
}