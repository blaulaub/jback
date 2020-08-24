package ch.patchcode.jback.jpa.registration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegistrationJpaRepository extends JpaRepository<Registration, UUID> {
}
