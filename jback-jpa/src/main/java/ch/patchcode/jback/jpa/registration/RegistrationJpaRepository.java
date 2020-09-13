package ch.patchcode.jback.jpa.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistrationJpaRepository extends JpaRepository<RegistrationJpa, UUID> {
}
