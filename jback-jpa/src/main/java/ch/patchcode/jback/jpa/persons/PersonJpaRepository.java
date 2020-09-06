package ch.patchcode.jback.jpa.persons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonJpaRepository extends JpaRepository<PersonJpa, UUID> {
}
