package ch.patchcode.jback.jpa.entitiesSpring;

import ch.patchcode.jback.jpa.entities.PersonJpa;
import ch.patchcode.jback.jpa.entities.RoleJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleJpaRepository extends
        JpaRepository<RoleJpa, UUID>,
        ch.patchcode.jback.jpa.entities.RoleJpaRepository {

    @Override
    <S extends RoleJpa> S save(S s);

    @Override
    Optional<RoleJpa> findById(UUID uuid);

    @Override
    List<RoleJpa> findByPersonIn(List<PersonJpa> persons);
}
